package main.actions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Click{
    public static final int     TIME_SECOND                = 5;

	public static void on(WebDriver webDriver, By locator,int timeSecond) {
        WebDriverWait wait = new WebDriverWait(webDriver, timeSecond);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();	
	}
    public static void on(WebDriver webDriver, By locator) {
        WebDriverWait wait = new WebDriverWait(webDriver, TIME_SECOND);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }
}