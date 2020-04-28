package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

import static com.codeborne.selenide.Selenide.$$;

public class RecommendationsPage {

    private ElementsCollection allProductsBlock = $$("[data-qaid='product_block']");
    private ElementsCollection choseBtn = $$("[data-qaid=\"buy-button\"]");

    public CurrentRecommendationProduct currentRecommendationProduct;

    @Step
    public RecommendationsPage isLoadedRecommendationsPage() {
        allProductsBlock.get(0).waitUntil(Condition.visible, 30000);
        return this;
    }

    @Step
    private void closePromo(){
        if (Selenide.$("[id='js-promo-img']").isDisplayed()){
            Selenide.$("[id='js-promo-close-btn']").click();
        }
    }

    @Step
    public void choseToByeProductById(Integer id) {
        WebDriver driver = WebDriverRunner.getWebDriver();
        Actions actions = new Actions(driver);
        List<WebElement> elements = driver.findElements(By.cssSelector("[data-qaid='product_block']"));
        closePromo();
        actions.moveToElement(elements.get(id)).build().perform();
        choseBtn.get(id).waitUntil(Condition.visible,5000).click();
    }

}
