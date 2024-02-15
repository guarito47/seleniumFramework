package bitconsulting.TestComponents;

import bitconsulting.Resources.ExtentReporterNG;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

//first we rewrite the methods in interface ITestListener to listening
/*we need driver, and method to take screenshot from the actual statement
 of the driver, so we declare our super class BaseTest by extends*/
public class ReportListeners extends BaseTest implements ITestListener {
    ExtentTest test;
    ExtentReports extent = ExtentReporterNG.getReporterObject();
    // this avoid to have concurrency issues by dont share same ExtentTest test
    // by the config of parallel feature in suite xml
    // this will create a thread id for that test
    ThreadLocal<ExtentTest> safeThread = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ITestListener.super.onTestStart(result);
        test= extent.createTest(result.getMethod().getMethodName());
        safeThread.set(test); //this assing a unique thread id to test
    }
    @Override
    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);
        safeThread.get().log(Status.PASS, "custom msg when pass");
    }
    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);
        safeThread.get().log(Status.FAIL, "custom msg when fails");
        //also we can print the java console error in the report
        //now also instead of using test, as we protect safeThread the test now
        //we use this wraper safeThread to refer our private ExtentTest test
        safeThread.get().fail(result.getThrowable());
        /*next we get the screenShot and we write initially like this
        String filePath = takeScreenShot(result.getMethod().getMethodName());
        but due to takeScreenshot handle files/locations, it asks for throws IOException
        but is not allowed in override methods, that's why we use try catch
        same aplies for getting the driver from result
        */
        /*
  //but onTestFailure has (ITestResult result) the result contains an alive driver
  //a driver placed in the exact moment of the failure, we will use that
        * */
        try {
            /*we use getclass to look driver at level of the class , because driver is an
            * attibute at level class not method level*/
            driver = (WebDriver)result.getTestClass().getRealClass()
                    .getField("driver").get(result.getInstance());
        } catch (Exception e) {
          e.printStackTrace();
        }
        String filePath;
        try {
            filePath = takeScreenShot(result.getMethod().getMethodName(), driver);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //now we have the screenshot in the filepath, now we pass this to the report
        safeThread.get().addScreenCaptureFromPath(filePath);
    }

    @Override //this listener runs when all the test finish and is the right one to
    // place as well extent.flush() so the report will not still waiting for more test
    //and will create the report with all the information recovered
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
        extent.flush();
    }
}
