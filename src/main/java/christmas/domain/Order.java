package christmas.domain;

import christmas.exception.ExceptionMessage;
import christmas.exception.InputException;

public class Order {
    private Menu menu;
    private int count;

    private Order(Menu menu, int count) {
        validateCount(count);
        this.menu = menu;
        this.count = count;
    }

    public static Order of(String name, int count) {
        return new Order(Menu.findMenuByName(name), count);
    }

    private void validateCount(int count) {
        if (count < 1) {
            throw new InputException(ExceptionMessage.INVALID_ORDER);
        }
    }
}
