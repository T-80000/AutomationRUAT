package main.tasks.login;

import main.actions.Click;
import main.actions.IsDisplayed;
import main.ui.loginUI.LoginUI;
import org.openqa.selenium.WebDriver;

public class acceptAlertError {
    public static void on(WebDriver webDriver){
        Click.on(webDriver,LoginUI.buttonAcceptAlertError);
    }

}
