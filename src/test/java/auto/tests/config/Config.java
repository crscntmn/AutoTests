package auto.tests.config;

public final class Config {

    private Config() {
    }

    private static String getConfigValue(String name, String defaultValue) {
        String systemProperty = System.getProperty(name);

        if (systemProperty != null && !systemProperty.isBlank()) {
            return systemProperty;
        }

        String environmentVariable = System.getenv(name);

        if (environmentVariable != null && !environmentVariable.isBlank()) {
            return environmentVariable;
        }

        return defaultValue;
    }

    public static String getBaseUrl() {
        return getConfigValue("BASE_URL", "https://demowebshop.tricentis.com/");
    }
}
