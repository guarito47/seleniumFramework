package bitconsulting.PageObjects;

import bitconsulting.ReusableComponents.ReusableComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends ReusableComponent {

    WebDriver driver;

    public LandingPage(WebDriver driver) {
        super(driver);
        this.driver= driver;
        PageFactory.initElements(driver, this);
    }

    // PageFactory : we can simplify the code like this
    //WebElement userEmail = driver.findElement(By.id("userEmail"));
    // by using PageFactory, the return FindBy will be stored in the object
    // bellow the declaration in this case userEmail
    @FindBy(id = "userEmail")
    WebElement userEmail;
    @FindBy(id = "userPassword")
    WebElement password;
    @FindBy(id = "login")
    WebElement submitLogin;

    @FindBy(css = "[class*='flyInOut']")
    WebElement loginErrorMsg; //red div message from login error

    public void goToLogin(){
        driver.get("https://rahulshettyacademy.com/client");
    }
    public ProductCatalogue loginApp(String email, String psw){
        userEmail.sendKeys(email);
        password.sendKeys(psw);
        submitLogin.click();
        ProductCatalogue productCatalogue = new ProductCatalogue(driver);
        return  productCatalogue;
    }

    public String getErrorLoginMsg(){

        waitForWebElementToAppear(loginErrorMsg);
        return loginErrorMsg.getText();
    }
}
