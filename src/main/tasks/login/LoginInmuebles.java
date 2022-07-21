package main.tasks.login;

import com.aventstack.extentreports.Status;
import main.actions.*;
import main.helpers.common.CommonComponent;
import main.helpers.dataUtility.ScreenShotHelper;
import main.tasks.mainMenu.MainMenu;
import main.ui.MainMenuUI;
import main.ui.loginUI.LoginUI;
import org.openqa.selenium.WebDriver;

public class LoginInmuebles {

    public static void as(WebDriver driver, String user, String password) {
        LogTime.start();
        Enter.text(driver, LoginUI.userInput, user);
        Enter.text(driver, LoginUI.passwordInput, password);
        ScreenShotHelper.takeScreenShotAndAdToHTMLReport(driver, Status.INFO, "Login");
        Click.on(driver, LoginUI.loginButton);
        LogWrite.with("Generar Login... OK");
        if(MainMenu.isPageMainDisplayed(driver, MainMenuUI.optionUser)){
            CommonComponent.RecordInLog(" paso la pagina");
        }else{
            ScreenShotHelper.takeScreenShotAndAdToHTMLReport(driver, Status.INFO, "Login Mensaje Alert Error");
            LogWrite.with("Login con Mensaje Alert Error");
            acceptAlertError.on(driver);
            password = setNewPasswords.with(password);
            Enter.text(driver, LoginUI.passwordInput, password);
            WaitUntilElement.isVisible(driver,LoginUI.loginButton);
            Click.on(driver, LoginUI.loginButton);
        }
        LogTime.end();
    }


}
