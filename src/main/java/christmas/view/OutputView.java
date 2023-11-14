package christmas.view;

public class OutputView {
    private static final String GREETING = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String PREVIEW_TITLE = "12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!";
    private static final String ORDERED_MENU = "<주문 메뉴>";
    private static final String TOTAL_BEFORE_DISCOUNT = "<할인 전 총주문 금액>";
    private static final String GIFT_MENU = "<증정 메뉴>";

    public static void printGreeting() {
        print(GREETING);
    }

    public static void printPreviewTitle(int date) {
        print(String.format(PREVIEW_TITLE, date));
    }

    public static void printOrderedMenu(String orders) {
        print(ORDERED_MENU);
        print(orders);
    }

    public static void printTotalBeforeDiscount(String amount) {
        print(TOTAL_BEFORE_DISCOUNT);
        print(amount);
    }

    public static void printGiftMenu(String menu) {
        print(GIFT_MENU);
        print(menu);
    }

    private static void print(String input) {
        System.out.println(input);
    }
}
