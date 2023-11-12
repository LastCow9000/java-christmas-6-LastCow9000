package christmas.domain.discount;

import christmas.domain.Date;
import christmas.domain.Event;
import christmas.domain.Orders;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Discount {
    private static final int THRESHOLD = 10_000;
    private static final int BASE_COUNT = 1;
    private static final int MINUS_BASE = -1;
    private static final String DELIMITER = ": ";
    private static final String CURRENCY_CODE = "KRW";
    private static final String WON = "원";
    private static final String LINE_FEED = "\n";

    private final List<DiscountStrategy> strategies;
    private DiscountStrategy discountStrategy;
    private Map<Event, Integer> checkedEvents;

    public Discount(List<DiscountStrategy> strategies) {
        this.strategies = strategies;
    }

    //@todo: refactoring
    public void checkEvent(Orders orders, Date date) {
        if (isBelowThreshold(orders)) {
            setCheckedEvents(Map.of(Event.NONE, BASE_COUNT));
            return;
        }

        strategies.forEach(strategy -> {
            setDiscountStrategy(strategy);

            List<Event> shouldApplyEvents = discountStrategy.getShouldApplyEvents(orders, date);
            Map<Event, Integer> checkedEvents = shouldApplyEvents.stream()
                    .collect(Collectors.toMap(
                            key -> key,
                            value -> BASE_COUNT,
                            Integer::sum
                    ));
            setCheckedEvents(checkedEvents);
        });
    }

    //@todo: refactoring
    public String getDetailedEventHistory(Date date) {
        StringBuilder sb = new StringBuilder();
        checkedEvents.forEach((event, count) -> {
            if (event.equals(Event.NONE)) {
                sb.append(event.getName())
                        .append(LINE_FEED);
                return;
            }
            int discountAmount = getDiscountAmount(date, event, count);

            sb.append(event.getName())
                    .append(DELIMITER)
                    .append(formatCurrency(discountAmount))
                    .append(WON)
                    .append(LINE_FEED);
        });

        return sb.toString();
    }

    private boolean isBelowThreshold(Orders orders) {
        return orders.calculateTotalBeforeDiscount() < THRESHOLD;
    }

    private int getDiscountAmount(Date date, Event event, Integer count) {
        return MINUS_BASE * event.calculateTotalAmount(count, date);
    }

    private String formatCurrency(int amount) {
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
        Currency currency = Currency.getInstance(CURRENCY_CODE);
        String withCurrencySymbol = currencyInstance.format(amount);

        return withCurrencySymbol.replace(currency.getSymbol(), "");
    }

    private void setDiscountStrategy(DiscountStrategy strategy) {
        this.discountStrategy = strategy;
    }

    private void setCheckedEvents(Map<Event, Integer> checkedEvents) {
        this.checkedEvents = checkedEvents;
    }
}
