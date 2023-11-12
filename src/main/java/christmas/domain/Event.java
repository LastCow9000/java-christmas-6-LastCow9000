package christmas.domain;

public enum Event {
    NONE("없음", 0),
    CHRISTMAS_D_DAY_DISCOUNT("크리스마스 디데이 할인", 1_000),
    WEEKDAY_DISCOUNT("평일 할인", 2_023),
    WEEKEND_DISCOUNT("주말 할인", 2_023),
    SPECIAL_DISCOUNT("특별 할인", 1_000),
    GIFT("증정 이벤트", 25_000);

    private static final int BASE_AMOUNT_PER_DATE = 100;

    private final String name;
    private final int amount;

    Event(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public int calculateTotalAmount(int count, Date date) {
        if (this.equals(CHRISTMAS_D_DAY_DISCOUNT)) {
            return getAmountOfDDayDiscount(date);
        }

        if (isOneTimeEvent()) {
            return amount;
        }

        return count * amount;
    }
    
    public String getName() {
        return name;
    }

    private boolean isOneTimeEvent() {
        return this.equals(SPECIAL_DISCOUNT) || this.equals(GIFT);
    }

    private int getAmountOfDDayDiscount(Date date) {
        return CHRISTMAS_D_DAY_DISCOUNT.amount + (BASE_AMOUNT_PER_DATE * date.daysSince1Day());
    }
}
