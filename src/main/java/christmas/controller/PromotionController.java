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
import christmas.exception.InputException;
import christmas.util.CurrencyUtil;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PromotionController {


    public void run() {
        OutputView.printGreeting();

        Date date = initVisitDate();
        Orders orders = initOrders();

        displayPreviewForEvent(date, orders);
    }

    private void displayPreviewForEvent(Date date, Orders orders) {
        OutputView.printPreviewTitle(date.getDay());
        OutputView.printOrderedMenu(orders.getStringOrders());
        displayTotalBeforeDiscount(orders);
        displayDiscountDetails(date, orders);
    }

    private void displayDiscountDetails(Date date, Orders orders) {
        Discount discount = applyDiscount(date, orders);

        OutputView.printGiftMenu(discount.getStringGiftMenu());
        OutputView.printBenefitHistory(discount.getDetailedEventHistory());

        int totalBenefitAmount = discount.getTotalBenefitAmount();
        displayTotalBenefitAmount(totalBenefitAmount);
        displayDiscountedTotal(orders, discount);

        displayEventBadge(totalBenefitAmount);
    }

    private void displayEventBadge(int totalBenefitAmount) {
        EventBadge eventBadge = EventBadge.getEventBadge(totalBenefitAmount);
        OutputView.printEventBadge(eventBadge.getName());
    }

    private void displayDiscountedTotal(Orders orders, Discount discount) {
        String amount = CurrencyUtil.formatToKor(
                orders.calculateTotalBeforeDiscount() + discount.getTotalDiscountAmount());
        OutputView.printDiscountedTotal(amount);
    }

    private void displayTotalBenefitAmount(int totalBenefitAmount) {
        String totalBenefit = CurrencyUtil.formatToKor(totalBenefitAmount);
        OutputView.printTotalBenefit(totalBenefit);
    }

    private void displayTotalBeforeDiscount(Orders orders) {
        String totalAmount = CurrencyUtil.formatToKor(orders.calculateTotalBeforeDiscount());
        OutputView.printTotalBeforeDiscount(totalAmount);
    }

    private Discount applyDiscount(Date date, Orders orders) {
        Discount discount = new Discount(List.of(
                new DDayStrategy(),
                new WeekdayStrategy(),
                new WeekendStrategy(),
                new SpecialStrategy(),
                new GiftStrategy()),
                date
        );
        discount.checkEvent(orders);

        return discount;
    }

    private Orders initOrders() {
        return wrapByLoop(() -> {
            Orders orders = new Orders(new ArrayList<>());
            orders.createOrder(InputView.getMenu());

            return orders;
        });
    }

    private Date initVisitDate() {
        return wrapByLoop(() -> Date.from(InputView.getDate()));
    }

    private <T> T wrapByLoop(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (InputException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }
}
