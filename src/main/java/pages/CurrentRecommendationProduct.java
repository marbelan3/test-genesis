package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CurrentRecommendationProduct {

    private ElementsCollection productBlock = $$("[data-qaid='product_block']");
    private By priceSector = By.cssSelector("[class=\"x-gallery-tile__price-holder\"]");
    private ElementsCollection choseBtn = $$("[data-qaid=\"buy-button\"]");

    public ElementsCollection getProductBlock() {
        return productBlock;
    }
}

