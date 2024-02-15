package bitconsulting.ReusableComponents;

import bitconsulting.PageObjects.CartPage;
import bitconsulting.PageObjects.OrdersPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ReusableComponent {
    WebDriver driver;
    public ReusableComponent(WebDriver driver) {

        this.driver= driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "[routerlink*='cart']")
    WebElement cartLinkHeader; //link page cart from header
    @FindBy(css = "[routerlink*='myorders']")
    WebElement ordersLinkHeader; // link page orders from header
    public CartPage goToCartPage(){
        //we use this method to go basket cart icon to go products added page
        cartLinkHeader.click();
        //and instead to create the objects in the testcase class, we do it
        //internally wherever superclass/ObjectPage class where redirect a new page
        CartPage cartPage = new CartPage(driver);
        return cartPage;
    }

    public OrdersPage goToOrdersPage(){

        ordersLinkHeader.click();
        OrdersPage ordersPage = new OrdersPage(driver);
        return ordersPage;
    }

    public void waitForElementToAppear(By findBy){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
    }

    public void waitForWebElementToAppear(WebElement ele){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(ele));
    }
    //with can create a new wait object that we use to load more waits,
    // into the driver
    public void waitForElementToDisappear(WebElement ele) throws InterruptedException {

        Thread.sleep(1000);
        //this line using invisibilityOfElementLocated have bugs, it wait's till 10 second by implicitd wait
        //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
        //so we will use invisibilityOf but uses Web Element thats why avoid to use from driver...
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //wait.until(ExpectedConditions.invisibilityOf(ele));
    }
}
