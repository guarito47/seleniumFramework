package bitconsulting.PageObjects;

import bitconsulting.ReusableComponents.ReusableComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class RegistrationPage extends ReusableComponent {

    WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "firstName")
    WebElement firstName;

    @FindBy(id = "lastName")
    WebElement lastName;

    @FindBy(id = "userEmail")
    WebElement email;

    @FindBy(id = "userPassword")
    WebElement password;

    @FindBy(id = "confirmPassword")
    WebElement confirmPassword;

    @FindBy(id = "register")
    WebElement registerButton;

    WebElement registrationSuccessMessage;

    public void enterFirstName(String fName) {
        firstName.sendKeys(fName);
    }

    public void enterLastName(String lName) {
        lastName.sendKeys(lName);
    }

    public void enterEmail(String userEmail) {
        email.sendKeys(userEmail);
    }

    public void enterPassword(String userPassword) {
        password.sendKeys(userPassword);
    }

    public void enterConfirmPassword(String confirmPwd) {
        confirmPassword.sendKeys(confirmPwd);
    }

    public void clickRegister() {
        registerButton.click();
    }

    public String getSuccessMessage() {
        waitForWebElementToAppear(registrationSuccessMessage);
        return registrationSuccessMessage.getText();
    }

    public void completeRegistration(WebDriver driver) {
        // Fill in the input fields
        driver.findElement(By.id("firstName")).sendKeys("John");
        driver.findElement(By.id("lastName")).sendKeys("Doe");
        driver.findElement(By.id("userEmail")).sendKeys("john.doe@example.com");
        driver.findElement(By.id("userMobile")).sendKeys("1234567890"); // Phone number
        driver.findElement(By.id("userPassword")).sendKeys("Password123");
        driver.findElement(By.id("confirmPassword")).sendKeys("Password123");

        // Select occupation from dropdown
        WebElement occupationDropdown = driver.findElement(By.cssSelector("select[formcontrolname='occupation']"));
        Select selectOccupation = new Select(occupationDropdown);
        selectOccupation.selectByVisibleText("Engineer");

        // Select gender radio button
        WebElement maleRadioButton = driver.findElement(By.cssSelector("input[type='radio'][value='Male']"));
        maleRadioButton.click();

        // Check the "I am 18 years or Older" checkbox
        WebElement ageCheckbox = driver.findElement(By.cssSelector("input[type='checkbox'][formcontrolname='required']"));
        ageCheckbox.click();

        // Click the Register button
        driver.findElement(By.id("login")).click();
    }
}