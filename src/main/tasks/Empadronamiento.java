package main.tasks;

import main.actions.LogWrite;
import main.helpers.common.Inmuebles.ConstantsInmuebles;
import main.helpers.dataUtility.AccesExcel;
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
        this.accesExcel = new AccesExcel( ConstantsInmuebles.ARCHIVO_DATOS_PAGINA, ConstantsInmuebles.HOJA_DATOS_EMPADRONAMIENTO );
        LogWrite.with("Termino de cargar el Excel de la hoja asignada Datos Reader Excel ");
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
