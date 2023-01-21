import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//class X {
//    public static void main(String[] args) {
//        CurrencyTest x = new CurrencyTest();
//        x.testGetAmount();
//    }
//}

class CurrencyTest {

    @Test
    void testGetAmount() {
        Currency fiveThousand = new FiveThousand(1);
        assertEquals(1, fiveThousand.getAmount());

        Currency thousand = new Thousand(2);
        assertEquals(2, thousand.getAmount());

        Currency fiveHundred = new FiveHundred(3);
        assertEquals(3, fiveHundred.getAmount());

        Currency hundred = new Hundred(4);
        assertEquals(4, hundred.getAmount());

        Currency fifty = new Fifty(5);
        assertEquals(5, fifty.getAmount());
    }

    @Test
    void testSetAmount() {
        Currency fiveThousand = new FiveThousand(1);
        assertEquals(0, fiveThousand.setAmount(0));

        Currency thousand = new Thousand(2);
        assertEquals(1, thousand.setAmount(1));

        Currency fiveHundred = new FiveHundred(3);
        assertEquals(2, fiveHundred.setAmount(2));

        Currency hundred = new Hundred(4);
        assertEquals(3, hundred.setAmount(3));

        Currency fifty = new Fifty(5);
        assertEquals(4, fifty.setAmount(4));
    }

    @Test
    void testMinus() {
        Currency fiveThousand = new FiveThousand(10);
        fiveThousand.minus(4);
        assertEquals(6, fiveThousand.getAmount());

        Currency thousand = new Thousand(10);
        thousand.minus(5);
        assertEquals(5, thousand.getAmount());

        Currency fiveHundred = new FiveHundred(10);
        fiveHundred.minus(6);
        assertEquals(4, fiveHundred.getAmount());

        Currency hundred = new Hundred(10);
        hundred.minus(7);
        assertEquals(3, hundred.getAmount());

        Currency fifty = new Fifty(10);
        fifty.minus(8);
        assertEquals(2, fifty.getAmount());
    }

    @Test
    void testPlus() {
        Currency fiveThousand = new FiveThousand(10);
        fiveThousand.plus(4);
        assertEquals(14, fiveThousand.getAmount());

        Currency thousand = new Thousand(10);
        thousand.plus(5);
        assertEquals(15, thousand.getAmount());

        Currency fiveHundred = new FiveHundred(10);
        fiveHundred.plus(6);
        assertEquals(16, fiveHundred.getAmount());

        Currency hundred = new Hundred(10);
        hundred.plus(7);
        assertEquals(17, hundred.getAmount());

        Currency fifty = new Fifty(10);
        fifty.plus(8);
        assertEquals(18, fifty.getAmount());
    }
}