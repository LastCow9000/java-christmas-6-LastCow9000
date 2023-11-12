package christmas.domain.discount;

import christmas.domain.Date;
import christmas.domain.Event;
import christmas.domain.Orders;
import java.util.List;

public interface DiscountStrategy {
    List<Event> getShouldApplyEvents(Orders orders, Date date);
}
