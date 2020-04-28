package my.company.tests;

import browser.BaseMobileTest;
import com.codeborne.selenide.Selenide;
import helpers.AllureUIUtil;
import mailsac.MailSacAction;
import org.junit.Test;
import pages.HomePagePromUa;
import pages.RegCompanyPage;
import pages.UserForPromUaTests;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PromMobTest extends BaseMobileTest {

    private final UserForPromUaTests userForPromUaTestsMobile = new UserForPromUaTests();

    @Test
    public void registerApproveAndCheckMailMobileTest() {
        Selenide.open("/");
        new HomePagePromUa.MobileHomePage()
                .isLoadedHomePageMobile()
                .createCompanyMobile();
        new RegCompanyPage.RegCompanyMobile()
                .fillAndRegCompanyData(userForPromUaTestsMobile.getEmail(), userForPromUaTestsMobile.getPassword());

        String activationUrl = new MailSacAction(userForPromUaTestsMobile.getEmail()).getActivateUrl();
        AllureUIUtil.paramNameValue("Activation URL is : " + activationUrl);
        Logger.getGlobal().log(Level.INFO, "Activation URL is : " + activationUrl);
        Selenide.open(activationUrl);

    }
}
