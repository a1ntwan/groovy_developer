import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.*;

class ATMTest {

    @Test
    void testPlus() {
        ATM atm = new ATM(1, 1, 1, 1, 1);
        List<? extends Currency> list = List.of((new FiveThousand(1)),
                                                (new Thousand(1)),
                                                (new FiveHundred(1)),
                                                (new Hundred(1)),
                                                (new Fifty(1)));
        atm.plus(list);
        assertEquals(13300, atm.showTotal());
    }

    @Test
    void testWithdrawal() {
        ATM atm0 = new ATM(2, 2, 2, 2, 2);
        Map<String, Integer> map0 = Map.ofEntries(entry("5000", 2), entry("1000", 2), entry("500", 2), entry("100", 2), entry("50", 2));
        assertEquals(map0, atm0.withdrawal(13300));

        ATM atm1 = new ATM(2, 2, 10, 1, 30);
        Map<String, Integer> map1 = Map.ofEntries(entry("5000", 2), entry("1000", 2), entry("500", 0), entry("100", 0), entry("50", 0));
        assertEquals(map1, atm1.withdrawal(12000));

        ATM atm2 = new ATM(1, 1, 10, 1, 30);
        Map<String, Integer> map2 = Map.ofEntries(entry("5000", 1), entry("1000", 1), entry("500", 8), entry("100", 1), entry("50", 1));
        assertEquals(map2, atm2.withdrawal(10150));
    }

    @Test
    void testShowTotal() {
        ATM atm = new ATM(1, 1, 1, 1, 1);
        assertEquals(6650, atm.showTotal());
    }
}