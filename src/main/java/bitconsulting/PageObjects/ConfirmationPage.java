package bitconsulting.PageObjects;

import bitconsulting.ReusableComponents.ReusableComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ConfirmationPage extends ReusableComponent {

    WebDriver driver;

    public ConfirmationPage(WebDriver driver) {
        super(driver);
        this.driver= driver;
        PageFactory.initElements(driver, this);
    }

    //we retrieve the h3 web elements that contains
    // the text names of the products in the cart
    @FindBy(css = ".hero-primary")
    WebElement confirmationLabel;
     public String getConfirmationText(){
        return confirmationLabel.getText();
     }





}
