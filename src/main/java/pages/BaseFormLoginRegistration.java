package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import helpers.RandomData;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BaseFormLoginRegistration {
    SelenideElement form = $("[class='lp-header__form-cell']");
    SelenideElement email = $(By.name("email"));
    SelenideElement password = $(By.name("password"));
    SelenideElement btnSubmit = $("[class='lp-header__form-cell'] [type='submit']");
    SelenideElement btnSubmitMobile = $$("[type='submit']").get(0);

    @Step
    public BaseFormLoginRegistration isLoaded() {
        Assert.assertTrue("Registration form should be visible ",
                form.waitUntil(Condition.visible, 15000).isDisplayed());
        return this;
    }

    @Step
    public void fillEmail(String email) {
        this.email.waitUntil(Condition.visible, 2000).sendKeys(email);
    }

    @Step
    public void fillEPassword(String password) {
        this.password.waitUntil(Condition.visible, 2000).sendKeys(password);
    }


}
