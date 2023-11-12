package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class EventTest {
    @DisplayName("각 이벤트에 따른 할인 금액을 계산해서 반환해야 한다.")
    @ParameterizedTest
    @MethodSource("getEventsWithCountAndDate")
    void calculateTotalAmountTest(EventDto eventDto) {
        // given
        Event event = eventDto.event();
        int count = eventDto.count();
        Date date = eventDto.date();
        int expectedAmount = eventDto.expectedAmount();

        // when
        int amount = event.calculateTotalAmount(count, date);

        // then
        assertThat(amount).isEqualTo(expectedAmount);
    }

    static Stream<EventDto> getEventsWithCountAndDate() {
        return Stream.of(
                new EventDto(Event.CHRISTMAS_D_DAY_DISCOUNT, 5, Date.from(25), 3_400),
                new EventDto(Event.WEEKDAY_DISCOUNT, 2, Date.from(25), 4_046),
                new EventDto(Event.WEEKEND_DISCOUNT, 10, Date.from(25), 20_230),
                new EventDto(Event.GIFT, 7, Date.from(25), 25_000),
                new EventDto(Event.SPECIAL_DISCOUNT, 5, Date.from(25), 1_000)
        );
    }
}

record EventDto(Event event, int count, Date date, int expectedAmount) {
}