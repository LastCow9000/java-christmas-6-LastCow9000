package christmas.controller;

import christmas.domain.Date;
import christmas.domain.EventBadge;
import christmas.domain.Orders;
import christmas.domain.discount.DDayStrategy;
import christmas.domain.discount.Discount;
import christmas.domain.discount.GiftStrategy;
import christmas.domain.discount.SpecialStrategy;
import christmas.domain.discount.WeekdayStrategy;
import christmas.domain.discount.WeekendStrategy;
import christmas.util.CurrencyUtil;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.util.ArrayList;
import java.util.List;

public class PromotionController {


    public void run() {
        OutputView.printGreeting();
        Date date = Date.from(InputView.getDate());

        Orders orders = new Orders(new ArrayList<>());
        orders.createOrder(InputView.getMenu());

        OutputView.printPreviewTitle(date.getDay());
        OutputView.printOrderedMenu(orders.getStringOrders());

        int amount = orders.calculateTotalBeforeDiscount();
        OutputView.printTotalBeforeDiscount(CurrencyUtil.formatToKor(amount));

        Discount discount = new Discount(List.of(
                new DDayStrategy(),
                new WeekdayStrategy(),
                new WeekendStrategy(),
                new SpecialStrategy(),
                new GiftStrategy())
        );
        discount.checkEvent(orders, date);
        OutputView.printGiftMenu(discount.getStringGiftMenu());

        OutputView.printBenefitHistory(discount.getDetailedEventHistory(date));

        int totalBenefitAmount = discount.getTotalBenefitAmount(date);
        OutputView.printTotalBenefit(CurrencyUtil.formatToKor(totalBenefitAmount));

        OutputView.printDiscountedTotal(
                CurrencyUtil.formatToKor(
                        orders.calculateTotalBeforeDiscount() + discount.getTotalDiscountAmount(date)));

        OutputView.printEventBadge(EventBadge.getEventBadge(totalBenefitAmount).getName());
    }
}
