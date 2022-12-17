import org.example.Drink;
import org.example.Order;
import org.example.VendingMachine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SimpleOrderTest {

    @Test
    public void placeASuccessfulOrder(){
        VendingMachine vm = new VendingMachine(Locale.GERMANY);
        vm.insertCoin(8, 0, 0, 0, 1);
        assertTrue(vm.buy(new Order(Drink.COCA_COLA)));
    }

    @Test
    public void placeAFailingOrderWithNoAvailableItem(){
        VendingMachine vm = new VendingMachine(Locale.GERMANY);
        vm.insertCoin(8, 0, 0, 0, 1);
        assertFalse(vm.buy(new Order(Drink.FANTA)));
    }

    @Test
    public void placeAFailingOrderInsufficientCoins(){
        VendingMachine vm = new VendingMachine(Locale.GERMANY);
        vm.insertCoin(0, 0, 0, 0, 0);
        assertFalse(vm.buy(new Order(Drink.WASSER)));
    }
}
