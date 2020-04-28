package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import helpers.RandomData;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class OneStepToRegistrationPage {

    private SelenideElement companyName = $(By.name("name"));
    private SelenideElement phoneNumber = $(By.name("phone"));
    private SelenideElement lastStepRegForm = $("[class='b-registration-step']");


    @Step
    public OneStepToRegistrationPage isLoadedFinishRegPage() {
        Assert.assertTrue("Second registration form should be visible ", lastStepRegForm
                .waitUntil(Condition.visible, 20000).isDisplayed());
        return this;
    }

    @Step
    public OneStepToRegistrationPage finishPreRegistration() {
        fillLastStepRegData();
        return this;
    }

    @Step
    private void fillCompanyName() {
        companyName.sendKeys("T E S T " + RandomData.getRandomPhoneNumber(5));
    }

    @Step
    private void fillPhoneNumber() {
        phoneNumber.sendKeys(RandomData.getRandomPhoneNumber(9));
    }

    @Step
    private void alreadySellProductsOrServices() {
        Selenide.$("[data-qaid='dd_checklist']").click();
        Selenide.$$("[data-qaid=\"dd_checklist\"] li").get(1).click();
    }

    @Step
    private void fillLastStepRegData() {
        alreadySellProductsOrServices();
        fillCompanyName();
        fillPhoneNumber();
    }

}
