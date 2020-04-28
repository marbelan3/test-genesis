package my.company.tests;

import browser.BaseUiClass;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import helpers.AllureUIUtil;
import helpers.RetryRule;
import io.qameta.allure.Issue;
import io.qameta.allure.junit4.DisplayName;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import junitparams.naming.TestCaseName;
import mailsac.MailSacAction;
import mapper.PromUaMapperCsv;
import org.junit.*;
import org.junit.runner.RunWith;
import pages.*;
import sun.rmi.runtime.Log;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.refresh;

@RunWith(JUnitParamsRunner.class)
public class PromWebTest extends BaseUiClass {

//
//    @Rule
//    public RetryRule rule = new RetryRule(2);

    private final UserForPromUaTests userForPromUaTests = new UserForPromUaTests();

    @Test
    @Issue("Test Prom.ua WEB")
    @DisplayName("Search and check autocomplete PROM.UA TEST WEB")
    @FileParameters(value = "classpath:testGoods.csv", mapper = PromUaMapperCsv.class)
    @TestCaseName(value = "{method}: {params}")
    public void searchAndCheckAutocompleteTest(String productName) {
        Selenide.open("/");
        new HomePagePromUa()
                .isLoadedHomePage()
                .choseLanguage("RU")
                .searchCurrentProductAndCheckAutocomplete(productName);
        new RecommendationsPage()
                .isLoadedRecommendationsPage()
                .choseToByeProductById(1);

        int browserPageCount = WebDriverRunner.getWebDriver().getWindowHandles().size();
        Logger.getGlobal().log(Level.INFO, "Browser window count is : " + browserPageCount);
        AllureUIUtil.paramNameValue("Browser window count is : " + browserPageCount);
        if (WebDriverRunner.getWebDriver().getWindowHandles().size() != 1) {
            Selenide.switchTo().window(1);
        }
        new CartList()
                .isLoadedCartList()
                .compareChosenProductAndTestProduct(productName);

        if (browserPageCount > 1) {
            Selenide.closeWindow();
            Selenide.switchTo().window(0);
        }
    }



    @Test
    @Issue("Test Prom.ua WEB")
    @DisplayName("Register Company PROM.UA TEST WEB")
    @TestCaseName(value = "{method}: {params}")
    public void regAsCompanyCheckAndApproveMail() {
        Selenide.open("/");
        new HomePagePromUa()
                .isLoadedHomePage()
                .goToRegCompanyPage();
        new RegCompanyPage()
                .isLoaded()
                .fillCompanyData(userForPromUaTests);
        new OneStepToRegistrationPage()
                .isLoadedFinishRegPage()
                .finishPreRegistration();

        String url = WebDriverRunner.url();
        Assert.assertTrue(url.contains("/cms/edit-checklist"));
        Logger.getGlobal().log(Level.INFO, "URL is OK !!!!!!!!!!");

        String activationUrl = new MailSacAction(userForPromUaTests.getEmail()).getActivateUrl();
        AllureUIUtil.paramNameValue("Activation URL is : " + activationUrl);
        Logger.getGlobal().log(Level.INFO, "Activation URL is : " + activationUrl);
        Selenide.open(activationUrl);

        new UserCabinet()
                .isLoadedUserCabinet()
                .goToUserProfileSettings()
                .deleteUserProfile(userForPromUaTests.getPassword());

    }

}

