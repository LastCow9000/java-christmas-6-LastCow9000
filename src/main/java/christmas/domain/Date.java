package christmas.domain;

import christmas.exception.ExceptionMessage;
import christmas.exception.InputException;
import java.time.LocalDate;

public class Date {
    private static final int MIN_DATE = 1;
    private static final int MAX_DATE = 31;

    private LocalDate date;

    private Date(int date) {
        validateRange(date);
        this.date = LocalDate.of(2023, 12, date);
    }

    public static Date from(int date) {
        return new Date(date);
    }

    private void validateRange(int date) {
        if (date < MIN_DATE || date > MAX_DATE) {
            throw new InputException(ExceptionMessage.INVALID_DATE);
        }
    }
}
