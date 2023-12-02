package christmas.domain.discount;

import christmas.domain.Date;
import christmas.domain.Event;
import christmas.domain.Orders;
import java.util.List;

public class GiftStrategy implements DiscountStrategy {
    private static final int GIFT_BASE_AMOUNT = 120_000;

    @Override
    public List<Event> getShouldApplyEvents(Orders orders, Date date) {
        if (orders.calculateTotalBeforeDiscount() >= GIFT_BASE_AMOUNT) {
            return List.of(Event.GIFT);
        }

        return List.of(Event.NONE);
    }
}
