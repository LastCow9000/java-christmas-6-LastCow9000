package christmas.domain;

import christmas.exception.ExceptionMessage;
import christmas.exception.InputException;
import java.util.Arrays;

public enum Menu {
    MUSHROOM_CREAM_SOUP("양송이수프", 6_000, FoodType.APPETIZER),
    TAPAS("타파스", 5_500, FoodType.APPETIZER),
    CAESAR_SALAD("시저샐러드", 8_000, FoodType.APPETIZER),
    T_BONE_STEAK("티본스테이크", 55_000, FoodType.MAIN),
    BARBECUE_RIBS("바비큐립", 54_000, FoodType.MAIN),
    SEAFOOD_PASTA("해산물파스타", 35_000, FoodType.MAIN),
    CHRISTMAS_PASTA("크리스마스파스타", 25_000, FoodType.MAIN),
    CHOCOLATE_CAKE("초코케이크", 15_000, FoodType.DESSERT),
    ICE_CREAM("아이스크림", 5_000, FoodType.DESSERT),
    ZERO_COLA("제로콜라", 3_000, FoodType.BEVERAGE),
    RED_WINE("레드와인", 60_000, FoodType.BEVERAGE),
    CHAMPAGNE("샴페인", 25_000, FoodType.BEVERAGE);

    private String name;
    private int price;
    private FoodType type;

    Menu(String name, int price, FoodType type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public static Menu findMenuByName(String name) {
        return Arrays.stream(values())
                .filter(value -> value.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new InputException(ExceptionMessage.INVALID_ORDER));
    }

    public static boolean isBeverage(String name) {
        return findMenuByName(name).type.equals(FoodType.BEVERAGE);
    }

    public boolean isDessert() {
        return this.type.equals(FoodType.DESSERT);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
