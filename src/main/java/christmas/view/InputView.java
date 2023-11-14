package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.exception.ExceptionMessage;
import christmas.exception.InputException;
import java.util.List;
import java.util.regex.Pattern;

public class InputView {
    private static final String INPUT_DATE = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    private static final String INPUT_MENU = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";
    private static final Pattern MENU_REG_EXP = Pattern.compile("^[가-힣]+-\\d+(,[가-힣]+-\\d+)*$");
    private static final String DELIMITER = ",";

    public static int getDate() {
        print(INPUT_DATE);

        return toInt(read());
    }

    public static List<String> getMenu() {
        print(INPUT_MENU);
        String input = read();
        validateFormat(input);

        return split(input);
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

    private static void validateFormat(String input) {
        if (!MENU_REG_EXP.matcher(input).matches()) {
            throw new InputException(ExceptionMessage.INVALID_ORDER);
        }
    }

    private static List<String> split(String input) {
        return List.of(input.split(DELIMITER));
    }
}
