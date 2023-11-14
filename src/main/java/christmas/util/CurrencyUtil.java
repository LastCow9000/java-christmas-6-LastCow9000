package christmas.util;

import java.text.NumberFormat;
import java.util.Currency;

public class CurrencyUtil {
    private static final String CURRENCY_CODE = "KRW";
    private static final String WON = "원";

    private CurrencyUtil() {
    }

    public static String formatToKor(int amount) {
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
        Currency currency = Currency.getInstance(CURRENCY_CODE);
        String withCurrencySymbol = currencyInstance.format(amount) + WON;

        return withCurrencySymbol.replace(currency.getSymbol(), "");
    }
}
