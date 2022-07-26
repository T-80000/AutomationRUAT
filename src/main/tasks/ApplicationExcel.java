package main.tasks;

import com.aventstack.extentreports.Status;
import main.actions.Log;
import main.actions.LogTime;
import main.helpers.common.*;
import main.helpers.common.Inmuebles.ConstantsINM;
import main.helpers.dataUtility.AccesExcel;
import main.helpers.dataUtility.ScreenShotHelper;
import main.tasks.login.LoginInmuebles;
import oracle.jdbc.pool.OraclePooledConnection;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

/**
 * @description Clase para iniciar el aplicativo con el Excel
 * @author Faustina Chambi Camata
 * @date 21/07/2022
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
    protected String               operacion;

    protected String               numeroInmueble;
    protected int                  numeroAccion;
    protected boolean              accionesYDerechos;
    protected String               accionInicio;
    protected String               accionFin;
    protected ArrayList < String > nombresArchivoReportes;


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

    /**
     * @return operacion
     */
    public String getOperacion()
    {
        return operacion;
    }

    /**
     * @param operacion to set
     */
    public void setOperacion(String operacion)
    {
        this.operacion = operacion;
    }
    /**
     * @return numeroInmueble
     */
    public String getNumeroInmueble()
    {
        return numeroInmueble;
    }

    /**
     * @param numeroInmueble
     */
    public void setNumeroInmueble(String numeroInmueble)
    {
        this.numeroInmueble = numeroInmueble;
    }

    /**
     * @return numeroAccion
     */
    public int getNumeroAccion ( )
    {
        return this.numeroAccion;
    }

    /**
     * @param numeroAccion
     */
    public void setNumeroAccion ( int numeroAccion )
    {
        this.numeroAccion = numeroAccion;
    }

    /**
     * @return accionInicio
     */
    public String getAccionInicio()
    {
        return accionInicio;
    }

    public ApplicationExcel() {
        this.usarDatosAmbienteTest      = false;
        this.mensajeError               = new StringBuilder(" ERROR: ");
        this.nombresArchivoReportes     = new ArrayList<String>();
        this.accionesYDerechos          = false;
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
                        archivoConfiguracion = new AccesExcel( ConstantsINM.ARCHIVO_DATOS_PAGINA, Constants.HOJA_DATOS_O_PARAMETRO_AMBIENTE );
                    else {
                        archivoConfiguracion = new AccesExcel(ConstantsINM.ARCHIVO_DATOS_PRUEBA, Constants.HOJA_DATOS_O_PARAMETRO_AMBIENTE);
                    }

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
        Log.recordInLog("Tiempo Inicialización (navegador): ");                                                                         //.concat(getTiempoTranscurrido(this.tiempoInicioTotal)));
        ventanas = this.driverApp.getWindowHandles();
        this.driverApp.switchTo().window(ventanas.toArray()[0].toString()).close();
        this.driverApp.switchTo().window(ventanas.toArray()[1].toString());
        Log.recordInLog("Abriendo aplicativo...");
        this.driverApp.manage().window().setPosition(new org.openqa.selenium.Point(1500, 0));
        this.driverApp.manage().window().maximize();
    }
    //void  startApplication(String Aplicativo){

    public  <E extends ApplicationExcel> void run(WebDriver driver, WebDriverWait wait){
        long        tiempoEjecucionInicial = 0;
        String      municipioActual        = "";
        String      municipioAnterior      = "";
        boolean     iniciarSesion          = true;
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
                    this.initApplication(this.geturl(), this.getMunicipio(), this.getUsuario(fila_i));
                }
                this.start();
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
               Log.recordInLog("-------------------------------");
                try
                {
                    volverMenuPrincipal ( );
                }
                catch ( Exception generarExcepcion ) { }
            }
        }
        LogTime.end();
        Log.endInLog(" Módulo (".concat(this.getClass().getSimpleName().toUpperCase().concat(")")));


    }
    public void volverMenuPrincipal()
    {
        final String ID_LOGO_APLICATIVO = "logo-aplicacion";

        this.driverApp.switchTo().parentFrame();
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(ID_LOGO_APLICATIVO)));
        //if (this.driver.findElement(By.id(ID_LOGO_APLICATIVO)).isSelected())
        //{
        this.driverApp.findElement(By.id(ID_LOGO_APLICATIVO)).click();
        //}
        this.wait.until(ExpectedConditions.presenceOfElementLocated(By.id(ID_LOGO_APLICATIVO)));
        this.driverApp.switchTo().defaultContent();
    }

    public void initApplication(String url, String municipio, String usuario) throws Exception {
        openApplication(url);
        Log.onEnvironment( this.getClass().getSimpleName().toUpperCase(), url);
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
    public void start()
    {
    }
    /**
     * Método procesarFallo(): Registra en log y en consola el fallo ocurrido, reinicia la variable mensajeError y se vuelve al menú
     * principal.
     * @param falloFlujo
     */
    public void processFail ( AssertionError falloFlujo )
    {
        Log.recordInLog( falloFlujo.getMessage ( ).replaceAll ( "\n", " " ) );
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
    public void iniciarModulo ( String subsistema, String menu, String modulo )
    {
        final String PATH_OPCIONES_SUBSISTEMA = "//*[@id='menu-lista']/ul/li";
        final String ID_IFRAME_PRINCIPAL      = "ifrm-contenido";
        final String ID_VENTANA_MODAL         = "modal_window";
        final String ID_MENSAJE_MODAL         = "mensaje-modal";

        String              rutaModulo                = "";
        String              pathOpcionesMenu          = "";
        String              pathOpcionesModulo        = "";
        String              pathOpcionesModuloDetalle = "";
        String              prefijoModulo             = "";
        boolean             subsistemaEncontrado      = false;
        boolean             opcionMenuEncontrado      = false;
        boolean             opcionModuloEncontrado    = false;
        List < WebElement > opcionesSubsistema        = null;
        List < WebElement > opcionesMenu              = null;
        List < WebElement > opcionesModulo            = null;
        List < WebElement > opcionesModuloDetalle     = null;

        this.wait.until ( ExpectedConditions.or (
                ExpectedConditions.presenceOfAllElementsLocatedBy ( By.xpath ( PATH_OPCIONES_SUBSISTEMA ) ),
                ExpectedConditions.visibilityOfElementLocated ( By.id ( ID_VENTANA_MODAL ) ) ) );

        if ( !this.driverApp.findElement ( By.id ( ID_VENTANA_MODAL ) ).isDisplayed ( ) )
        {
            opcionesSubsistema = this.driverApp.findElements ( By.xpath ( PATH_OPCIONES_SUBSISTEMA ) );
            pathOpcionesMenu   = PATH_OPCIONES_SUBSISTEMA.concat ( "[" );

            if ( modulo.contains( menu ) )
            {
                rutaModulo = CommonComponent.obtenerCadenaTipoTitulo ( modulo );
            }
            else
            {
                rutaModulo = menu.concat ( " " ).concat ( CommonComponent.obtenerCadenaTipoTitulo ( modulo ) );
            }

           Log.recordInLog("Iniciando módulo ".concat ( rutaModulo ).concat ( ": ..." ) );

            for ( int subsistema_i = 0; subsistema_i < opcionesSubsistema.size ( ); subsistema_i ++ )
            {
                if ( opcionesSubsistema.get ( subsistema_i ).getText ( ).equals ( subsistema ) )
                {
                    opcionesSubsistema.get ( subsistema_i ).click ( );
                    subsistemaEncontrado = true;
                    pathOpcionesMenu     = pathOpcionesMenu.concat ( String.valueOf ( subsistema_i + 1 ) ).concat ( "]/ul/li" );
                    opcionesMenu         = this.wait.until ( ExpectedConditions.presenceOfAllElementsLocatedBy (
                            By.xpath ( pathOpcionesMenu ) ) );

                    for ( int opcion_j = 0; opcion_j < opcionesMenu.size ( ); opcion_j ++ )
                    {
                        if ( opcionesMenu.get ( opcion_j ).getText ( ).equals ( menu ) )
                        {
                            opcionesMenu.get ( opcion_j ).click ( );
                            opcionMenuEncontrado = true;
                            pathOpcionesModulo   = pathOpcionesMenu.concat ( "[" ).concat ( String.valueOf ( opcion_j + 1 ) )
                                    .concat ( "]/ul/li" );

                            this.wait.until (
                                    ExpectedConditions.or (
                                            ExpectedConditions.presenceOfAllElementsLocatedBy ( By.xpath ( pathOpcionesModulo ) ),
                                            ExpectedConditions.attributeContains (
                                                    opcionesMenu.get ( opcion_j ).findElement ( By.tagName ( "a" ) ),
                                                    "class", "enlace" ) ) );

                            ( ( JavascriptExecutor ) this.driverApp ).executeScript (
                                    "arguments [ 0 ].scrollIntoView ( true );",
                                    opcionesMenu.get ( opcion_j ) );

                            opcionesModulo = this.driverApp.findElements ( By.xpath ( pathOpcionesModulo ) );

                            if ( opcionesModulo.size ( ) > 0 )
                            {
                                if ( modulo.contains ( " - " ) )
                                {
                                    prefijoModulo = modulo.substring ( 0, modulo.indexOf ( " -" ) );
                                    modulo        = modulo.substring ( modulo.indexOf ( "- " ) + 2 );
                                }

                                for ( int modulo_k = 0; modulo_k < opcionesModulo.size ( ); modulo_k ++ )
                                {
                                    if ( opcionesModulo.get ( modulo_k ).getText ( ).equalsIgnoreCase ( prefijoModulo ) )
                                    {
                                        opcionesModulo.get ( modulo_k ).click ( );
                                        pathOpcionesModuloDetalle = pathOpcionesModulo.concat ( "[" )
                                                .concat ( String.valueOf ( modulo_k + 1 ) )
                                                .concat( "]/ul/li" );

                                        opcionesModuloDetalle = this.wait.until (
                                                ExpectedConditions.presenceOfAllElementsLocatedBy (
                                                        By.xpath ( pathOpcionesModuloDetalle ) ) );

                                        for ( WebElement moduloDetalle_l: opcionesModuloDetalle )
                                        {
                                            if ( moduloDetalle_l.getText ( ).equalsIgnoreCase ( modulo ) )
                                            {
                                                moduloDetalle_l.findElement ( By.tagName ( "a" ) ).click ( );
                                                opcionModuloEncontrado = true;
                                                break;
                                            }
                                        }

                                        if ( opcionModuloEncontrado )
                                        {
                                            break;
                                        }
                                    }
                                    else
                                    {
                                        if ( opcionesModulo.get ( modulo_k ).getText ( ).equalsIgnoreCase ( modulo ) )
                                        {
                                            opcionesModulo.get ( modulo_k ).findElement ( By.tagName ( "a" ) ).click ( );
                                            opcionModuloEncontrado = true;
                                            break;
                                        }
                                    }
                                }
                            }
                            else
                            {
                                opcionesMenu.get ( opcion_j ).findElement ( By.tagName ( "a" ) ).click ( );
                                opcionModuloEncontrado = true;
                            }
                            break;
                        }
                    }
                    break;
                }
            }

            if ( !subsistemaEncontrado )
            {
                this.mensajeError.append ( "Subsistema " ).append ( subsistema ).append ( " no encontrado." );
                throw new AssertionError ( this.mensajeError.toString ( ) );
            }
            else if ( !opcionMenuEncontrado )
            {
                this.mensajeError.append ( "Opción " ).append ( menu ).append ( " no encontrado." );
                throw new AssertionError ( this.mensajeError.toString ( ) );
            }
            else if ( !opcionModuloEncontrado )
            {
                this.mensajeError.append ( "Módulo " ).append ( modulo ).append ( " no encontrado." );
                throw new AssertionError ( this.mensajeError.toString ( ) );
            }

            this.wait.until ( ExpectedConditions.frameToBeAvailableAndSwitchToIt (
                    this.driverApp.findElement ( By.id ( ID_IFRAME_PRINCIPAL ) ) ) );
           Log.recordInLog ( "Iniciando módulo ".concat ( rutaModulo ).concat ( ": OK." ) );
        }
        else
        {
            this.mensajeError.append ( this.driverApp.findElement ( By.id ( ID_MENSAJE_MODAL ) ).getText ( ) )
                    .append ( " Se perdió la sesión." );
            throw new AssertionError ( this.mensajeError.toString ( ) );
        }
    }
    public void busquedaInmueble ( String numeroInmueble )
    {
        final String ID_CAMPO_IDENTIFICADOR   = "txtIdentificador";
        final String ID_RADIO_NUMERO_INMUEBLE = "rbtCriterioNIM";

        String mensajeAlerta = "";

        this.wait.until ( ExpectedConditions.visibilityOfElementLocated ( By.id ( ID_CAMPO_IDENTIFICADOR ) ) ).sendKeys ( numeroInmueble );
        Log.recordInLog( "Búsqueda Inmueble: ..." );
        Log.recordInLog(  " Número Inmueble: ".concat ( numeroInmueble ).concat ( " ( " ).concat ( this.municipio ).concat ( " )" ) );
        this.driverApp.findElement ( By.id ( ID_RADIO_NUMERO_INMUEBLE ) ).click ( );
        ScreenShotHelper.takeScreenShotAndAdToHTMLReport(driverApp, Status.INFO, "Búsqueda Inmueble");
        this.driverApp.findElement ( By.id ( Constants.ID_NAME_BOTON_ACEPTAR ) ).click ( );

        try
        {
            this.wait.until ( ExpectedConditions.or ( ExpectedConditions.alertIsPresent ( ),
                    ExpectedConditions.visibilityOfElementLocated ( By.xpath ( Constants.PATH_TITULO_VISTA ) ),
                    ExpectedConditions.visibilityOfElementLocated ( By.xpath ( Constants.PATH_TITULO_VISTA.replaceAll ( "/form", "" ) ) ) ) );
        }
        catch ( UnhandledAlertException alertExcepcion ) { }

        if( cuadroDialogoEstaPresente ( ) )
        {
            mensajeAlerta = this.driverApp.switchTo ( ).alert ( ).getText ( );

            if( mensajeAlerta.equalsIgnoreCase ( "No se recuperaron inmuebles para el criterio de búsqueda." )     ||
                    mensajeAlerta.equalsIgnoreCase ( "No existen trámites asociados al inmueble seleccionado." )       ||
                    mensajeAlerta.equalsIgnoreCase ( "No se pueden fusionar las clases de los inmuebles ingresados." ) ||
                    mensajeAlerta.equalsIgnoreCase ( "El inmueble no tiene deuda en cero pendiente para procesar." ) )
            {
                this.driverApp.switchTo ( ).alert ( ).accept ( );
                this.mensajeError.append ( mensajeAlerta );
                throw new AssertionError ( this.mensajeError.toString ( ) );
            }
        }

        if( !cuadroDialogoEstaPresente ( ) && getTituloVista ( ) .contains ( "BUSQUEDA INMUEBLE" ) )
        {
            if ( esAccionesDerechos ( ) )
            {
                this.accionesYDerechos = true;

                seleccionarPropiedad( this.numeroAccion );
            }
            else //Para casos con registros históricos: Baja, Fraccionado, Fusionado, Cambio de Propiedad.
            {
                try
                {
                    seleccionarPropiedad ( 1 );
                }
                catch ( AssertionError noExistePropiedadActiva )
                {
                    this.mensajeError.delete ( 8, this.mensajeError.length ( ) );
                    this.driverApp.findElement ( By.linkText ( "Seleccionar" ) ).click ( );
                }
            }
        }
        Log.recordInLog(  "Búsqueda Inmueble: OK." );
    }
    public String getTituloVista ( )
    {
        final String NOMBRE_VISTA_GENERICO = "SIN_TITULO";

        String nombreVista = "";

        if ( elementoWebEstaPresente ( Constants.TIPO_LOCALIZADOR_XPATH, Constants.PATH_TITULO_VISTA ) )
        {
            nombreVista = this.driverApp.findElement ( By.xpath(Constants.PATH_TITULO_VISTA)).getText();
        }
        else if ( elementoWebEstaPresente ( Constants.TIPO_LOCALIZADOR_XPATH, Constants.PATH_TITULO_VISTA.replaceAll(Constants.PATH_FORMULARIO, "") ) )
        {
            nombreVista = this.driverApp.findElement(By.xpath(Constants.PATH_TITULO_VISTA.replaceAll(Constants.PATH_FORMULARIO, ""))).getText();
        }
        else if ( elementoWebEstaPresente ( Constants.TIPO_LOCALIZADOR_XPATH, Constants.PATH_VISTA_DATOS_COD_CATASTRAL ) )
        {
            nombreVista = this.driverApp.findElement ( By.xpath( Constants.PATH_VISTA_DATOS_COD_CATASTRAL ) ).getText();
        }
        else
        {
            nombreVista = NOMBRE_VISTA_GENERICO;
        }

        return nombreVista.replaceAll("\n"," ");
    }
    public boolean elementoWebEstaPresente(String tipoLocalizador, String localizador)
    {
        boolean estaPresente = false;

        switch(tipoLocalizador)
        {
            case Constants.TIPO_LOCALIZADOR_ID	         : estaPresente = (this.driverApp.findElements(By.id(localizador)).size() > 0)? true: false; break;
            case Constants.TIPO_LOCALIZADOR_NAME         : estaPresente = (this.driverApp.findElements(By.name(localizador)).size() > 0)? true: false; break;
            case Constants.TIPO_LOCALIZADOR_XPATH        : estaPresente = (this.driverApp.findElements(By.xpath(localizador)).size() > 0)? true: false; break;
            case Constants.TIPO_LOCALIZADOR_LINK_TEXT    : estaPresente = (this.driverApp.findElements(By.linkText(localizador)).size() > 0)? true: false; break;
            case Constants.TIPO_LOCALIZADOR_CSS_SELECTOR : estaPresente = (this.driverApp.findElements(By.cssSelector(localizador)).size() > 0)? true: false; break;
        }
        return estaPresente;
    }
    public boolean esAccionesDerechos ( )
    {
        final String PATH_BASE_PROPIEDADES = "/html/body/div[2]/form/table/tbody/tr[3]/td/table/tbody/tr";

        int                 nroColumnas        = 0;
        int                 propiedad_i        = 1;
        int                 pagina_i           = 0;
        int                 nroFilasResultados = 0;
        String              estado             = "";
        String              propietario        = "";
        boolean             esAccionesDerechos = false;
        boolean             existePaginacion   = false;
        List < WebElement > filasPropiedades   = null;
        List < WebElement > paginacion         = null;

        nroFilasResultados = this.driverApp.findElements ( By.xpath ( PATH_BASE_PROPIEDADES ) ).size ( );

        if ( nroFilasResultados > 1 ) //Existe paginación
        {
            existePaginacion   = true;
            paginacion         = this.driverApp.findElements ( By.xpath( PATH_BASE_PROPIEDADES.concat ( String.valueOf ( nroFilasResultados ) ).concat ( "]/td/a" ) ) );
            nroFilasResultados = nroFilasResultados - 1;
        }

        filasPropiedades = this.driverApp.findElements ( By.xpath ( PATH_BASE_PROPIEDADES.concat ( "[" ).concat ( String.valueOf ( nroFilasResultados ) ).concat ( "]/td/table/tbody/tr" ) ) );

        do
        {
            nroColumnas = filasPropiedades.get ( propiedad_i ).findElements ( By.xpath ( "td" ) ).size ( );
            estado      = filasPropiedades.get ( propiedad_i ).findElement ( By.xpath ( "td[".concat ( String.valueOf ( nroColumnas - 1 ) ).concat ( "]" ) ) ).getText ( );
            propietario = filasPropiedades.get ( propiedad_i ).findElement ( By.xpath ( "td[".concat ( String.valueOf ( nroColumnas - 3 ) ).concat ( "]" ) ) ).getText ( );

            if ( estado.equalsIgnoreCase ( "ACTIVO" ) || estado.equalsIgnoreCase ( "EN TRANSFERENCIA" ) )
            {
                if ( propietario.contains( "%" ) )
                {
                    esAccionesDerechos = true;
                    break;
                }
            }
            propiedad_i ++;

            if ( existePaginacion )
            {
                if ( pagina_i < paginacion.size ( ) )
                {
                    paginacion.get( pagina_i ++).click ( );
                }
                else
                {
                    break;
                }
                propiedad_i = 1;
            }
            else
            {
                if ( propiedad_i >= filasPropiedades.size ( ) )
                {
                    break;
                }
            }

        } while ( true );

        return esAccionesDerechos;
    }
    public void seleccionarPropiedad ( int numeroPropiedad )
    {
        final String PATH_BASE_PROPIEDADES = "/html/body/div[2]/form/table/tbody/tr[3]/td/table/tbody/tr";

        int                 propiedad_i         = 1;
        int                 pagina_i            = 0;
        int                 nroPropiedadActiva  = 0;
        int                 nroColumnas         = 0;
        int                 nroFilasResultados  = 0;
        String              estado              = "";
        String              propietario         = "";
        boolean             existePaginacion    = false;
        boolean             propiedadEncontrada = false;
        List < WebElement > filasPropiedades    = null;
        List < WebElement > paginacion          = null;

        nroFilasResultados = this.driverApp.findElements ( By.xpath ( PATH_BASE_PROPIEDADES ) ).size ( );

        if ( nroFilasResultados > 1 ) //Existe paginación
        {
            existePaginacion   = true;
            paginacion         = this.driverApp.findElements ( By.xpath( PATH_BASE_PROPIEDADES.concat ( String.valueOf ( nroFilasResultados ) ).concat ( "]/td/a" ) ) );
            nroFilasResultados = nroFilasResultados - 1;
        }

        filasPropiedades = this.driverApp.findElements ( By.xpath ( PATH_BASE_PROPIEDADES.concat ( "[" ).concat ( String.valueOf ( nroFilasResultados ) ).concat ( "]/td/table/tbody/tr" ) ) );

        do
        {
            nroColumnas = filasPropiedades.get ( propiedad_i ).findElements ( By.xpath ( "td" ) ).size ( );
            estado      = filasPropiedades.get ( propiedad_i ).findElement ( By.xpath ( "td[".concat ( String.valueOf ( nroColumnas - 1 ) ).concat ( "]" ) ) ).getText ( );
            propietario = filasPropiedades.get ( propiedad_i ).findElement ( By.xpath ( "td[".concat ( String.valueOf ( nroColumnas - 3 ) ).concat ( "]" ) ) ).getText ( );

            if ( estado.equalsIgnoreCase ( "ACTIVO" ) || estado.equalsIgnoreCase ( "EN TRANSFERENCIA" ) )
            {
                nroPropiedadActiva ++;

                if ( nroPropiedadActiva == numeroPropiedad )
                {
                    propiedadEncontrada = true;
                    ScreenShotHelper.takeScreenShotAndAdToHTMLReport(driverApp, Status.INFO, "Propiedades");
                    Log.recordInLog( " Evaluando Propiedad -> ".concat ( String.valueOf ( numeroPropiedad ) ).concat ( ": " ).concat ( propietario.replaceAll ( "\n", " " ) ) );
                    filasPropiedades.get ( propiedad_i ).findElement ( By.xpath ( "td[".concat ( String.valueOf ( nroColumnas ) ).concat ( "]" ) ) ).findElement ( By.linkText ( "Seleccionar" ) ).click ( );

                    try
                    {
                        this.wait.until ( ExpectedConditions.or ( ExpectedConditions.alertIsPresent ( ),
                                ExpectedConditions.visibilityOfElementLocated ( By.xpath ( Constants.PATH_TITULO_VISTA ) ),
                                ExpectedConditions.visibilityOfElementLocated ( By.xpath ( Constants.PATH_TITULO_VISTA.replaceAll ( "/form", "" ) ) ) ) );
                    }
                    catch ( UnhandledAlertException alertaExcepcion ) { }

                    break;
                }
            }
            propiedad_i ++;

            if ( existePaginacion )
            {
                if ( pagina_i < paginacion.size ( ) )
                {
                    paginacion.get( pagina_i ++).click ( );
                }
                else
                {
                    break;
                }
                propiedad_i = 1;
            }
            else
            {
                if ( propiedad_i >= filasPropiedades.size ( ) )
                {
                    break;
                }
            }

        } while ( true );

        if ( !propiedadEncontrada )
        {
            ScreenShotHelper.takeScreenShotAndAdToHTMLReport(driverApp, Status.INFO, "No válido");
            this.mensajeError.append ( "La propiedad: " ).append ( numeroPropiedad ).append( " no se encuentra en estado válido." );
            throw new AssertionError ( this.mensajeError.toString ( ) );
        }
    }
    public boolean esperarVista ( String tituloVista )
    {
        this.wait.until ( ExpectedConditions.or ( ExpectedConditions.textToBe ( By.xpath ( Constants.PATH_TITULO_VISTA ), tituloVista ),
                ExpectedConditions.textToBe ( By.xpath ( Constants.PATH_TITULO_VISTA.replaceAll ( "/form", "" ) ), tituloVista ),
                ExpectedConditions.visibilityOfElementLocated ( By.xpath( Constants.PATH_TITULO_VISTA ) ),
                ExpectedConditions.visibilityOfElementLocated ( By.xpath ( Constants.PATH_VALIDACIONES ) ),
                ExpectedConditions.visibilityOfElementLocated ( By.xpath( Constants.PATH_VISTA_DATOS_COD_CATASTRAL ) ),
                ExpectedConditions.visibilityOfElementLocated ( By.cssSelector ( Constants.SELECTOR_CSS_CODIGO_ERROR ) ),
                ExpectedConditions.textToBe ( By.xpath ( "/html/body/h1" ), "Page Flow Error - Unresolvable Forward" ) ) );

        if ( getTituloVista ( ).equalsIgnoreCase( tituloVista ) )
        {
            return true;
        }
        else
        {
            if ( this.driverApp.findElements ( By.xpath ( Constants.PATH_VALIDACIONES ) ).size ( ) > 0 &&
                    getTituloVista ( ).equalsIgnoreCase( "VALIDACIONES" ) )
            {
                this.mensajeError.append ( this.driverApp.findElement ( By.xpath ( Constants.PATH_VALIDACIONES ) ).getText ( ) );
                throw new AssertionError ( this.mensajeError.toString ( ) );
            }

            if ( this.driverApp.findElements ( By.xpath ( "/html/body/h1" ) ).size() > 0 &&
                    this.driverApp.findElement ( By.xpath ( "/html/body/h1" ) ).getText().equalsIgnoreCase( "Page Flow Error - Unresolvable Forward" ))
            {
                this.mensajeError.append ( this.driverApp.findElement ( By.xpath ( "/html/body/h1" ) ).getText() );
                throw new AssertionError ( this.mensajeError.toString ( ) );
            }

            if ( this.driverApp.findElements ( By.cssSelector ( Constants.SELECTOR_CSS_CODIGO_ERROR ) ).size ( ) > 0)
            {
                throw new AssertionError ( procesarErrorAplicativo ( ) );
            }

            return false;
        }
    }
    /**
     * Función que devuelve el mensaje de código de error del aplicativo, y además, realiza conexión con Base de Datos y obtiene la
     * descripción del error, devolviendo todo en una cadena de texto.
     * @return errorAplicativo
     */
    public String procesarErrorAplicativo ( )
    {
        String codigoError      = "";
        String descripcionError = "";

        codigoError      = this.driverApp.findElement ( By.cssSelector ( Constants.SELECTOR_CSS_CODIGO_ERROR ) ).getText ( );
        codigoError      = codigoError.substring ( codigoError.indexOf ( ":" ) + 2, codigoError.indexOf ( "en la" ) - 1 );
        descripcionError = procesarErrorAplicativo ( codigoError );

        return descripcionError;
    }
    public String procesarErrorAplicativo ( String codigoError )
    {
       // AccesoBaseDatos                            objBaseDatos     = new AccesoBaseDatos ( );
        StringBuilder                              descripcionError = new StringBuilder ( );
        Hashtable < String, ArrayList < String > > consultaError    = new Hashtable < String, ArrayList < String > > ( );

        descripcionError.append ( "ERROR APLICATIVO:\n" ).append ( Constants.CADENA_ERROR_APLICATIVO )
                .append ( "\n " ).append ( codigoError );

       // consultaError = objBaseDatos.getConsultaBD ( ConsultasSQL.getErrorInmuebles ( codigoError ) );

        descripcionError.append ( "\n Descripción Error:\n " ).append ( consultaError.get ( "DESCRIPCION" ).get ( 0 ) )
                .append ( Constants.CADENA_ERROR_APLICATIVO );

        return descripcionError.toString ( );
    }
    /**
     * Creado: efigueredo	Fecha: 01/08/2019
     * Método esperarDescargaReporte ( ): espera a que se despliegue la ventana del reporte y luego esta sea cerrada.
     */
    public void esperarDescargaReporte ( )
    {
        Set < String > ventanas = null;

        this.wait.until ( ExpectedConditions.numberOfWindowsToBe ( 2 ) );
        ventanas = this.driverApp.getWindowHandles ( );
        this.driverApp.switchTo ( ).window ( ventanas.toArray ( ) [ ventanas.toArray ( ).length - 1 ].toString ( ) );
        this.wait.until (ExpectedConditions.titleIs(Constants.TITULO_VENTANA_REPORTE));
        this.driverApp.close ( );
        this.driverApp.switchTo ( ).window ( ventanas.toArray ( ) [ ventanas.toArray ( ).length - 2 ].toString ( ) );
    }
    public void moverScroll(WebElement elemento)
    {
        int posicionY;

        posicionY = elemento.getLocation().getY() - 20;
        ((JavascriptExecutor) this.driverApp).executeScript("scroll(0, ".concat(String.valueOf(posicionY)).concat(")"));
    }
    public boolean cuadroVerificacionHabilitado(WebElement cuadroVerificacion)
    {
        if(cuadroVerificacion.getAttribute(ConstantsINM.ATRIBUTO_DESHABILITADO) == null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
