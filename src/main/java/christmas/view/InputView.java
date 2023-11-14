package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.exception.ExceptionMessage;
import christmas.exception.InputException;

public class InputView {
    private static final String INPUT_DATE = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";

    public static int getDate() {
        print(INPUT_DATE);

        return toInt(read());
    }

    private static void print(String data) {
        System.out.println(data);
    }

    private static String read() {
        return Console.readLine();
    }

    private static int toInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            throw new InputException(ExceptionMessage.INVALID_DATE);
        }
    }
}
