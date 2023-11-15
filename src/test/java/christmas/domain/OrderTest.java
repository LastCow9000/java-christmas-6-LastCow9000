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

    @DisplayName("디저트 메뉴이면 개수를 반환해야 한다.")
    @Test
    void getCountPerDessertTest() {
        // given
        int count = 5;
        Order order = Order.of("초코케이크", count);

        // when
        int countPerDessert = order.getCountPerDessert();

        // then
        assertThat(countPerDessert).isEqualTo(count);
    }

    @DisplayName("디저트 메뉴가 아니면 0을 반환해야 한다.")
    @Test
    void getCountPerDessertFailTest() {
        // given
        Order order = Order.of("티본스테이크", 5);

        // when
        int countPerDessert = order.getCountPerDessert();

        // then
        assertThat(countPerDessert).isZero();
    }

    @DisplayName("메인 메뉴이면 개수를 반환해야 한다.")
    @Test
    void getCountPerMainTest() {
        // given
        int count = 3;
        Order order = Order.of("티본스테이크", count);

        // when
        int countPerMain = order.getCountPerMain();

        // then
        assertThat(countPerMain).isEqualTo(count);
    }

    @DisplayName("메인 메뉴가 아니면 0을 반환해야 한다.")
    @Test
    void getCountPerMainFailTest() {
        // given
        Order order = Order.of("아이스크림", 5);

        // when
        int countPerMain = order.getCountPerMain();

        // then
        assertThat(countPerMain).isZero();
    }

    @DisplayName("메뉴와 개수를 문자열로 반환해야 한다.")
    @Test
    void getStringMenuAndCountTest() {
        // given
        Order order = Order.of("바비큐립", 4);

        // when
        String menuAndCount = order.getStringMenuAndCount();

        // then
        assertThat(menuAndCount).contains("바비큐립 4개");
    }
}