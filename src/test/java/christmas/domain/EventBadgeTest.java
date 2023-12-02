package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EventBadgeTest {
    @DisplayName("혜택 금액이 주어지면 해당되는 이벤트 뱃지를 반환해야 한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "3_000:없음",
            "5_000:별",
            "9_999:별",
            "10_000:트리",
            "19_999:트리",
            "20_000:산타",
            "50_000:산타"
    }, delimiter = ':')
    void getEventBadgeTest(int amount, String expectedBadgeName) {
        // when
        EventBadge eventBadge = EventBadge.getEventBadge(amount);

        // then
        assertThat(eventBadge.getName()).isEqualTo(expectedBadgeName);
    }
}