package main.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUntilElement {
    public static final int     TIME_SECOND                = 30;
    public static boolean isPresent(WebDriver webDriver, By locator){
        try{
            WebDriverWait wait = new WebDriverWait(webDriver,TIME_SECOND);
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isVisible(WebDriver webDriver, By locator){
        try{
            WebDriverWait wait = new WebDriverWait(webDriver,TIME_SECOND);
            wait.until(ExpectedConditions.visibilityOf(webDriver.findElement(locator)));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static void isVisibleElementOr(WebDriver webDriver, By locator1, By locator2,int timeSecond){
        WebDriverWait wait = new WebDriverWait(webDriver,timeSecond);
        wait.until (ExpectedConditions.or( ExpectedConditions.visibilityOfElementLocated (locator1),
                                           ExpectedConditions.visibilityOfElementLocated (locator2)));
    }
    public static void isVisibleElementOr(WebDriver webDriver, By locator1, By locator2){
        WebDriverWait wait = new WebDriverWait(webDriver,TIME_SECOND);
        wait.until (ExpectedConditions.or( ExpectedConditions.visibilityOfElementLocated (locator1),
                ExpectedConditions.visibilityOfElementLocated (locator2)));
    }

    public static void isVisibleElement(WebDriver webDriver,By locator,int timeSecond){
        WebDriverWait wait= new WebDriverWait(webDriver,timeSecond);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public static void isVisibleElement(WebDriver webDriver,By locator){
        WebDriverWait wait= new WebDriverWait(webDriver,TIME_SECOND);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void isClikeableOf(WebDriver webDriver,By locator,int timeSecond){
        WebDriverWait wait= new WebDriverWait(webDriver,timeSecond);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    public static void isClikeableOf(WebDriver webDriver,By locator){
        WebDriverWait wait= new WebDriverWait(webDriver,TIME_SECOND);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    public static void isInvisibleElement(WebDriver webDriver,By locator,int timeSecond){
        WebDriverWait wait= new WebDriverWait(webDriver,timeSecond);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    public static void isInvisibleElement(WebDriver webDriver,By locator){
        WebDriverWait wait= new WebDriverWait(webDriver,TIME_SECOND);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
}
