package main.tasks.login;

import com.aventstack.extentreports.Status;
import main.actions.*;
import main.helpers.dataUtility.ScreenShotHelper;
import main.ui.loginUI.LoginUI;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public class LoginInmuebles {

    public static void authenticate (WebDriver driver, String abreviacionMunicipio, String usuario, String contrasenia )
    {
        String mensajeAlerta = "";
        String codigoError   = "";

        WaitUntilElement.isVisibleElementOr(driver,LoginUI.ID_VENTANA_MODAL,LoginUI.ID_CAMPO_USUARIO,60);
        if(IsDisplayed.element(driver,LoginUI.ID_MENSAJE_MODAL,5))
        {
            mensajeAlerta = GetText.of(driver,LoginUI.ID_MENSAJE_MODAL,5);
            if ( mensajeAlerta.contains ( "versi�n del Aplicativo no es v�lida" ) )
            {
                ScreenShotHelper.takeScreenShotAndAdToHTMLReport(driver, Status.INFO, "version app no valida");
                Log.recordInLog( " ".concat ( mensajeAlerta ) );
                Click.on(driver,LoginUI.PATH_BOTON_VENTANA_MODAL,5);
                WaitUntilElement.isVisibleElement(driver,LoginUI.ID_MENSAJE_MODAL,5);
            }
            else if ( mensajeAlerta.contains ( "C�digo de Error" ) )
            {
                ScreenShotHelper.takeScreenShotAndAdToHTMLReport(driver, Status.INFO, "Codigo de Error");
                //codigoError = mensajeAlerta.substring ( mensajeAlerta.indexOf ( ": " ) + 1 );
                //throw new AssertionError ( procesarErrorAplicativo ( codigoError ) );
                System.out.println("registro del error en la base de datos");
            }
        }
        try
        {
            WaitUntilElement.isVisibleElementOr(driver,LoginUI.ID_CAMPO_USUARIO,LoginUI.ID_CAMPO_CONTRASENIA,60);
        }
        catch ( TimeoutException tiempoExcedidoAutentificacion )
        {
           /* this.mensajeError.append ( "El aplicativo demor� mas de 1 minuto en el proceso de autentificaci�n." );
            throw new AssertionError ( this.mensajeError.toString ( ) );*/
            System.out.println("registro del error en la base de datos");
        }
        if ( usuario.contains ( "." ) )
        {
            usuario = usuario.substring ( 0, usuario.indexOf ( "." ) );
        }
        Clear.on(driver, LoginUI.ID_CAMPO_USUARIO,5);
        Enter.text(driver,LoginUI.ID_CAMPO_USUARIO, usuario.concat ( "." ).concat ( abreviacionMunicipio ),5);
        Enter.text(driver,LoginUI.ID_CAMPO_CONTRASENIA,contrasenia,5);
        ScreenShotHelper.takeScreenShotAndAdToHTMLReport(driver, Status.INFO, "Proceso de login.");
        Click.on(driver,LoginUI.ID_BOTON_INGRESAR,5);
        Log.recordInLog ( "Proceso de autentificaci�n: ..." );
        WaitUntilElement.isVisibleElementOr(driver,LoginUI.PATH_BOTON_VENTANA_MODAL,LoginUI.ID_CAMPO_BUSQUEDA_MODULO,5);
        try
        {
            if(IsDisplayed.element(driver,LoginUI.ID_MENSAJE_MODAL,5))
            {
                ScreenShotHelper.takeScreenShotAndAdToHTMLReport(driver, Status.INFO, "Error en la logueo. ");
                mensajeAlerta = GetText.of(driver,LoginUI.ID_MENSAJE_MODAL,5);
                Click.on(driver,LoginUI.PATH_BOTON_VENTANA_MODAL,5);
                WaitUntilElement.isInvisibleElement(driver,LoginUI.ID_MENSAJE_MODAL,5);

                if ( mensajeAlerta.contains ( "Ha expirado el plazo para el cambio de su contrase�a" ) )
                {
                    ScreenShotHelper.takeScreenShotAndAdToHTMLReport(driver, Status.INFO, "Ha expirado");
                    WaitUntilElement.isClikeableOf(driver,LoginUI.ID_CAMPO_CONTRASENIA_ACTUAL,5);
                    Enter.text(driver,LoginUI.ID_CAMPO_CONTRASENIA_ACTUAL,contrasenia,5);
                    if ( contrasenia.contains( "8" ) )
                    {
                        //contrasenia = contrasenia.substring ( 0, contrasenia.length ( ) - 1 );
                        contrasenia = setNewPasswords.with(contrasenia);
                    }
                    else
                    {
                        //contrasenia = contrasenia.concat ( "8" );
                        contrasenia = setNewPasswords.with(contrasenia);
                    }
                    Enter.text(driver,LoginUI.ID_CAMPO_CONTRASENIA_NUEVA,contrasenia,5);
                    Enter.text(driver,LoginUI.ID_CAMPO_CONTRASENIA_CONFIRMACION,contrasenia,5);
                    Click.on(driver,LoginUI.ID_BOTON_GRABAR,5);
                    WaitUntilElement.isVisibleElement(driver,LoginUI.PATH_BOTON_VENTANA_MODAL,5);
                    mensajeAlerta = GetText.of(driver,LoginUI.ID_MENSAJE_MODAL,5);
                    Click.on(driver,LoginUI.PATH_BOTON_VENTANA_MODAL,5);
                    if ( mensajeAlerta.contains ( "cambio de contrase�a se realiz� correctamente" ) )
                    {
                        Log.recordInLog( " ".concat ( mensajeAlerta ) );
                    }
                    else
                    {
                        ScreenShotHelper.takeScreenShotAndAdToHTMLReport(driver, Status.INFO, "Error en el login");
                        /*this.mensajeError.append ( mensajeAlerta );
                        throw new AssertionError ( this.mensajeError.toString ( ) );*/
                        System.out.println("se adiciona un mensaje de error");
                    }
                }
                else if ( mensajeAlerta.contains ( "no se encuentra registrado para el uso de esta aplicaci�n" ) ||
                        mensajeAlerta.contains ( "Usuario y/o Contrase�a es inexistente" ) )
                {
                    ScreenShotHelper.takeScreenShotAndAdToHTMLReport(driver, Status.INFO, "No se encuentra registrado para el uso de la aplicaci�n o usuario inexistente");
                    /*this.mensajeError.append ( mensajeAlerta );
                    throw new AssertionError ( this.mensajeError.toString ( ) );*/
                    System.out.println("se adiciona un mensaje de error");
                }
                else if ( mensajeAlerta.contains ( "Error al intentar autentificarse" ) )
                {
                    if ( !contrasenia.contains ( "F1234567$" ) )
                    {
                        Log.recordInLog  ( " Se intentar� la autentificaci�n nuevamente con la contrase�a alternativa..." );
                        contrasenia = setNewPasswords.with(contrasenia);
                        authenticate ( driver, abreviacionMunicipio, usuario, contrasenia);
                    }
                    else
                    {
                        throw new AssertionError ( " Dato Usuario o contrase�a incorrecto." );  // Se genera una excepci�n si no funcionan ninguna de los 2 intentos.
                    }
                }
            }
            else
            {
                Log.recordInLog ( "Proceso de autentificaci�n: OK." );
            }
        }
        catch ( TimeoutException tiempoEsperaExcedido )
        {
           /* this.mensajeError.append ( "Tiempo de espera excedido." );
            throw new AssertionError ( this.mensajeError.toString ( ) );*/
            System.out.println("se adiciona un mensaje de error");
        }
    }



}
