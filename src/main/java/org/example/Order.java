package org.example;

public record Order(Drink drink) {
}

enum Drink{
    COCA_COLA,
    FANTA,
    SPRITE,
    WASSER
}
