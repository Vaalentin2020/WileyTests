package wiley;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.BeforeClass;

public class TestBase {

    @BeforeClass
    public void setUp() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/win/chromedriver.exe");
        } else if (os.contains("linux")) {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/linux/chromedriver");
        }
        Configuration.browser = "chrome";
        Configuration.baseUrl = BaseHelper.getProperty("baseUrl");
        System.setProperty("chromeoptions.args", "--no-sandbox");
    }
}
