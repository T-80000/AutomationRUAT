package main.ui.inmuebles;

import org.openqa.selenium.By;
/**
 * @description Generar inmuebles empadronados
 * @date 22/07/2022
 * @author Faustina Chambi Camata
 */
public class ProformaUI {
    public static String  PATH_TABLE                   = "//*[@id='ventana']/form/table/tbody/tr";

    public static By PATH_TABLES                       = By.xpath(PATH_TABLE);
    public static By NOMBRE_BOTON_DETALLADA            = By.name("btnDetallada");
    public static By NOMBRE_BOTON_RESUMIDA             = By.name("btnResumida");
    public static By PATH_FILAS_DATOS_DEUDAS           =  null;
    public static By MOVER_SCROLL                      = By.xpath ( "td[1]" );
    public static By GESTION                           = By.xpath ( "td[1]" );
    public static By DETALLE_DEUDA                     = By.xpath ( "td[2]" );
    public static By CHECK_GESTION                     = By.xpath ( "td[6]" );
    public static By CHECK_GESTION_TAG                 = By.tagName ( "input" );

    public static By getPathFilasDatosDeudas(String path) {
        PATH_FILAS_DATOS_DEUDAS     =   By.xpath (path);
        return PATH_FILAS_DATOS_DEUDAS;
    }

}
