package auto.tests.testdata;

import java.util.UUID;

public class TestData {
    public static final String PASSWORD = "buben12345";
    public static final String WRONG_PASSWORD = "buben12346";
    public static final String NAME = "name";
    public static final String LASTNAME = "lastName";

    public static String generateEmail() {
        return "buben" + UUID.randomUUID() + "@mail.ru";
    }

    public static String generateWrongEmail() {
        return "buben" + UUID.randomUUID();
    }
}
