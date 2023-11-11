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
    private static final int LIMIT_COUNT = 20;

    private List<Order> orders;

    public Orders(List<Order> orders) {
        this.orders = orders;
    }

    public void createOrder(List<String> orders) {
        validateDuplicate(orders);
        validateOverLimit(orders);
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

    private void validateOverLimit(List<String> orders) {
        if (getSumOfCount(orders) > LIMIT_COUNT) {
            throw new InputException(ExceptionMessage.OVER_LIMIT_ORDER);
        }
    }

    private int getSumOfCount(List<String> orders) {
        return orders.stream()
                .mapToInt(order -> splitByNameAndCount(order).count())
                .sum();
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
