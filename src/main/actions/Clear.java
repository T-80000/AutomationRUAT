package main.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Clear {
    public static void on(WebDriver webDriver, By locator, int timeSedond) {
        WebDriverWait wait = new WebDriverWait(webDriver, timeSedond);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        element.clear();
    }
}
