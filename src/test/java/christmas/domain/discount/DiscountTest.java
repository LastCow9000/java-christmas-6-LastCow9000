package christmas.domain.discount;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Date;
import christmas.domain.Order;
import christmas.domain.Orders;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountTest {
    @DisplayName("주문금액이 10000원 미만이면 할인금액이 없어야 한다.")
    @Test
    void noDiscountTest() {
        // given
        Date date = Date.from(25);
        Discount discount = new Discount(List.of(new DDayStrategy()));
        Orders orders = new Orders(List.of(
                Order.of("양송이수프", 1)
        ));

        // when
        discount.checkEvent(orders, date);
        String detailEvents = discount.getDetailedEventHistory(date);

        // then
        assertThat(detailEvents).contains("없음");
    }

    @DisplayName("크리스마스 디데이 이벤트가 적용되어 할인금액이 계산되어야 한다.")
    @Test
    void christmasDDayDiscountTest() {
        // given
        Date date = Date.from(5);
        Discount discount = new Discount(List.of(new DDayStrategy()));
        Orders orders = new Orders(List.of(
                Order.of("티본스테이크", 2),
                Order.of("레드와인", 1)
        ));

        // when
        discount.checkEvent(orders, date);
        String detailEvents = discount.getDetailedEventHistory(date);

        // then
        assertThat(detailEvents).contains("크리스마스 디데이 할인", "-1,400원");
    }

    @DisplayName("증정 이벤트가 적용되어 내역에 나와야 한다.")
    @Test
    void giftDiscountTest() {
        // given
        Date date = Date.from(30);
        Discount discount = new Discount(List.of(new GiftStrategy()));
        Orders orders = new Orders(List.of(
                Order.of("티본스테이크", 2),
                Order.of("레드와인", 1)
        ));

        // when
        discount.checkEvent(orders, date);
        String detailEvents = discount.getDetailedEventHistory(date);
        System.out.println("detailEvents = " + detailEvents);

        // then
        assertThat(detailEvents).contains("증정 이벤트", "-25,000원");
    }

    @DisplayName("특별 할인 이벤트가 적용되어 내역에 나와야 한다.")
    @Test
    void specialDiscountTest() {
        // given
        Date date = Date.from(25);
        Discount discount = new Discount(List.of(new SpecialStrategy()));
        Orders orders = new Orders(List.of(
                Order.of("크리스마스파스타", 1)
        ));

        // when
        discount.checkEvent(orders, date);
        String detailEvents = discount.getDetailedEventHistory(date);

        // then
        assertThat(detailEvents).contains("특별 할인", "-1,000원");
    }

    @DisplayName("평일 할인 이벤트가 적용되어 내역에 나와야 한다.")
    @Test
    void weekdayDiscountTest() {
        // given
        Date date = Date.from(21);
        Discount discount = new Discount(List.of(new WeekdayStrategy()));
        Orders orders = new Orders(List.of(
                Order.of("초코케이크", 2),
                Order.of("아이스크림", 3),
                Order.of("제로콜라", 3)
        ));

        // when
        discount.checkEvent(orders, date);
        String detailEvents = discount.getDetailedEventHistory(date);

        // then
        assertThat(detailEvents).contains("평일 할인", "-10,115원");
    }

    @DisplayName("주말 할인 이벤트가 적용되어 내역에 나와야 한다.")
    @Test
    void weekendDiscountTest() {
        // given
        Date date = Date.from(22);
        Discount discount = new Discount(List.of(new WeekendStrategy()));
        Orders orders = new Orders(List.of(
                Order.of("티본스테이크", 1),
                Order.of("아이스크림", 3),
                Order.of("해산물파스타", 3),
                Order.of("크리스마스파스타", 2),
                Order.of("바비큐립", 2)
        ));

        // when
        discount.checkEvent(orders, date);
        String detailEvents = discount.getDetailedEventHistory(date);

        // then
        assertThat(detailEvents).contains("주말 할인", "-16,184원");
    }
}