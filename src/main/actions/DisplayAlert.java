package main.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DisplayAlert {
    public static void toAcept(WebDriver webDriver){
        webDriver.switchTo().alert().accept();
    }

    public static void cancel (WebDriver webDriver){
        webDriver.switchTo().alert().dismiss();
    }
}
