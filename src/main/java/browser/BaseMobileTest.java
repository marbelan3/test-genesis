package browser;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.junit.TextReport;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.CustomMobileEventListener;
import helpers.ReportWatcher;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BaseMobileTest {
    static {
        Logger.getGlobal().log(Level.INFO, "Initialization BaseUiTest");
        Configuration.baseUrl = "https://prom.ua";
    }

    @Rule
    public TextReport textReport = new TextReport();

    @Rule
    public ReportWatcher customWatcher = new ReportWatcher().onFailedTest(true).onSucceededTest(true);


    @BeforeClass
    public static void beforeClass() {
        WebDriver chrome;
        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", "iPhone X");
        Configuration.browser = WebDriverRunner.CHROME;
        Configuration.pageLoadStrategy = "none";
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
        chrome = new ChromeDriver(chromeOptions);
        WebDriverRunner.setWebDriver(chrome);


        Configuration.clickViaJs = true;
        Configuration.pageLoadStrategy = "none";
        Configuration.holdBrowserOpen = false;
        Configuration.timeout = 6000;
        Configuration.screenshots = true;
        Configuration.savePageSource = true;
        Configuration.pageLoadStrategy = "normal";
        Configuration.pollingInterval = 100;
        Configuration.fastSetValue = false;
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(false).savePageSource(false));
        WebDriverRunner.addListener(new CustomMobileEventListener());

    }


    @SuppressWarnings("Duplicates")
    @After
    public void afterEachTest() {
        while (WebDriverRunner.getWebDriver().getWindowHandles().size() != 1) {
            Selenide.switchTo().window(0);
            try {
                Selenide.clearBrowserCookies();
                Selenide.clearBrowserLocalStorage();
                Selenide.switchTo().window(0).close();
            } catch (Exception e) {
                Logger.getGlobal().log(Level.WARNING, "CLEARING BROWSER COOKIES AND STORAGE WAS FAILED");
            }
        }

    }

    @AfterClass
    public static void closeBrowser(){
        Selenide.closeWebDriver();
    }


}

