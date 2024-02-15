package bitconsulting.Test;

import bitconsulting.PageObjects.*;
import bitconsulting.TestComponents.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
// the conversion of standAloneTest to framework using page Objects
// page factory and more
public class SubmitOrderTest extends BaseTest {
    //eguarachi@yahoo.com  Pa$$w0rd
    //rahul rahulshettyacademy
    String productName= "ZARA COAT 3";

    @Test(dataProvider = "getLoginDataUsingJson", groups = {"Purchase"})
    /* we specify the data provider and also the group that belongs the test
    now we declare that we expect 1 hashMap each run */
    public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException {
        /*by using the annotation beforeMethod in LaunchApp method from baseTest
        where we create driver, waits and Landing Page no more need to be here
        LandingPage landingPage= launchApp();*/
        ProductCatalogue productCatalogue= landingPage.loginApp(input.get("email"), input.get("psw"));
        List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(input.get("prodName"));
        CartPage cartPage = productCatalogue.goToCartPage();

        Assert.assertTrue(cartPage.verifyProductAdded(input.get("prodName")));
        CheckoutPage checkoutPage= cartPage.goToCheckoutPage();
        checkoutPage.selectCountry("India");
        ConfirmationPage confirmationPage= checkoutPage.goToConfirmationPage();
        String confirmMessage = confirmationPage.getConfirmationText();
        Assert.assertTrue( confirmMessage.equalsIgnoreCase("Thankyou for the order."));

    }

    @Test (dependsOnMethods = {"submitOrder"})
    public void OrderExistInHistoryPage(){

        ProductCatalogue productCatalogue= landingPage.loginApp("eguarachi@yahoo.com", "Pa$$w0rd");
        OrdersPage ordersPage= productCatalogue.goToOrdersPage();
        Assert.assertTrue( ordersPage.verifyOrderPresent(productName));
    }

    @DataProvider //using hashMap
    public Object[][] getLoginDataUsingHashMap(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("email", "eguarachi@yahoo.com");
        map.put("psw", "Pa$$w0rd");
        map.put("prodName", "ZARA COAT 3");

        HashMap<String, String> map2 = new HashMap<String, String>();
        map2.put("email", "eguarachi@yahoo.com");
        map2.put("psw", "Pa$$w0rd");
        map2.put("prodName", "ADIDAS ORIGINAL");
        //always will be Object matrix only change the content
        return new Object[][] {{map}, {map2}};
    }

    @DataProvider //using Json
    public Object[][] getLoginDataUsingJson() throws IOException {
        
        List<HashMap<String, String> > data =
                getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\bitconsulting\\Data\\PurchaseOrder.json");
        //now because we have 2 hashmaps in our list, we send those by using get(i)
        return new Object[][] {{data.get(0)}, {data.get(1)}};

    }
    @DataProvider //using Matrix Object
    public Object[][] getLoginDataUsingMatrix(){ //intead to use hardcode for user psw and product

        return new Object[][] {{"eguarachi@yahoo.com", "Pa$$w0rd","ZARA COAT 3" },
                                {"eguarachi@yahoo.com", "Pa$$w0rd","ADIDAS ORIGINAL"}};
//and their method declaration would be
// @Test(dataProvider = "getLoginDataUsingMatrix", groups = {"Purchase"})
//public void submitOrder(String email, String psw, String productName)
    }


}
