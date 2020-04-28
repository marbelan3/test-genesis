package pages;

import com.codeborne.selenide.*;
import helpers.AllureUIUtil;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;

import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.lang.Thread.sleep;

public class HomePagePromUa {

    private RecommendationsPage recommendationsPage;

    private SelenideElement reg = $("[data-qaid='reg_element']");
    private SelenideElement searchField = $("[name='search_term']");
    private SelenideElement logo = $(".x-header__logo-img");
    private SelenideElement popUpRegCustomerOrCompany = $("[class='white__popper--s5-D9']");
    private SelenideElement regCompanyBtn = $("[data-qaid='reg_as_company_btn']");
    private SelenideElement searchButton = $("[data-qaid='search_button']");
    private ElementsCollection autocompleteList = $$("[data-qaid='suggest_item']");
    private ElementsCollection languages = $$("[class='headerLanguage__text--2XAay'");
    private SelenideElement createCompanyWithoutPopUp = $("[class='x-button__text']");

    @Step
    public HomePagePromUa isLoadedHomePage() {
        Assert.assertTrue("Home page should be visible ", logo.waitUntil(Condition.visible, 300000).isDisplayed());
        return this;
    }

    @Step
    public HomePagePromUa choseLanguage(String languageCode) {
        languages.findBy(Condition.text(languageCode)).click();
        logo.waitUntil(Condition.visible, 200000);
        return this;
    }


    @Step
    public void searchCurrentProductAndCheckAutocomplete(String productName) {
        searchField.scrollTo().sendKeys(productName);
        Assert.assertTrue("Autocomplete should be visible ", autocompleteList.get(0).waitUntil(Condition.visible, 10000).isDisplayed());
        for (SelenideElement selenideElement : autocompleteList) {
            Logger.getGlobal().log(Level.INFO, "Text from autocomplete is : " + selenideElement.getText());
            AllureUIUtil.paramNameValue("Text from autocomplete is : " + selenideElement.getText().toLowerCase() + "and should contains : " + productName);
            Assert.assertTrue("Every element from autocomplete should have test name value text : " + productName,
                    selenideElement.getText().toLowerCase().contains(productName));
        }
        searchButton.click();
    }

    @Step
    public void openRegPopUp() {
        /****************Handle unexpected redirect*************/
        $("body").waitUntil(Condition.visible,15000);
        if ($("[class=\"lp-vertical-form__input\"]").isDisplayed()) {
            Selenide.open(Configuration.baseUrl);
        }
        /********************************************************/
        for (int i = 0; i < 10; i++) {
            reg.waitUntil(Condition.visible, 3000).click();
            Selenide.sleep(1000);
            if (popUpRegCustomerOrCompany.is(Condition.visible)) {
                regCompanyBtn.waitUntil(Condition.visible, 2000).click();
                return;
            } else {
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Logger.getGlobal().log(Level.WARNING, "Button is not clicked, try again ");
            }
        }
        throw new NoSuchElementException("Button is not available");
    }

    @Step
    public void goToRegCompanyPage() {
        openRegPopUp();
        new RegCompanyPage().isLoaded();
    }

    public static class MobileHomePage {
        private SelenideElement createCompanyMobile = $$("[type='button']").get(0);

        @Step
        public MobileHomePage isLoadedHomePageMobile() {
            Assert.assertTrue("Home page should be visible ", createCompanyMobile.waitUntil(Condition.visible, 200000).isDisplayed());
            return this;
        }

        @Step
        public void createCompanyMobile() {
            createCompanyMobile.scrollTo().click();
        }


    }
}
