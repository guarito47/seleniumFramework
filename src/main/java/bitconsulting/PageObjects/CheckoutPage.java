package bitconsulting.PageObjects;

import bitconsulting.ReusableComponents.ReusableComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage extends ReusableComponent {

    WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver= driver;
        PageFactory.initElements(driver, this);
    }

    //we retrieve the h3 web elements that contains
    // the text names of the products in the cart
    @FindBy(css = "[placeholder='Select Country']")
    WebElement selectCountry;
    @FindBy(xpath = "(//button[contains(@class, 'ta-item')])[2]")
    WebElement chooseIndiaCountry;
    @FindBy(css = ".action__submit")
    WebElement placeOrderBtn;

    By listCountrySuggestionsList =By.cssSelector(".ta-results");
     public void selectCountry(String countryName){
         Actions act = new Actions(driver);
         act.sendKeys(selectCountry, countryName)
                 .build().perform();
         waitForElementToAppear(listCountrySuggestionsList);
         chooseIndiaCountry.click();
     }

     public ConfirmationPage goToConfirmationPage(){
         placeOrderBtn.click();
         return new ConfirmationPage(driver);
     }




}
