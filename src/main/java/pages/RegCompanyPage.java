package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import helpers.RandomData;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import static com.codeborne.selenide.Selenide.$;

public class RegCompanyPage extends BaseFormLoginRegistration {

    private SelenideElement seoBlock = $("[class='lp-seo']");

    @Step
    public RegCompanyPage isLoaded() {
        Assert.assertTrue("Seo block should be visible ", seoBlock.waitUntil(Condition.visible, 30000).isDisplayed());
        return this;
    }

    @Step
    public RegCompanyPage fillCompanyData(UserForPromUaTests user) {
        fillEmail(user.getEmail());
        fillEPassword(user.getPassword());
        btnSubmit.click();
        return this;
    }

    public static class RegCompanyMobile {

        @Step
        private void fillCompanyName() {
            Selenide.$$("[name='company_name']").filterBy(Condition.visible).get(0)
                    .sendKeys("T E S T " + RandomData.getRandomPhoneNumber(5));
        }

        @Step
        private void fillPhoneNumber() {
            Selenide.$$("[name='phone']").filterBy(Condition.visible).get(0)
                    .sendKeys("91" + RandomData.getRandomPhoneNumber(7));
        }

        @Step
        private void fillEmail(String email) {
            new BaseFormLoginRegistration().fillEmail(email);
        }


        @Step
        private void fillPassword(String password) {
            new BaseFormLoginRegistration().fillEPassword(password);
        }

        @Step
        private void alreadySellProductsOrServices() {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions builder = new Actions(driver);
            WebElement el = driver.findElement(By.cssSelector("[name=\"sales\"]"));
            builder.moveToElement(el).click(el);
            Action mouseoverAndClick = builder.build();
            mouseoverAndClick.perform();

            WebElement el2 = driver.findElement(By.cssSelector("[value=\"426\"]"));
            el2.click();
            builder.moveToElement(el2).click(el2);
            mouseoverAndClick.perform();
        }

        @Step
        public void fillAndRegCompanyData(String email, String password) {
            fillCompanyName();
            fillEmail(email);
            fillPassword(password);
            fillPhoneNumber();
            alreadySellProductsOrServices();
            new BaseFormLoginRegistration().btnSubmitMobile.click();
            $("[class=\"PlaceholderEmpty__container--lZwQ-\"]").waitUntil(Condition.visible, 20000);
        }

    }
}
