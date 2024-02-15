package bitconsulting.PageObjects;

import bitconsulting.ReusableComponents.ReusableComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ProductCatalogue extends ReusableComponent {

    WebDriver driver;

    public ProductCatalogue(WebDriver driver) {
        super(driver);
        this.driver= driver;
        PageFactory.initElements(driver, this);
    }
    //IMPORTANT: pageFactory works only for elements in the entire page because driver
    //handle whole page, for webElement results that are only a portion of the page don't works
    // maybe we can do it but the page factory pattern is not using in the right way
    //List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
    //as the expected result declared by List<WebElement>, page Factory intelligence
    // knows to use findElements instead of findElement
    //mb-3 is the div container of each product of the list of products in the page
    @FindBy(css = ".mb-3")
    List<WebElement> products;

    @FindBy(css = ".ng-animating")
    WebElement loadingAnimation;

    By elementToWait = By.cssSelector(".mb-3");
    By addToCartBtnItem=  By.cssSelector(".card-body button:last-of-type");
    By greenProductAddedMsg = By.cssSelector("#toast-container");

    public List<WebElement> getProductList(){
        //once in the catalogue page we grab all the products by div class .mb-3 name in common
        //but before that we give 5 secs till all the product loads
        waitForElementToAppear(elementToWait);
        // we already got the list of products by page factory declare before as products
        return products;
    }

    public WebElement getProductByName(String productName){
        //list has the property of doing stream, so we are filtering  by a condition where
        // the tag "b" inside each "product" is equal to "zara coat 3", is theres many results
        //we get only the first, in case is empty we return null, all in the WebElement "prod"
        //IMPORTANT: we use the method getProductList() and not directly products
        //because getProductList loads the var products if you use products before call
        // the getProductList method can be cause error by have empty list
        WebElement prod= getProductList().stream().filter(product->
                        product.findElement(By.cssSelector("b")).getText().equals(productName))
                .findFirst().orElse(null);
        return prod;
    }

    public void addProductToCart(String productName) throws InterruptedException {
        WebElement prod = getProductByName(productName);
        // addToCartBtnItem is By.cssSelector(".card-body button:last-of-type")
        //that helps to refer this button on other methods
        //having specific div ".card-body" of the product desired search inside the specific div
        // Having inside 2 buttons "view" and "Add to cart" we will use css traverse parent to child
        // and we use the helper "last of type" to get the last of the results
        prod.findElement(addToCartBtnItem).click();
        //reusing waitForElementToAppear that we inheritance by ReusableComponent
        // we can load more waits, just sending the By var in our case wait till appears green message
        // and till disappear loading animation
        waitForElementToAppear(greenProductAddedMsg);
        waitForElementToDisappear(loadingAnimation);
    }
}
