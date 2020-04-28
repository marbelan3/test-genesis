package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class CartList {

    private SelenideElement orderItem = $("[class='x-shc-group__order-items']");
    private By productDescription = By.cssSelector("[class='x-shc-item__title-link']");
    private SelenideElement cartBody = $("[class='qa-shoping-cart-list']");

    @Step
    public CartList isLoadedCartList() {
        Assert.assertTrue("Cart body should be visible : ", cartBody.waitUntil(Condition.visible, 15000).isDisplayed());
        return this;
    }

    @Step
    public CartList compareChosenProductAndTestProduct(String product) {
        Assert.assertTrue("Product description in cart should contains test product",
                $(this.productDescription).getText().toLowerCase().contains(product));
        return this;
    }

}