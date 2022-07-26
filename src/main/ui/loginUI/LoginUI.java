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
	//------------------ nuevo ----------------------------
	public static By ID_CAMPO_USUARIO                  = By.id("login");
	public static By ID_CAMPO_CONTRASENIA              = By.id("password");
	public static By ID_BOTON_INGRESAR                 = By.id("btn-ingresar");
	public static By ID_VENTANA_MODAL                  = By.id("modal_window");
	public static By ID_MENSAJE_MODAL                  = By.id("mensaje-modal");
	public static By PATH_BOTON_VENTANA_MODAL          = By.xpath("//*[@id='modal_window']/div[2]/div[2]/button");
	public static By ID_CAMPO_BUSQUEDA_MODULO          = By.id("txt-buscar-modulo");
	public static By ID_CAMPO_CONTRASENIA_ACTUAL       = By.id("password-actual");
	public static By ID_CAMPO_CONTRASENIA_NUEVA        = By.id("password-nuevo");
	public static By ID_CAMPO_CONTRASENIA_CONFIRMACION = By.id("password-confirmacion");
	public static By ID_BOTON_GRABAR                   = By.id("btn-grabar");

}
