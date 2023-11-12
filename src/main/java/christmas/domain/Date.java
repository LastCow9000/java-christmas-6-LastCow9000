package christmas.domain;

import christmas.exception.ExceptionMessage;
import christmas.exception.InputException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class Date {
    private static final int MIN_DATE = 1;
    private static final int MAX_DATE = 31;
    private static final int OFFSET = 1;
    private static final int CHRISTMAS = 25;
    private static final String SUNDAY = "일";
    private static final List<String> WEEKDAY = List.of(SUNDAY, "월", "화", "수", "목");

    private final LocalDate date;

    private Date(int date) {
        validateRange(date);
        this.date = LocalDate.of(2023, 12, date);
    }

    public static Date from(int date) {
        return new Date(date);
    }

    public boolean isBetween1And25days() {
        return getDay() <= CHRISTMAS;
    }

    public int daysSince1Day() {
        return getDay() - OFFSET;
    }

    public boolean isSpecialDay() {
        return getDay() == CHRISTMAS || getDayOfWeek().equals(SUNDAY);
    }

    public boolean isWeekday() {
        return WEEKDAY.contains(getDayOfWeek());
    }

    private int getDay() {
        return date.getDayOfMonth();
    }

    private String getDayOfWeek() {
        return date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREA);
    }

    private void validateRange(int date) {
        if (date < MIN_DATE || date > MAX_DATE) {
            throw new InputException(ExceptionMessage.INVALID_DATE);
        }
    }
}
