package christmas.domain;

import static org.assertj.core.api.Assertions.*;

import christmas.exception.ExceptionMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


class MenuTest {
    @DisplayName("메뉴판에 없는 메뉴이면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"양송이파스타", "치킨파스타", "치즈케이크", "화이트와인"})
    void OutOfMenuTest(String name) {
        // when, then
        assertThatThrownBy(() -> Menu.findMenuByName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessage.INVALID_ORDER);
    }
}