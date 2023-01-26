class Bank {
    static void main(String[] args) {
        ATM atm = new ATM(1, 1, 10, 1, 5)
        println("${atm.showTotal()} is currently in ATM")
        atm.withdrawal(10150)
        println("${atm.showTotal()} is currently in ATM")
        atm + [new Hundred(1), new Fifty(1)]
        println("${atm.showTotal()} is currently in ATM")

    }
}

class ATM {
    private FiveThousand fiveThousand
    private Thousand thousand
    private FiveHundred fiveHundred
    private Hundred hundred
    private Fifty fifty
    private List<? extends Currency> cell
    private int total
    private static int machine = 0

    ATM(fiveThousand, thousand, fiveHundred, hundred, fifty) {
        this.fiveThousand = new FiveThousand(fiveThousand)
        this.thousand = new Thousand(thousand)
        this.fiveHundred = new FiveHundred(fiveHundred)
        this.hundred = new Hundred(hundred)
        this.fifty = new Fifty(fifty)
        this.cell = [this.fiveThousand, this.thousand, this.fiveHundred, this.hundred, this.fifty]

        machine++
        println("ATM number ${machine} has been created")
    }

    /**
     * Этот метод используется для пополнения банкомата купюрами разного номинала в одну общую ячейку
     * @param list - список экземпляров классов валют которые мы передаем для пополнения банкомата
     * @return ничего
     */
    ATM plus(List<? extends Currency> list){
        this.cell.each {banknoteCell ->
            list.each {inputBanknote ->
                if (banknoteCell.getClass() == inputBanknote.getClass()) {
                    banknoteCell + inputBanknote.getAmount()
                }
            }
        }
        println("Money has been loaded")
        this
    }

    /**
     * Этот метод используется для подсчета необходимого количества купюр разного номинала для выдачи
     * и подготовки к выдаче
     * @param input - входящее, общая сумма, которую запрашиваем на снятие
     * @return map - словарь вида ["номинал банкноты": количество]
     */
    private Map calculator(int input) {
        if (input > this.showTotal()) {
            throw new UnsupportedOperationException("ATM is empty !")
        }

        Map<String, Integer> map = ["5000": 0, "1000": 0, "500": 0, "100": 0, "50": 0]

        this.cell.each {
            if (input > 0) {
                int intDivision = (int) (input / it.rate)
                if (intDivision <= it.getAmount()) {
                    map[it.rate as String] = intDivision
                    it - intDivision
                    input -= intDivision * it.rate
                } else {
                    map[it.rate as String] = it.getAmount()
                    input -= it.getAmount() * it.rate
                    it.setAmount(0)
                }
            }
        }
        return map
    }

    /**
     * Этот метод используется для итоговой выдачи денег и отображения отчета клиенту
     * @param input - входящее, общая сумма, которую запрашиваем на снятие
     * @return ничего
     */
    Map withdrawal(int input) {
        Map map = calculator(input)
        println("Your withdrawal is:")
        map.each { key, value ->
            if (value != 0) {
                println("${value} banknotes of ${key}")
            }
        }
        return map
    }

    /**
     * Этот метод используется для получения остатка денег в банкомате
     * @return ничего
     */
    int showTotal() {
        this.total = (
            this.fiveThousand.getAmount() * this.fiveThousand.rate +
            this.thousand.getAmount() * this.thousand.rate +
            this.fiveHundred.getAmount() * this.fiveHundred.rate +
            this.hundred.getAmount() * this.hundred.rate +
            this.fifty.getAmount() * this.fifty.rate
        )
        return this.total
    }
}