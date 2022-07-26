package main.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Enter {
    public static final int     TIME_SECOND                = 5;
	
    public static void text(WebDriver webDriver, By localizador, String texto,int timeSecond){
        WebDriverWait wait = new WebDriverWait(webDriver, timeSecond);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(localizador));
        element.clear();
        element.sendKeys(texto);

    }
    public static void text(WebDriver webDriver, By localizador, String texto){
        WebDriverWait wait = new WebDriverWait(webDriver, TIME_SECOND);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(localizador));
        element.clear();
        element.sendKeys(texto);

    }
}
