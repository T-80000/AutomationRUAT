package main.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GetText {
    public static String of(WebDriver webDriver, By locator,int timeSecond) {
        WebDriverWait wait = new WebDriverWait(webDriver,timeSecond);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        return element.getText();
    }
}
