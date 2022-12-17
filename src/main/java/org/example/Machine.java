package org.example;

public interface Machine {
    void insertCoin(int tenCents, int twentyCents, int fiftyCents, int oneEuro, int twoEuro);
    int[] getChange();

    boolean buy(Order order);

    void fillMachineWithCoins(int tenCents, int twentyCents, int fiftyCents, int oneEuro, int twoEuro);

    void removeCoinsFromMachine(int tenCents, int twentyCents, int fiftyCents, int oneEuro, int twoEuro);
}
