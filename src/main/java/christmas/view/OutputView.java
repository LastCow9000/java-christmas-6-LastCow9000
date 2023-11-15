package christmas.view;

public class OutputView {
    private static final String GREETING = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String PREVIEW_TITLE = "12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!";
    private static final String ORDERED_MENU = "<주문 메뉴>";
    private static final String TOTAL_BEFORE_DISCOUNT = "<할인 전 총주문 금액>";
    private static final String GIFT_MENU = "<증정 메뉴>";
    private static final String BENEFIT_HISTORY = "<혜택 내역>";
    private static final String TOTAL_BENEFIT = "<총혜택 금액>";
    private static final String DISCOUNTED_TOTAL = "<할인 후 예상 결제 금액>";
    private static final String EVENT_BADGE = "<12월 이벤트 배지>";

    private OutputView() {
    }

    public static void printGreeting() {
        print(GREETING);
    }

    public static void printPreviewTitle(int date) {
        print(String.format(PREVIEW_TITLE, date));
        printLineFeed();
    }

    public static void printOrderedMenu(String orders) {
        print(ORDERED_MENU);
        print(orders);
    }

    public static void printTotalBeforeDiscount(String amount) {
        print(TOTAL_BEFORE_DISCOUNT);
        print(amount);
        printLineFeed();
    }

    public static void printGiftMenu(String menu) {
        print(GIFT_MENU);
        print(menu);
        printLineFeed();
    }

    public static void printBenefitHistory(String history) {
        print(BENEFIT_HISTORY);
        print(history);
    }

    public static void printTotalBenefit(String amount) {
        print(TOTAL_BENEFIT);
        print(amount);
        printLineFeed();
    }

    public static void printDiscountedTotal(String amount) {
        print(DISCOUNTED_TOTAL);
        print(amount);
        printLineFeed();
    }

    public static void printEventBadge(String badge) {
        print(EVENT_BADGE);
        print(badge);
    }

    private static void print(String input) {
        System.out.println(input);
    }

    private static void printLineFeed() {
        System.out.println();
    }
}
