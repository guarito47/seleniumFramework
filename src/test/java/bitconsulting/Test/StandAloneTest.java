package bitconsulting.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class StandAloneTest {
//eguarachi@yahoo.com  Pa$$w0rd
    //rahul rahulshettyacademy
    public static void main(String[] args) {
        String productName = "ZARA COAT 3";
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        //we will give a implicit wait globally fo all the actions that we will made
        //because the page bellow is quite slow
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://rahulshettyacademy.com/client");
        //we log into the page giving the following credentials
        driver.findElement(By.id("userEmail")).sendKeys("eguarachi@yahoo.com");
        driver.findElement(By.id("userPassword")).sendKeys("Pa$$w0rd");
        driver.findElement(By.id("login")).click();
        //giving 5 secs till products divs .mb-3 (product) appears
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

        //once in the catalogue page we grab all the items by div class name in common
        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
        //list has the property of doing stream, so we are filtering  by a condition where
        // the tag "b" inside each "product" is equal to "zara coat 3", is theres many results
        //we get only the first, in case is empty we return null, all in the WebElement "prod"
        WebElement prod= products.stream().filter(product->
                product.findElement(By.cssSelector("b")).getText().equals(productName))
                .findFirst().orElse(null);
        //having just one div of a product we can search only in this short html for the second
        // buttom "Add to cart" we will use css traverse parent to child to reach the button
        // and because there's another button"view" we will use "last of type" helper
        prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

        //with the same wait object we can load more waits, like wait till appears green message and
        //till disappear loading animation
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
        //this line using invisibilityOfElementLocated have bugs wait till 10 second by implicitd wait
        //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
        //we calso can use invisibilityOf but uses Web Element thats why we use from driver.findElement...
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

        //we retrieve the h3 web elements that contains the text names of the products in the cart
        List<WebElement> cartProducts= driver.findElements(By.cssSelector(".cart h3"));
        //we verify is the productName exist in the List of h3's just want to know that's why we use anyMatch
        //if we want the element that matchs then we use filter that will return the webEllement
        Boolean match = cartProducts.stream().anyMatch(
                cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
        Assert.assertTrue(match);
        driver.findElement(By.cssSelector(".subtotal button")).click();
        //to select a country field and because is a suggestion text/autofill, we need to use actions
        Actions act = new Actions(driver);
        act.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india")
                .build().perform();
        //to give a short time to display the suggestions we will  set an explicit wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));

        //css and helper nth-of-type that gets the index result .ta-item:nth-of-type(2)
        //driver.findElement(By.cssSelector(".ta-item:nth-of-type(2)")).click();
        //xpath and  using the index of teh result desired (//button[contains(@class, 'ta-item')])[2]
        driver.findElement(By.xpath("(//button[contains(@class, 'ta-item')])[2]")).click();

        driver.findElement(By.cssSelector(".action__submit")).click();

        String confirmation= driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertTrue( confirmation.equalsIgnoreCase("Thankyou for the order."));

    }

}
