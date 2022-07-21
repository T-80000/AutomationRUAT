package main.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class IsVisible {
    public static boolean element(WebDriver webDriver, By locator){
        return webDriver.findElement((locator)).isDisplayed();
    }
}
