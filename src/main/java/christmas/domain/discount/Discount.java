package christmas.domain.discount;

import christmas.domain.Date;
import christmas.domain.Event;
import christmas.domain.Menu;
import christmas.domain.Orders;
import christmas.util.CurrencyUtil;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Discount {
    private static final int THRESHOLD = 10_000;
    private static final int BASE_COUNT = 1;
    private static final int MINUS_BASE = -1;
    private static final int ZERO = 0;
    private static final String DELIMITER = ": ";
    private static final String LINE_FEED = "\n";
    private static final String COUNT = "개";
    private static final String BLANK = " ";


    private final List<DiscountStrategy> strategies;
    private final Date date;
    private DiscountStrategy discountStrategy;
    private final Map<Event, Integer> checkedEvents;

    public Discount(List<DiscountStrategy> strategies, Date date) {
        this.strategies = strategies;
        this.date = date;
        this.discountStrategy = null;
        this.checkedEvents = new EnumMap<>(Event.class);
    }

    //@todo: refactoring
    public void checkEvent(Orders orders) {
        if (isBelowThreshold(orders)) {
            setCheckedEvents(Map.of(Event.NONE, BASE_COUNT));
            return;
        }

        strategies.forEach(strategy -> {
            setDiscountStrategy(strategy);

            List<Event> shouldApplyEvents = discountStrategy.getShouldApplyEvents(orders, date);
            Map<Event, Integer> checkedEvents = shouldApplyEvents.stream()
                    .filter(event -> !event.equals(Event.NONE))
                    .collect(Collectors.toMap(
                            key -> key,
                            value -> BASE_COUNT,
                            Integer::sum
                    ));
            setCheckedEvents(checkedEvents);
        });
    }

    //@todo: refactoring
    public String getDetailedEventHistory() {
        StringBuilder sb = new StringBuilder();
        if (hasOnlyNoneEvent()) {
            return sb.append(Event.NONE.getName())
                    .append(LINE_FEED)
                    .toString();
        }

        checkedEvents.forEach((event, count) -> {
            int discountAmount = getDiscountAmount(event, count);

            sb.append(event.getName())
                    .append(DELIMITER)
                    .append(CurrencyUtil.formatToKor(discountAmount))
                    .append(LINE_FEED);
        });

        return sb.toString();
    }

    public int getTotalBenefitAmount() {
        return checkedEvents.entrySet()
                .stream()
                .mapToInt(entry -> entry.getKey().calculateTotalAmount(entry.getValue(), date))
                .sum()
                * MINUS_BASE;
    }

    public int getTotalDiscountAmount() {
        return getTotalBenefitAmount() + getExcludedAmount();
    }

    public String getStringGiftMenu() {
        Event event = Event.GIFT;
        if (hasEvent(event)) {
            return new StringBuilder()
                    .append(Menu.CHAMPAGNE.getName())
                    .append(BLANK)
                    .append(BASE_COUNT)
                    .append(COUNT)
                    .toString();
        }

        return Event.NONE.getName();
    }

    private int getExcludedAmount() {
        Event event = Event.GIFT;
        if (hasEvent(event)) {
            return event.getAmount();
        }

        return ZERO;
    }

    private boolean hasEvent(Event event) {
        return checkedEvents.containsKey(event);
    }

    private boolean isBelowThreshold(Orders orders) {
        return orders.calculateTotalBeforeDiscount() < THRESHOLD;
    }

    private boolean hasOnlyNoneEvent() {
        return checkedEvents.size() == 1 && checkedEvents.entrySet()
                .stream()
                .anyMatch(entry -> entry.getKey().equals(Event.NONE));
    }

    private int getDiscountAmount(Event event, Integer count) {
        return MINUS_BASE * event.calculateTotalAmount(count, date);
    }

    private void setDiscountStrategy(DiscountStrategy strategy) {
        this.discountStrategy = strategy;
    }

    private void setCheckedEvents(Map<Event, Integer> checkedEvents) {
        this.checkedEvents.putAll(checkedEvents);
    }
}
