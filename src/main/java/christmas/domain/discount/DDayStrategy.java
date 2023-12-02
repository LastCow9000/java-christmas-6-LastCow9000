package christmas.domain.discount;

import christmas.domain.Date;
import christmas.domain.Event;
import christmas.domain.Orders;
import java.util.List;

public class DDayStrategy implements DiscountStrategy {
    @Override
    public List<Event> getShouldApplyEvents(Orders orders, Date date) {
        if (date.isBetween1And25days()) {
            return List.of(Event.CHRISTMAS_D_DAY_DISCOUNT);
        }

        return List.of(Event.NONE);
    }
}
