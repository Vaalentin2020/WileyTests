package wiley;

public class BaseHelper {
    public static String getProperty(String baseUrl) {
        return System.getenv(baseUrl) != null ? System.getenv(baseUrl) : "https://www.wiley.com";
    }
}
