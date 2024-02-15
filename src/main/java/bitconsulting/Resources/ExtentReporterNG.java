package bitconsulting.Resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

//we wrap the creation of the objects needed to make the report in a class
public class ExtentReporterNG {
    //we declare static to get the Extent return without instance an Object
    public static ExtentReports getReporterObject(){
        String path = System.getProperty("user.id")+"\\reports\\index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Web Automation Results");
        reporter.config().setDocumentTitle("Test Results");

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);//here we link both cores
        extent.setSystemInfo("Tester", "Edwin Guarachi");
        return extent;
    }
}
