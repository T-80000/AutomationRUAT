package main.tasks.inmuebles.liquidacion;

import com.aventstack.extentreports.Status;
import main.actions.Click;
import main.actions.Log;
import main.helpers.common.Constants;
import main.helpers.common.Inmuebles.ConstantsINM;
import main.helpers.dataUtility.AccesExcel;
import main.helpers.dataUtility.ScreenShotHelper;
import main.tasks.ApplicationExcel;
import main.ui.inmuebles.ProformaUI;
import org.openqa.selenium.WebElement;
import main.helpers.common.CommonComponent;

import java.util.List;

/**
 * @description: Clase que realiza el flujo del módulo de proforma, liquida un inmueble, genera la proforma detallada y guarda
 * el reporte en el directorio descargas. Se incluyen 3 posibles operaciones: sólo liquidar, emitir proforma
 * detallada o emitir proforma resumida.
 * @author Faustina Chambi Camata
 * Fecha: 21/07/2022
 */
public class Proforma extends ApplicationExcel{
    private String detalleDeuda;
    private int    gestionInicio;
    private int    gestionFin;

    public static final String MENU_PROFORMA                = "Proforma";
    public static final String HOJA_DATOS_PROFORMA          = "Proforma (GENERADOR)";
    public static final String MODULO_PROFORMA              = "Proforma Inmuebles";
    public static final String TIPO_DEUDA_TODOS             = "TODOS";
    public static final String PARAMETRO_DETALLE_DEUDA      = "DETALLE DEUDA";
    public static final String OPERACION_SOLO_LIQUIDAR      = "SÓLO LIQUIDAR";
    public static final String OPERACION_PROFORMA_DETALLADA = "PROFORMA DETALLADA";
    public static final String OPERACION_PROFORMA_RESUMIDA  = "PROFORMA RESUMIDA";

    public Proforma ( )
    {
        super ( );
        this.accesExcel = new AccesExcel(ConstantsINM.ARCHIVO_DATOS_PAGINA, HOJA_DATOS_PROFORMA );
    }

    public Proforma ( ApplicationExcel objInicial, String operacion, String municipio, String detalleDeuda, String inmueble, int gestionInicio,
                      int gestionFin )
    {
        super ( );
        setOperacion ( operacion );
        setMunicipio ( municipio );
        setDetalleDeuda ( detalleDeuda );
        setNumeroInmueble ( inmueble );
        setGestionInicio ( gestionInicio );
        setGestionFin ( gestionFin );
        /*configFirefox ( objInicial.getPerfil ( ), objInicial.getOpcionesNavegador ( ), objInicial.getDriver ( ), objInicial.getWait ( ),
                objInicial.getAcciones ( ) );*/
    }

    /**
     * @return detalleDeuda
     */
    public String getDetalleDeuda ( )
    {
        return detalleDeuda;
    }

    /**
     * @param detalleDeuda to set
     */
    public void setDetalleDeuda ( String detalleDeuda )
    {
        this.detalleDeuda = detalleDeuda;
    }

    /**
     * @return gestionInicio
     */
    public int getGestionInicio ( )
    {
        return this.gestionInicio;
    }

    /**
     * @param gestionInicio to set
     */
    public void setGestionInicio ( int gestionInicio )
    {
        this.gestionInicio = gestionInicio;
    }

    /**
     * @return gestionFin
     */
    public int getGestionFin()
    {
        return this.gestionFin;
    }

    /**
     * @param gestionFin to set
     */
    public void setGestionFin ( int gestionFin )
    {
        this.gestionFin = gestionFin;
    }

    public void start ( )
    {
        iniciarModulo ( Constants.SUBSISTEMA_LIQUIDACION, MENU_PROFORMA, MODULO_PROFORMA );
        busquedaInmueble ( this.numeroInmueble );
        detalleDeudas ( );
    }

    /**
     * Adecuaciones para permitir parametrizar 3 operaciones en proforma. Se agregó el atributo detalleDeuda.
     */
    public void detalleDeudas ( )
    {
        int                 nroTablasDetalleDeudas = 0;
        String              gestion                = "";
        String              pathFilasDatosDeudas   = "";
        String              detalleDeuda           = "";
        boolean             seleccionarGestion     = false;
        WebElement          checkGestion           = null;
        List< WebElement > filasDatosDeudas       = null;

        esperarVista ( "DETALLE DEUDAS" );
        Log.recordInLog( "Detalle Deudas: ..." );

        if( !this.operacion.equalsIgnoreCase ( OPERACION_SOLO_LIQUIDAR ) )   //11/01/2019
        {
            nroTablasDetalleDeudas = this.driverApp.findElements (ProformaUI.PATH_TABLES ).size ( );
            pathFilasDatosDeudas   = ProformaUI.PATH_TABLE.concat ( "[" ).concat ( String.valueOf ( nroTablasDetalleDeudas - 2 ) ).concat ( "]/td/table/tbody/tr" );
            filasDatosDeudas       = this.driverApp.findElements ( ProformaUI.getPathFilasDatosDeudas(pathFilasDatosDeudas) );

            for ( int fila_i = 1; fila_i < filasDatosDeudas.size ( ); fila_i ++ )
            {
                moverScroll ( filasDatosDeudas.get ( fila_i ).findElement ( ProformaUI.MOVER_SCROLL ) );
                gestion      = filasDatosDeudas.get ( fila_i ).findElement ( ProformaUI.GESTION ).getText ( );
                detalleDeuda = filasDatosDeudas.get ( fila_i ).findElement ( ProformaUI.DETALLE_DEUDA ).getText ( );
                checkGestion = filasDatosDeudas.get ( fila_i ).findElement ( ProformaUI.CHECK_GESTION ).findElement ( ProformaUI.CHECK_GESTION_TAG );

                if ( gestion.equals ( "N/A" ) )
                {
                    if ( this.detalleDeuda.equals ( "TODOS" ) ||
                            ( this.detalleDeuda.equals ( "IMT" ) && detalleDeuda.contains("TRANSFERENCIA")) ||
                            ( this.detalleDeuda.equals ( "MIDF (IMT)" ) && detalleDeuda.contains("INCUMPLIMIENTO")) ||
                            ( this.detalleDeuda.equals ( "MOP (IMT)" ) && detalleDeuda.contains("OMISION PAGO")) ||
                            ( this.detalleDeuda.equals ( "MOR" ) && detalleDeuda.contains("OMISION REGISTRO")))
                    {
                        seleccionarGestion = true;
                    }
                }
                else
                {
                    if(Integer.parseInt(gestion) >= this.gestionInicio && Integer.parseInt(gestion) <= this.gestionFin)
                    {
                        if(this.detalleDeuda.equals("TODOS") ||
                                (this.detalleDeuda.equals("IP") && detalleDeuda.contains("PROPIEDAD")) ||
                                (this.detalleDeuda.equals("MIDF (IP)") && detalleDeuda.contains("INCUMPLIMIENTO")) ||
                                (this.detalleDeuda.equals("MOP (IP)") && detalleDeuda.contains("OMISION PAGO")))
                        {
                            seleccionarGestion = true;
                        }
                    }
                }
                if(seleccionarGestion && cuadroVerificacionHabilitado(checkGestion))
                {
                    checkGestion.click();
                    seleccionarGestion = false;
                }
            }
            moverScroll(this.driverApp.findElement(ProformaUI.NOMBRE_BOTON_DETALLADA));
            ScreenShotHelper.takeScreenShotAndAdToHTMLReport(driverApp, Status.INFO, "Detalle deudas");
            if(this.operacion.equalsIgnoreCase(OPERACION_PROFORMA_DETALLADA))
            {
                this.driverApp.findElement(ProformaUI.NOMBRE_BOTON_DETALLADA).click();
            }
            else if(this.operacion.equalsIgnoreCase(OPERACION_PROFORMA_RESUMIDA))
            {
               // ********************this.driverApp.findElement(ProformaUI.NOMBRE_BOTON_RESUMIDA).click();
                Click.on(driverApp,ProformaUI.NOMBRE_BOTON_RESUMIDA);
            }
            Log.recordInLog( " Generar Proforma: ...");
            super.esperarDescargaReporte ( );
            //Log.recordInLog( "  Archivo: ".concat(ComponentesComunes.obtenerNombreArchivoUltimoPdfGenerado()));
            Log.recordInLog( "Generar Proforma: OK.");
        }
        Log.recordInLog( "Detalle Deudas: OK.");
    }
}
