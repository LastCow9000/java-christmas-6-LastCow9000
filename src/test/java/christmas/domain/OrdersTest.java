package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.exception.ExceptionMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class OrdersTest {
    @DisplayName("음료만 주문했으면 예외가 발생해야 한다.")
    @ParameterizedTest
    @MethodSource("getOnlyBeverages")
    void validateOnlyBeverageTest(List<String> orders) {
        // given
        Orders newOrders = new Orders(new ArrayList<>());

        // when, then
        assertThatThrownBy(() -> newOrders.createOrder(orders))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessage.ONLY_BEVERAGE_ORDER);
    }

    static Stream<List<String>> getOnlyBeverages() {
        return Stream.of(
                List.of("제로콜라-10"),
                List.of("레드와인-2"),
                List.of("샴페인-1", "레드와인-2"),
                List.of("제로콜라-3", "레드와인-3", "샴페인-3")
        );
    }

    @DisplayName("음료와 음식을 같이 주문했으면 예외가 발생하지 않는다.")
    @Test
    void validateOnlyBeverageWithFoodTest() {
        // given
        List<String> orders = Arrays.asList("제로콜라-3", "레드와인-3", "샴페인-3", "초코케이크-1");
        Orders newOrders = new Orders(new ArrayList<>());

        // when, then
        assertThatNoException().isThrownBy(() -> newOrders.createOrder(orders));
    }

    @DisplayName("중복 메뉴를 주문했을 경우 예외가 발생해야 한다.")
    @ParameterizedTest
    @MethodSource("getDuplicatedOrders")
    void validateDuplicateTest(List<String> orders) {
        // given
        Orders newOrders = new Orders(new ArrayList<>());

        // when, then
        assertThatThrownBy(() -> newOrders.createOrder(orders))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessage.INVALID_ORDER);
    }

    static Stream<List<String>> getDuplicatedOrders() {
        return Stream.of(
                List.of("샴페인-1", "샴페인-9"),
                List.of("아이스크림-2", "해산물파스타-1", "아이스크림-11"),
                List.of("제로콜라-1", "타파스-1", "바비큐립-2", "제로콜라-1")
        );
    }

    @DisplayName("20개 초과 주문했으면 예외가 발생해야 한다.")
    @ParameterizedTest
    @MethodSource("getOverLimitOrders")
    void validateOverLimitTest(List<String> orders) {
        // given
        Orders newOrders = new Orders(new ArrayList<>());

        // when, then
        assertThatThrownBy(() -> newOrders.createOrder(orders))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessage.OVER_LIMIT_ORDER);
    }

    static Stream<List<String>> getOverLimitOrders() {
        return Stream.of(
                List.of("티본스테이크-21"),
                List.of("크리스마스파스타-10", "시저샐러드-11"),
                List.of("제로콜라-5", "초코케이크-5", "바비큐립-5", "해산물파스타-5", "타파스-1")
        );
    }

    @DisplayName("할인 전 총주문 금액을 반환해야 한다.")
    @Test
    void calculateTotalBeforeDiscountTest() {
        // given
        Orders newOrders = new Orders(
                List.of(
                        Order.of("티본스테이크", 2),
                        Order.of("제로콜라", 2),
                        Order.of("시저샐러드", 1),
                        Order.of("초코케이크", 1),
                        Order.of("아이스크림", 1)
                ));

        // when
        int totalPrice = newOrders.calculateTotalBeforeDiscount();

        // then
        assertThat(totalPrice).isEqualTo(144_000);
    }

}