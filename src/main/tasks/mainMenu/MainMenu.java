package main.tasks.mainMenu;

import com.aventstack.extentreports.Status;
import main.actions.Click;
import main.actions.IsDisplayed;
import main.helpers.dataUtility.ScreenShotHelper;
import main.ui.MainMenuUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Driver;

public class MainMenu {
    public static boolean isVisible (WebDriver webDriver){
        return IsDisplayed.element(webDriver, MainMenuUI.optionUser);
    }


    public static boolean isPageMainDisplayed(WebDriver webDriver, By locator)
    {
        return IsDisplayed.element(webDriver, locator);

    }
    public static void enterProforma (WebDriver webDriver){
        ScreenShotHelper.takeScreenShotAndAdToHTMLReport(webDriver, Status.INFO,"Ingresando a Proforma");
        Click.on(webDriver, MainMenuUI.optionProform);
    }


}
