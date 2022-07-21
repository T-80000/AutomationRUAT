package main.tasks;

import main.actions.LogEnvironment;
import main.actions.LogWrite;
import main.helpers.common.*;
import main.helpers.common.Inmuebles.ConstantsInmuebles;
import main.helpers.dataUtility.AccesExcel;
import main.tasks.login.LoginInmuebles;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

/**
 * @description Clase para iniciar el aplicativo
 * @author Faustina Chambi Camata
 * @date 14/07/2022
 * */
public class ApplicationExcel {
    protected String               url; //url de acceso
    protected String               app; //Aplicativo Inmuebles, Vehículos, Gestión de Usuarios, etc.
    protected String               municipio;
    protected String               usuario;
    protected boolean              usarDatosAmbienteTest; //usar excel de casos prueba
    protected WebDriver            driverApp;
    protected WebDriverWait        wait;
    protected AccesExcel           accesExcel;
    protected StringBuilder        mensajeError;

    public AccesExcel getAccesExcel() {
        return accesExcel;
    }

    public void setAccesExcel(AccesExcel accesExcel) {
        this.accesExcel = accesExcel;
    }

    public String getUsuario(int i) {
        if(this.accesExcel.getParametro("USUARIO").get(i).contains("."))
        {
            return this.accesExcel.getParametro("USUARIO").get(i).substring(0, this.accesExcel.getParametro("USUARIO").get(i).indexOf("."));
        }
        else
        {
            return this.accesExcel.getParametro("USUARIO").get(i);
        }
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public ApplicationExcel() {
        this.usarDatosAmbienteTest      = false;
        this.mensajeError               = new StringBuilder(" ERROR: ");
    }
    public String getApp() {
        return this.app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    /**
     * @description fijar url del ambiente
     * @return
     */
    public String geturl() {
        AccesExcel archivoConfiguracion = null;
        if(this.url== null)
        {
                    if ( !this.usarDatosAmbienteTest)
                        archivoConfiguracion = new AccesExcel( ConstantsInmuebles.ARCHIVO_DATOS_PAGINA, Constants.HOJA_DATOS_O_PARAMETRO_AMBIENTE );
                    else
                        archivoConfiguracion = new AccesExcel( ConstantsInmuebles.ARCHIVO_DATOS_PRUEBA, Constants.HOJA_DATOS_O_PARAMETRO_AMBIENTE );

            seturl(archivoConfiguracion.getParametro(Constants.HOJA_DATOS_O_PARAMETRO_AMBIENTE).get(0));
        }
        return this.url;
    }

    public void seturl(String ambiente) {
        this.url = ambiente;
    }

    protected void openApplication(String url)
    {
        Set<String> ventanas = null;

        this.driverApp.get(url);                      // Abre el navegador y redirige a una URL.
        this.wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        LogWrite.with("Tiempo Inicialización (navegador): ");                                                                         //.concat(getTiempoTranscurrido(this.tiempoInicioTotal)));
        ventanas = this.driverApp.getWindowHandles();
        this.driverApp.switchTo().window(ventanas.toArray()[0].toString()).close();
        this.driverApp.switchTo().window(ventanas.toArray()[1].toString());
        LogWrite.with("Abriendo aplicativo...");
        this.driverApp.manage().window().setPosition(new org.openqa.selenium.Point(1500, 0));
        this.driverApp.manage().window().maximize();
    }
    //void  startApplication(String Aplicativo){
    public  <E extends ApplicationExcel> void startApplication(WebDriver driver, WebDriverWait wait){
        long        tiempoEjecucionInicial = 0;
        String      municipioActual        = "";
        String      municipioAnterior      = "";
        boolean     iniciarSesion          = true;
       //tiempoEjecucionInicial = System.currentTimeMillis();
        for(int fila_i = 0; fila_i < this.accesExcel.getNroDatosPrueba(); fila_i ++)
        {
            try
            {
                municipioActual        = this.accesExcel.getParametro(Constants.PARAMETRO_MUNICIPIO).get(fila_i);
                municipioAnterior      = fila_i > 0 ? this.accesExcel.getParametro(Constants.PARAMETRO_MUNICIPIO).get(fila_i - 1): "";
                iniciarSesion          = municipioActual.equals(municipioAnterior) ? false: true;
                this.cargarParametros(fila_i);
                if(iniciarSesion)
                {
                    this.driverApp = driver;
                    this.wait = wait;
                    this.iniciarAplicacion(this.geturl(), this.getMunicipio(), this.getUsuario(fila_i));
                }
            }
            catch(AssertionError falloFlujo)
            {
                processFail(falloFlujo);
            }
            catch(Exception generarExcepcion)
            {
                //procesarExcepcionInmuebles(generarExcepcion);
            }
            finally
            {
                LogWrite.with ( Constants.CADENA_FIN_ITERACION );
                try
                {
                    //volverMenuPrincipal ( );
                }
                catch ( Exception generarExcepcion ) { }
            }
        }
        LogWrite.with("TIEMPO EJECUCIÓN TOTAL: ".concat(CommonComponent.formatearTiempoEjecucion(System.currentTimeMillis() - tiempoEjecucionInicial)));
        LogWrite.with(Constants.CADENA_FIN_EJECUCION_TOTAL );
    }

    public void iniciarAplicacion(String url, String municipio, String usuario) throws Exception {
        openApplication(url);
        LogEnvironment.on("AMBIENTE: "+ url);
        LoginInmuebles.as(driverApp, usuario+".LPZ",Constants.CONTRASENIA_DEFECTO);
        //autentificar(getAbreviacionAlcaldia(municipio), usuario, String.valueOf(usuario.charAt(0)).concat(Constantes.CONTRASENIA_DEFECTO));
    }


    /*
     * @description
     * Se utilizó Java Reflection para inicializar los atributos de los page Objects con los datos establecidos en el
     * archivo de datos.
     * @param fila_i
     */
    public void cargarParametros ( int fila_i )
    {
        Field                atributoClase      = null;
        Object               objetoDatosExcel   = null;
        Object               camposExcel        = null;
        Method               metodoGetClaves    = null;
        Method               metodoGetParametro = null;
        String               nombreAtributo     = "";
        ArrayList < String > listaParametros    = null;

        try
        {
            Field atributoDatosExcel = this.getClass ( ).getSuperclass ( ).getDeclaredField ( "accesExcel" );

            try
            {
                objetoDatosExcel = ( Object ) atributoDatosExcel.get ( this );

                try
                {
                    metodoGetClaves = objetoDatosExcel.getClass ( ).getMethod ( "getClaves" );

                    try
                    {
                        camposExcel = metodoGetClaves.invoke ( objetoDatosExcel );

                        for ( String campoExcel_i: ( ArrayList < String > ) camposExcel )
                        {
                            atributoClase = null;

                            if( !campoExcel_i.equals( "VOLVER " ) && !campoExcel_i.equals( "USUARIO" ) )
                            {
                                nombreAtributo = CommonComponent.eliminarAcentos ( campoExcel_i.toLowerCase ( ) );
                                nombreAtributo = CommonComponent.formatearNombreAtributo ( nombreAtributo );

                                if( esAtributo ( this.getClass ( ).getSuperclass ( ), nombreAtributo ) )
                                {
                                    atributoClase = this.getClass ( ).getSuperclass ( ).getDeclaredField ( nombreAtributo );
                                }
                                else if( esAtributo ( this.getClass ( ), nombreAtributo ) )
                                {
                                    atributoClase = this.getClass().getDeclaredField (nombreAtributo);
                                }

                                metodoGetParametro = objetoDatosExcel.getClass ( ).getMethod ( "getParametro", String.class );
                                listaParametros    = ( ArrayList < String > ) metodoGetParametro.invoke ( objetoDatosExcel, campoExcel_i );
                                atributoClase.setAccessible(true);

                                if ( atributoClase.getType ( ).toString ( ).equals( "int" ) )
                                {
                                    atributoClase.set ( this,  Integer.parseInt( listaParametros.get ( fila_i ) ) );
                                }
                                else
                                {
                                    atributoClase.set ( this,  listaParametros.get ( fila_i ) );
                                }
                            }
                        }

                    }
                    catch (InvocationTargetException invocacionExcepcion)
                    {
                        invocacionExcepcion.printStackTrace ( );
                    }
                }
                catch (NoSuchMethodException noEncuentraMetodoExcepcion)
                {
                    noEncuentraMetodoExcepcion.printStackTrace ( );
                }
            }
            catch (IllegalArgumentException | IllegalAccessException operacionIlegalExcepcion)
            {
                operacionIlegalExcepcion.printStackTrace ( );
            }
        }
        catch (NoSuchFieldException | SecurityException noEncuentraCampoExcepcion)
        {
            noEncuentraCampoExcepcion.printStackTrace ( );
        }
    }

    /**
     * Valida si un atributo corresponde a una determinada clase. Utilizado por el método cargarParametros ( ).
     * @param clase
     * @param string
     */
    public boolean esAtributo ( Class < ? > clase, String string )
    {
        Field atributos [] = clase.getDeclaredFields ( );
        for (Field atributo_i : atributos)
        {
            if (atributo_i.getName ( ).equals ( string ) )
                return true;
        }
        return false;
    }
    /**
     * Método procesarFallo(): Registra en log y en consola el fallo ocurrido, reinicia la variable mensajeError y se vuelve al menú
     * principal.
     * @param falloFlujo
     */
    public void processFail ( AssertionError falloFlujo )
    {
        LogWrite.with ( falloFlujo.getMessage ( ).replaceAll ( "\n", " " ) );
        if ( cuadroDialogoEstaPresente ( ) )
        {
            this.driverApp.switchTo ( ).alert ( ).accept ( );
        }
        this.mensajeError.delete ( 8, this.mensajeError.length ( ) );
    }
    public boolean cuadroDialogoEstaPresente()
    {
        try
        {
            this.driverApp.switchTo().alert();
            return true;
        }
        catch(NoSuchElementException noEncuentraAlertaExcepcion)
        {
            return false;
        }
        catch(NoAlertPresentException alertNoPresenteExcepcion)
        {
            return false;
        }
    }
}
