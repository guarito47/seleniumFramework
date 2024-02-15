package bitconsulting.PageObjects;

import bitconsulting.ReusableComponents.ReusableComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends ReusableComponent {

    WebDriver driver;

    public CartPage(WebDriver driver) {
        super(driver);
        this.driver= driver;
        PageFactory.initElements(driver, this);
    }

    //we retrieve the h3 web elements that contains
    // the text names of the products in the cart
    @FindBy(css = ".cart h3")
    List<WebElement> cartProducts;
    @FindBy(css = ".subtotal button")
    WebElement checkoutBtn;

     public Boolean verifyProductAdded(String productName){

         //we verify if the productName exist in the List of h3's that's why we use anyMatch
         // if we want the return the element that matches then we use filter
         Boolean match= cartProducts.stream().anyMatch(product->
                        product.getText().equalsIgnoreCase(productName));
         return match;
    }

    public CheckoutPage goToCheckoutPage(){
         checkoutBtn.click();
         return new CheckoutPage(driver);
    }




}
