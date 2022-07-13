package main.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Enter {

	public Enter() {
	}
	
    public static void text(WebDriver webDriver, By localizador, String texto){
        WebDriverWait wait = new WebDriverWait(webDriver, 60);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(localizador));
        element.clear();
        element.sendKeys(texto);

    }
}
