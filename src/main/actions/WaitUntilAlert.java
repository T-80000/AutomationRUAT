package main.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUntilAlert {
    public static final int     TIME_SECOND                = 5;
    public static boolean isPresent(WebDriver webDriver){
       try{
           WebDriverWait wait = new WebDriverWait(webDriver,TIME_SECOND);
           wait.until(ExpectedConditions.alertIsPresent());
           return true;
       }catch (Exception e){
           e.printStackTrace();
           return false;
       }
    }
    public static boolean isPresent(WebDriver webDriver, int timeSecond){
        try{
            WebDriverWait wait = new WebDriverWait(webDriver,timeSecond);
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
