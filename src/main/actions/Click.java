package main.actions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Click{
	public static void on(WebDriver webDriver, By locator) {
        WebDriverWait wait = new WebDriverWait(webDriver, 60);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();	
	}
}