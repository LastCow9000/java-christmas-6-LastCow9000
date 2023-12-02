package christmas.domain.discount;

import christmas.domain.Date;
import christmas.domain.Event;
import christmas.domain.Orders;
import java.util.List;

public class SpecialStrategy implements DiscountStrategy {
    @Override
    public List<Event> getShouldApplyEvents(Orders orders, Date date) {
        if (date.isSpecialDay()) {
            return List.of(Event.SPECIAL_DISCOUNT);
        }

        return List.of(Event.NONE);
    }
}
