package bitconsulting.Test;

import bitconsulting.PageObjects.CartPage;
import bitconsulting.PageObjects.ProductCatalogue;
import bitconsulting.TestComponents.BaseTest;
import bitconsulting.TestComponents.RetryFailTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;
// the conversion of standAloneTest to framework using page Objects
// page factory and more
public class ErrorValidationsTest extends BaseTest {

    //for retry a test, because we already have ReportListeners in the testng.xml
    //we cant add more listeners there so we need to pass as parameter in the
    //annotation giving the class implementation
    @Test (groups = {"errorHandling"}, retryAnalyzer = RetryFailTest.class)
    public void errorLogin() throws IOException, InterruptedException {
        //errorLogin makes sure that shows the correct message when fail login
        String productName = "ZARA COAT 3";
        //we don need to catch the productCatalogue obj from loginapp
        //because its empty due to dont reach the product catalogue page
        landingPage.loginApp("eguarachi@yahoo.com", "wrongPsw");
        Assert.assertEquals("Incorrect email or password.", landingPage.getErrorLoginMsg());
    }
    @Test //after login and add product it goes to cart page and look for a invalid product
    public void productExistValError() throws InterruptedException {
        String productName = "ZARA COAT 3";
        ProductCatalogue productCatalogue= landingPage.loginApp("eguarachi@yahoo.com", "Pa$$w0rd");
        productCatalogue.addProductToCart(productName);
        CartPage cartPage = productCatalogue.goToCartPage();
        Assert.assertFalse(cartPage.verifyProductAdded("wrong productName to fail"));

    }


}
