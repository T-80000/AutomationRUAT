package main.helpers.dataUtility;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


public class ScreenShotHelper {
    public static String takeScreenShot(WebDriver webDriver){
        TakesScreenshot takesScreenshot = (TakesScreenshot)webDriver;
        String screenshot = takesScreenshot.getScreenshotAs(OutputType.BASE64);

        return screenshot;
    }

    public static void takeScreenShotAndAdToHTMLReport(WebDriver webDriver, Status status, String details){
        String imageBase64 = ScreenShotHelper.takeScreenShot(webDriver);

       try {
           ReportManager.getInstance().getTest().log(status, details,
                   MediaEntityBuilder.createScreenCaptureFromBase64String(imageBase64).build());
       }catch (Exception e){

       }

    }

    public static void setUpSuiteReport() throws Exception {
        ReportManager.init("C:\\Reports", "LoginSite");
    }
}
