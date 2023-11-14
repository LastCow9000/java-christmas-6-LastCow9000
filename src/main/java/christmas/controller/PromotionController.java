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
import christmas.view.InputView;
import christmas.view.OutputView;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class PromotionController {
    private static final String CURRENCY_CODE = "KRW";
    private static final String WON = "원";

    public void run() {
        OutputView.printGreeting();
        Date date = Date.from(InputView.getDate());

        Orders orders = new Orders(new ArrayList<>());
        orders.createOrder(InputView.getMenu());

        OutputView.printPreviewTitle(date.getDay());
        OutputView.printOrderedMenu(orders.getStringOrders());

        int amount = orders.calculateTotalBeforeDiscount();
        OutputView.printTotalBeforeDiscount(formatCurrency(amount) + WON);

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
        OutputView.printTotalBenefit(formatCurrency(totalBenefitAmount) + WON);

        OutputView.printDiscountedTotal(
                formatCurrency(orders.calculateTotalBeforeDiscount() + discount.getTotalDiscountAmount(date)) + WON);

        OutputView.printEventBadge(EventBadge.getEventBadge(totalBenefitAmount).getName());
    }

    //@todo: refactor
    private String formatCurrency(int amount) {
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
        Currency currency = Currency.getInstance(CURRENCY_CODE);
        String withCurrencySymbol = currencyInstance.format(amount);

        return withCurrencySymbol.replace(currency.getSymbol(), "");
    }
}
