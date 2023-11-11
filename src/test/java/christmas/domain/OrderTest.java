package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.exception.ExceptionMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


class OrderTest {
    @DisplayName("단건 메뉴 주문이 성공한다.")
    @ParameterizedTest
    @CsvSource(value = {"해산물파스타:1", "양송이수프:10", "아이스크림:3"}, delimiter = ':')
    void createOrderTest(String name, int count) {
        // when, then
        assertThatNoException()
                .isThrownBy(() -> Order.of(name, count));
    }

    @DisplayName("개수가 1이상의 숫자가 아니면 예외가 발생해야 한다.")
    @ParameterizedTest
    @CsvSource(value = {"해산물파스타:-1", "양송이수프:0"}, delimiter = ':')
    void validateCountTest(String name, int count) {
        // when, then
        assertThatThrownBy(() -> Order.of(name, count))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessage.INVALID_ORDER);
    }

    @DisplayName("해당 메뉴의 총 가격을 반환해야 한다.")
    @Test
    void getTotalPriceTest() {
        // given
        Order order = Order.of("티본스테이크", 10);

        // when
        int totalPrice = order.getTotalPrice();

        // then
        assertThat(totalPrice).isEqualTo(550_000);
    }
}