package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class UserCabinet {
    private SelenideElement userCapabilitiesBlock = $("[data-qaid='content_block']");
    private SelenideElement userProfileSettings = $$("[class='b-main-menu__title']").last();

    @Step
    public UserCabinet isLoadedUserCabinet() {
        Assert.assertTrue("User capabilities block should be visible", userCapabilitiesBlock
                .waitUntil(Condition.visible, 10000).isDisplayed());
        return this;
    }

    @Step
    public UserSettings goToUserProfileSettings() {
        if (userProfileSettings.isDisplayed()) {
            userProfileSettings.click();
        }
        return new UserSettings().isLoadedUserSettings();
    }


    public class UserSettings {
        private ElementsCollection userDataBlocks = $$("[class='b-profile-page__content']");
        private SelenideElement deleteUserProfileButton = $("[class='b-profile-page']").lastChild().find(By.partialLinkText("дал"));
        private SelenideElement delUserWindow = $("[class='overlay__dialog__g7iAKW']");
        private SelenideElement passwordField = $("[type='password']");
        private SelenideElement submitDeleteUserProfile = $("[data-qaid='button']");

        @Step
        private UserSettings isLoadedUserSettings() {
            userDataBlocks.forEach(SelenideElement::isDisplayed);
            return this;
        }


        @Step
        public void deleteUserProfile(String userPassword) {
            deleteUserProfileButton.scrollTo().click();
            delUserWindow.waitUntil(Condition.visible, 5000);
            passwordField.sendKeys(userPassword);
            submitDeleteUserProfile.click();
            Selenide.sleep(5000);
        }

    }
}
