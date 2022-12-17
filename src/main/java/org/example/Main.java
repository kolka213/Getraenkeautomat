package org.example;

import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        VendingMachine vm = new VendingMachine(Locale.GERMANY);
        vm.insertCoin(8, 0, 0, 0, 1);
        vm.buy(new Order(Drink.COCA_COLA));
    }
}