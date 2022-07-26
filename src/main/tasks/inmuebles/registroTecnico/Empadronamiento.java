package main.tasks.inmuebles.registroTecnico;

import main.actions.Log;
import main.helpers.common.Inmuebles.ConstantsINM;
import main.helpers.dataUtility.AccesExcel;
import main.tasks.ApplicationExcel;

/**
 * @description Permite empadronar inmueble.
 * @date 21/07/2022
 * @author Faustina Chambi
 */
public class Empadronamiento extends ApplicationExcel {
    private int       numeroAcciones;
    private int       numeroInmuebles;
    private int       numeroConstrucciones;
    private int       numeroContribuyentes;
    private String    area;
    private String    materialVia;
    private String    enCondominio;
    private String    tipoPropiedad;
    private String    claseInmueble;
    private String    zonaTributaria;
    private String    tipoContribuyente;
    private String    anioInicioImpuesto;
    private String    pmcContribuyentes [ ];
    //private AccesTest datosContribuyentes;
   // private Hashtable < String, ArrayList < String > > inmueblesEmpadronados;


    public Empadronamiento() {
        super();
        this.accesExcel = new AccesExcel( ConstantsINM.ARCHIVO_DATOS_PAGINA, ConstantsINM.HOJA_DATOS_EMPADRONAMIENTO );
        Log.recordInLog("Termino de cargar el Excel de la hoja asignada Datos Reader Excel ");
    }
   /** public void principal(){

        Empadronamiento objEmpadronamiento    = new Empadronamiento( );
        try
        {
            objEmpadronamiento.startApplication();
        }
        catch ( Exception empadronamientoExcepcion ) { }
        CommonComponent.RecordInLog(" Ingreso a empadronar");
    }*/
    public void cargarParametros ( int fila_i )
    {
        int    indice    = 0;
        String parametro = "";
        super.cargarParametros ( fila_i );
    }

}
