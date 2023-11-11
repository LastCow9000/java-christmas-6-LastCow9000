package christmas.domain;

import christmas.dto.OrderParam;
import christmas.exception.ExceptionMessage;
import christmas.exception.InputException;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Orders {
    private static final String DELIMITER = "-";

    private List<Order> orders;

    public Orders(List<Order> orders) {
        this.orders = orders;
    }

    public void createOrder(List<String> orders) {
        validateDuplicate(orders);
        //@todo: 개수의 합이 20이하인지 검증
        validateOnlyBeverage(orders);

        orders.forEach(order -> {
            OrderParam orderParam = splitByNameAndCount(order);
            Order.of(orderParam.name(), orderParam.count());
        });
    }

    private void validateDuplicate(List<String> orders) {
        if (getDeduplicatedNames(orders).size() != orders.size()) {
            throw new InputException(ExceptionMessage.INVALID_ORDER);
        }
    }

    private Set<String> getDeduplicatedNames(List<String> orders) {
        return orders.stream()
                .map(order -> splitByNameAndCount(order).name())
                .collect(Collectors.toSet());
    }

    private void validateOnlyBeverage(List<String> orders) {
        long beverageCount = orders.stream()
                .filter(isBeverage())
                .count();

        if (beverageCount == orders.size()) {
            throw new InputException(ExceptionMessage.ONLY_BEVERAGE_ORDER);
        }
    }

    private Predicate<String> isBeverage() {
        return order -> Menu.isBeverage(splitByNameAndCount(order).name());
    }

    private OrderParam splitByNameAndCount(String order) {
        String[] NameAndCount = order.split(DELIMITER);

        return new OrderParam(NameAndCount[0], toInt(NameAndCount[1]));
    }

    private int toInt(String number) {
        return Integer.parseInt(number);
    }
}
