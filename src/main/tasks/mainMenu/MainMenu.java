package main.tasks.mainMenu;

import com.aventstack.extentreports.Status;
import main.actions.Click;
import main.actions.IsDisplayed;
import main.actions.IsVisible;
import main.helpers.dataUtility.ScreenShotHelper;
import main.ui.MainMenuUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainMenu {
    public static boolean isVisible (WebDriver webDriver){
        return IsVisible.element(webDriver, MainMenuUI.optionUser);
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
