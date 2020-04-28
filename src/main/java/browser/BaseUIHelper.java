package browser;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseUIHelper {
    @Step
    public static void setAntiDistilCookie() {
        while (Objects.isNull(WebDriverRunner.getWebDriver().manage().getCookieNamed("x_test"))) {
            WebDriverRunner.getWebDriver().manage().addCookie(new Cookie.Builder("x_test", "test").build());
            Selenide.sleep(20);
        }
        Logger.getGlobal().log(Level.INFO, "COOKIE WAS SET");
    }

    @Step
    public static void stopLoadingPage() {
        Selenide.executeJavaScript("document.removeEventListener");
        Selenide.sleep(100);
        Selenide.executeJavaScript("document.removeEventListener");
        Selenide.executeJavaScript("window.stop();");
        Selenide.sleep(100);
        Logger.getGlobal().log(Level.INFO, "BEFOREUNLOAD EVENT STOPED");
    }

    @Step
    public static String getExtendedUserToken() {
        Selenide.$("body").waitUntil(Condition.visible, 50000);
        return String.valueOf(WebDriverRunner.getWebDriver().manage().getCookieNamed("__io_visit_expire.26.day").getValue());
    }

    @Step
    public static String getUserId() {
        String[] userId = String.valueOf(WebDriverRunner.getWebDriver().manage().getCookieNamed("lid").getValue()).split("-");
        return userId[userId.length-1];
    }

    @Step
    public static String getCsrfToken() {
        return String.valueOf(WebDriverRunner.getWebDriver().manage().getCookieNamed("csrf_token").getValue());

    }
}