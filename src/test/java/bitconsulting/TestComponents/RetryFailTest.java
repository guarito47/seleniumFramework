package bitconsulting.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
//we implements the Interface IRetryAnalyzer to create another listener
public class RetryFailTest implements IRetryAnalyzer {
    int count =0;
    int maxTry=1;
    //this example of interface shows that one instance of interface is runinng
    //for entire test avery time TW keeps the counts
    @Override
    //this only method keeps retrying the test that throws the fail
    //till you decide when its enough by returning false in the code
    public boolean retry(ITestResult iTestResult) {
        System.out.println("retry in action");
        if(count<maxTry){
            count++;
            return true;
        }
        return false;
    }
}
