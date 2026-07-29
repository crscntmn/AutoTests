package auto.tests.testdata;

import java.util.UUID;

public class TestData {
    public static final String PASSWORD = "baben12345";
    public static final String SHORT_PASSWORD = "buben1";
    public static final String INVALIDATE_PASSWORD = "buben";
    public static final String WRONG_PASSWORD = "buben12346";
    public static final String EMPTY_PASSWORD = "";
    public static final String NAME = "name";
    public static final String EMPTY_NAME = "";
    public static final String LASTNAME = "lastName";
    public static final String EMPTY_LASTNAME = "";

    public static String generateEmail() {
        return "buben" + UUID.randomUUID() + "@mail.ru";
    }

    public static String generateWrongEmail() {
        return "buben" + UUID.randomUUID();
    }

    public static String generateEmptyEmail() {
        return "";
    }

    public static String generateEmailWithSpace() {
        return "bu ben" + UUID.randomUUID() + "@mail.ru";
    }

    public static String generateEmailWithCaps() {
        return "BUBEN" + UUID.randomUUID() + "@MAIL.RU";
    }
}
