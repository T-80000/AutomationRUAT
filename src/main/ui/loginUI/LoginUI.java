package main.ui.loginUI;

import org.openqa.selenium.By;

public class LoginUI {
	//-------------- login --------------------
	public static By userInput = By.id("login");
	public static By passwordInput = By.id("password");
	public static By loginButton = By.id("btn-ingresar");
	//--------------- en pagina principal -----
	public static By logoMainPage = By.id("logo-aplicacion");
	public static By icoProforma = By.className("ico_prof");
	//---------------- alert error ------------
	public static By buttonAcceptAlertError = By.className("botones-modal");

}
