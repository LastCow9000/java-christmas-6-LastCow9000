package christmas.domain;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.exception.ExceptionMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DateTest {
    @DisplayName("1 ~ 31이 아닌 숫자가 들어오면 예외가 발생해야 한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 32, 40})
    void validateRangeTest(int date) {
        // when, then
        assertThatThrownBy(() -> Date.from(date))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessage.INVALID_DATE);
    }

    @DisplayName("1 ~ 31인 숫자가 들어오면 정상적으로 날짜 객체가 만들어진다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 31, 22})
    void createDateSuccessTest(int date) {
        // when, then
        assertThatNoException().isThrownBy(() -> Date.from(date));
    }
}