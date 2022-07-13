package main.tasks;

import main.actions.LogEnvironment;
import main.actions.LogTime;
import main.actions.LogWrite;
import com.aventstack.extentreports.Status;
import main.helpers.dataUtility.ScreenShotHelper;
import org.openqa.selenium.WebDriver;

import main.actions.Click;
import main.actions.Enter;
import main.ui.LoginUI;

public class Login
{
	  public static void as(WebDriver driver, String user, String password) {
		  LogEnvironment.on ("CALIDAD");
		  LogTime.start();
          Enter.text(driver, LoginUI.userInput, user);
		  Enter.text(driver, LoginUI.passwordInput, password);
		  ScreenShotHelper.takeScreenShotAndAdToHTMLReport(driver, Status.INFO,"Login");
		  Click.on(driver, LoginUI.loginButton);
		  LogWrite.with("Generar Login... OK");
		  LogTime.end();
	 }

}
 