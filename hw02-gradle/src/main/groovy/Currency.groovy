class Currency implements CurrencyInterface {
    int amount
    int rate

    Currency(amount) {
        this.amount = amount
    }

    /**
     * Этот геттер используется для отображения количества кюпюр в одной из ячеек номиналов купюр банкомата
     */
    int getAmount() {
        return this.amount
    }

    /**
     * Этот сеттер используется изменения количества купюр в одной из ячеек номиналов купюр банкомата
     */
    int setAmount(int right) {
        return this.amount = right
    }

    /**
     * Этот метод используется для снятия купюр в одной из ячеек номиналов купюр банкоммата
     * @param right - количество купюр, снимаемых из ячейки
     */
    Currency minus(int right) {
        this.amount -= right
        this
    }

    /**
     * Этот метод используется для пополнения купюр в одной из ячеек номиналов купюр банкоммата
     * @param right - количество купюр, пополняемых в ячейку
     */
    Currency plus(int right) {
        this.amount += right
        this
    }
}


class FiveThousand extends Currency {
    int rate = 5000

    FiveThousand(Object amount) {
        super(amount)
    }
}

class Thousand extends Currency {
    int rate = 1000

    Thousand(Object amount) {
        super(amount)
    }
}

class FiveHundred extends Currency {
    int rate = 500

    FiveHundred(Object amount) {
        super(amount)
    }
}

class Hundred extends Currency {
    int rate = 100

    Hundred(Object amount) {
        super(amount)
    }
}

class Fifty extends Currency {
    int rate = 50

    Fifty(Object amount) {
        super(amount)
    }
}