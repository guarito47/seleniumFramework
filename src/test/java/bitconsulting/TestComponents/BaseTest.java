package bitconsulting.TestComponents;

import bitconsulting.PageObjects.LandingPage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class BaseTest { //now base test will create the driver, set implicit wait, etc
    public WebDriver driver;
    public LandingPage landingPage;
    public WebDriver initializeDriver() throws IOException {
//we create a globalData.properties file where we will have global parameters
//like browser type, TW we use the class Properties that handle this kind of file,
        Properties prop = new Properties();
//but we send the file as inputstream TW we need to use inputStream to parse the file
//instead to use your local machine GlobalData path , we use System.getProperty("user.dir")
//that will give the current project path and from there wi will give the specific location
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\bitconsulting\\Resources\\GlobalData.properties");
        prop.load(fis);

        //BaseTest only catch the browser type from file GlobalData .properties
        // now we have another option through Maven cmd using –D browser= chrome
        // so we will add this another way to set browser type value if its
        // launching from cmd using msven comands thi is the Old code
        // String browserType= prop.getProperty("browser"); and new one
        String browserType= System.getProperty("browser")!= null
                ? System.getProperty("browser")
                :prop.getProperty("browser");

        //if(browserType.equalsIgnoreCase("chrome")){
        if(browserType.contains("chrome")){ // having chromeheadless
            ChromeOptions options = new ChromeOptions();
            if (browserType.contains("headless")){
                //in case we choose chromeheadless
                options.addArguments("headless");
            }
            WebDriverManager.chromedriver().setup();
            //if dont choose headlees, optrions is empty so no affects
            driver = new ChromeDriver(options);
            //in headless mode to maximize window works in this way
            driver.manage().window().setSize(new Dimension(1440,900));
        }
        if (browserType.equalsIgnoreCase("firefox")){
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();

        }
        if (browserType.equalsIgnoreCase("edge")){
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();

        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        return driver;

    }
//  when run a group tests, and the methods have depends on or in this case
//  setup beforeMethod and afterMethod testng will skip to run those because
//  are not part of the group to test, VERY IMPORTANT  dont give the property
// (groups = {"errorHandling"}) because will be only part of the group to run
//  for any group that still to keep their before after Method we will use
    @BeforeMethod (alwaysRun = true)
    public LandingPage launchApp() throws IOException {

        driver=initializeDriver();
        landingPage = new LandingPage(driver);
        landingPage.goToLogin();
        return landingPage;
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown(){
        driver.close();
    }

    public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
        //we will use readFileToString method, that ask for the json file and the encoding format
        //teh result is a long string
        String jsonContent=
                FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        //System.out.println(jsonContent);
        //we add dependency Jackson Databind to map jsonContent into HashMap
        //using this obj from class ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        //we declare that we will return a List of hashmaps, dont forget 1 hashmap
        //contains user psw, product name
        List<  HashMap<String, String> > data =
                mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
                });
        return data;
    }

    //because this method is not called from a PageObject (that initialize the driver)
    //its called directly from the listener method and driver it's not initialized so its null
    //TW we will expect to receive a driver in this particular method
    public String takeScreenShot(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File screen= ts.getScreenshotAs(OutputType.FILE);
        File file=new File(System.getProperty("user.dir")+"\\reports\\"+testCaseName+".png");
        FileUtils.copyFile(screen, file);

        return System.getProperty("user.dir")+"\\reports\\"+testCaseName+".png";
    }
}
