package christmas.domain;

import christmas.exception.ExceptionMessage;
import christmas.exception.InputException;

public class Order {

    private static final int MIN_COUNT = 1;
    private static final int NONE = 0;
    private static final String BLANK = " ";
    private static final String COUNT = "개";
    private static final String LINE_FEED = "\n";

    private final Menu menu;
    private final int count;

    private Order(Menu menu, int count) {
        validateCount(count);
        this.menu = menu;
        this.count = count;
    }

    public static Order of(String name, int count) {
        return new Order(Menu.findMenuByName(name), count);
    }

    public int getTotalPrice() {
        return menu.getPrice() * count;
    }

    public int getCountPerDessert() {
        if (menu.isDessert()) {
            return count;
        }

        return NONE;
    }

    public int getCountPerMain() {
        if (menu.isMain()) {
            return count;
        }

        return NONE;
    }

    public String getStringMenuAndCount() {
        return new StringBuilder()
                .append(menu.getName())
                .append(BLANK)
                .append(count)
                .append(COUNT)
                .append(LINE_FEED)
                .toString();
    }

    private void validateCount(int count) {
        if (count < MIN_COUNT) {
            throw new InputException(ExceptionMessage.INVALID_ORDER);
        }
    }
}
