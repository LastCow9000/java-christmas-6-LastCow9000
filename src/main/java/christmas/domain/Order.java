package christmas.domain;

import christmas.exception.ExceptionMessage;
import christmas.exception.InputException;

public class Order {
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

        return 0;
    }

    public int getCountPerMain() {
        if (menu.isMain()) {
            return count;
        }

        return 0;
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
        if (count < 1) {
            throw new InputException(ExceptionMessage.INVALID_ORDER);
        }
    }
}
