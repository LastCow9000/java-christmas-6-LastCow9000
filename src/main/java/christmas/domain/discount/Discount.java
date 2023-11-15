package christmas.domain.discount;

import static christmas.Constants.BASE_COUNT;
import static christmas.Constants.BLANK;
import static christmas.Constants.COUNT;
import static christmas.Constants.LINE_FEED;
import static christmas.Constants.NONE;

import christmas.domain.Date;
import christmas.domain.Event;
import christmas.domain.Menu;
import christmas.domain.Orders;
import christmas.util.CurrencyUtil;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Discount {
    private static final int DISCOUNT_THRESHOLD = 10_000;
    private static final int MINUS_BASE = -1;
    private static final String DELIMITER = ": ";

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
        if (isBelowAmountForDiscount(orders)) {
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

    public String getDetailedEventHistory() {
        if (hasOnlyNoneEvent()) {
            return Event.NONE.getName() + LINE_FEED;
        }

        return createEventHistory();
    }

    private String createEventHistory() {
        return checkedEvents.entrySet()
                .stream()
                .map(this::getStringHistory)
                .collect(Collectors.joining());
    }

    private StringBuilder getStringHistory(Entry<Event, Integer> entry) {
        Event event = entry.getKey();
        int count = entry.getValue();
        String amount = CurrencyUtil.formatToKor(getDiscountAmount(event, count));

        return new StringBuilder()
                .append(event.getName())
                .append(DELIMITER)
                .append(amount)
                .append(LINE_FEED);
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

        return NONE;
    }

    private boolean hasEvent(Event event) {
        return checkedEvents.containsKey(event);
    }

    private boolean isBelowAmountForDiscount(Orders orders) {
        return orders.calculateTotalBeforeDiscount() < DISCOUNT_THRESHOLD;
    }

    private boolean hasOnlyNoneEvent() {
        return checkedEvents.size() == 1 &&
                checkedEvents.entrySet()
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
