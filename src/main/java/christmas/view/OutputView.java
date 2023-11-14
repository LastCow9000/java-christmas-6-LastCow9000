package christmas.view;

public class OutputView {
    private static final String GREETING = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String PREVIEW_TITLE = "12월 3일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!";

    public void printGreeting() {
        print(GREETING);
    }

    public void printPreviewTitle() {
        print(PREVIEW_TITLE);
    }

    private void print(String input) {
        System.out.println(input);
    }
}
