package bitconsulting.PageObjects;

import bitconsulting.ReusableComponents.ReusableComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class OrdersPage extends ReusableComponent {

    WebDriver driver;

    public OrdersPage(WebDriver driver) {
        super(driver);
        this.driver= driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "tr td:nth-child(3)")
    List<WebElement> ordersPresent;

     public Boolean verifyOrderPresent(String productName){


         Boolean match= ordersPresent.stream().anyMatch(product->
                        product.getText().equalsIgnoreCase(productName));
         return match;
    }






}
