package org.example;

import java.util.HashMap;
import java.util.Locale;

public class VendingMachine implements Machine{
    private int moneyReceived;

    private Order order;

    private HashMap<Drink, Integer> priceList;

    private HashMap<Drink, Boolean> availableDrinks;

    private int tenCents;

    private int twentyCents;

    private int fiftyCents;

    private int oneEuro;

    private int twoEuro;

    public VendingMachine(Locale locale) {
        priceList = initPriceList(locale);
        availableDrinks = initAvailableDrinks();
        initCoins();
    }

    /**
     *
     * @param tenCentsCoins anzahl der 10 cent münzen
     * @param twentyCentsCoins anzahl der 20 cent münzen
     * @param fiftyCentsCoins anzahl der 50 cent münzen
     * @param oneEuroCoins anzahl der 1 euro münzen
     * @param twoEuroCoins anzahl der 2 euro münzen
     */
    @Override
    public void insertCoin(int tenCentsCoins, int twentyCentsCoins, int fiftyCentsCoins, int oneEuroCoins,
                           int twoEuroCoins) {
        fillMachineWithCoins(tenCentsCoins, twentyCentsCoins, fiftyCentsCoins, oneEuroCoins, twoEuroCoins);

        // coins werden in cents umgerechnet
        int coinValue = tenCentsCoins * 10 + twentyCentsCoins * 20 + fiftyCentsCoins * 50 + oneEuroCoins * 100 +
                twoEuroCoins * 200;
        moneyReceived += coinValue;
    }

    /**
     *
     * @return integer Array mit der Anzahl an coins die zurückgegeben werden
     */
    public int[] getChange() {
        int change = moneyReceived - priceList.get(order.drink());

        // Wenn change kleiner null, dann zu wenig Geld reingesteckt für das jeweilige Produkt
        if (change < 0) {
            return new int[0];
        }

        int numTwoEurosNeeded = Math.min(twoEuro, change / 200);
        change -= numTwoEurosNeeded * 200;
        int numEurosNeeded = Math.min(oneEuro, change / 100);
        change -= numEurosNeeded * 100;
        int numFiftyCentsNeeded = Math.min(fiftyCents, change / 50);
        change -= numFiftyCentsNeeded * 50;
        int numTwentyCentsNeeded = Math.min(twentyCents, change / 20);
        change -= numTwentyCentsNeeded * 20;
        int numTenCentsNeeded = Math.min(tenCents, change / 10);
        change -= numTenCentsNeeded * 10;

        if (change > 0){
            return new int[0];
        }

        removeCoinsFromMachine(numTenCentsNeeded, numTwentyCentsNeeded, numFiftyCentsNeeded, numEurosNeeded,
                numTwoEurosNeeded);

        int[] changeCoins = { numTwoEurosNeeded, numEurosNeeded, numFiftyCentsNeeded,
                numTwentyCentsNeeded, numTenCentsNeeded };
        moneyReceived = 0;
        return changeCoins;
    }

    @Override
    public boolean buy(Order order) {
        // check ob dring überhaupt verfügbar ist
        if (availableDrinks.get(order.drink())) {
            this.order = order;
            int[] changeCoins = getChange();
            if (changeCoins.length == 0) {
                System.out.println("Not enough money inserted.");
                return false;
            }

            System.out.printf("Price: %s\n", priceList.get(order.drink()));
            System.out.println("Your change is: \n" +
                    "2€: " + changeCoins[0] + "\n" +
                    "1€: " + changeCoins[1] + "\n" +
                    "0.5€: " + changeCoins[2] + "\n" +
                    "0.2€: " + changeCoins[3] + "\n" +
                    "0.1€: " + changeCoins[4]);
            System.out.printf("%s received\n", order.drink().name());

            return true;
        }
        System.out.println("Drink not available.");
        return false;
    }

    @Override
    public void fillMachineWithCoins(int tenCentsCoins, int twentyCentsCoins, int fiftyCentsCoins, int oneEuroCoins,
                                     int twoEuroCoins) {
        this.tenCents += tenCentsCoins;
        this.twentyCents += twentyCentsCoins;
        this.fiftyCents += fiftyCentsCoins;
        this.oneEuro += oneEuroCoins;
        this.twoEuro += twoEuroCoins;
    }

    @Override
    public void removeCoinsFromMachine(int tenCentsCoins, int twentyCentsCoins, int fiftyCentsCoins, int oneEuroCoins,
                                       int twoEuroCoins) {
        this.tenCents -= tenCentsCoins;
        this.twentyCents -= twentyCentsCoins;
        this.fiftyCents -= fiftyCentsCoins;
        this.oneEuro -= oneEuroCoins;
        this.twoEuro -= twoEuroCoins;
    }


    /**
     *
     * @param locale hier entweder US oder GERMANY
     * @return Preisliste als HashMap, preise in cents umgerechnet
     */
    private HashMap<Drink, Integer> initPriceList(Locale locale){
        HashMap<Drink, Integer> map = new HashMap<>();
        if (locale.equals(Locale.US)){
            map.put(Drink.COCA_COLA, 150);
            map.put(Drink.FANTA, 130);
            map.put(Drink.SPRITE, 120);
            map.put(Drink.WASSER, 100);
        }
        if (locale.equals(Locale.GERMANY)){
            map.put(Drink.COCA_COLA, 250);
            map.put(Drink.FANTA, 230);
            map.put(Drink.SPRITE, 220);
            map.put(Drink.WASSER, 200);
        }
        return map;
    }

    private void initCoins(){
        tenCents = 5;
        twentyCents = 5;
        fiftyCents = 5;
        oneEuro = 5;
        twoEuro = 5;
    }


    /**
     *
     * @return HashMap mit Drinks als Schlüssel und einen bool ob verfügbar oder nicht
     */
    private HashMap<Drink, Boolean> initAvailableDrinks(){
        HashMap<Drink, Boolean> map = new HashMap<>();
        map.put(Drink.WASSER, true);
        map.put(Drink.COCA_COLA, true);
        map.put(Drink.FANTA, false);
        map.put(Drink.SPRITE, true);

        return map;
    }
}