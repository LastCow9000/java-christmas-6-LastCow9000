package christmas.domain.discount;

import christmas.domain.Date;
import christmas.domain.Event;
import christmas.domain.Orders;
import java.util.List;
import java.util.stream.IntStream;

public class WeekendStrategy implements DiscountStrategy {

    @Override
    public List<Event> getShouldApplyEvents(Orders orders, Date date) {
        if (date.isWeekday()) {
            return List.of(Event.NONE);
        }

        return IntStream
                .range(0, orders.getCountOfMain())
                .mapToObj(index -> Event.WEEKEND_DISCOUNT)
                .toList();
    }
}
