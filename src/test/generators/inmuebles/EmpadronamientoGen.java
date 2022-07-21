package test.generators.inmuebles;

import main.helpers.common.CommonComponent;
import main.tasks.Empadronamiento;
import org.testng.annotations.Test;
import test.baseTest.BaseTest;

/**
 * @description Se definen constantes comunes generales.
 * @date 21/07/2022
 * @author Faustina Chambi
 */
public class EmpadronamientoGen extends BaseTest {

    @Test
    public void empadronar(){

        Empadronamiento objEmpadronamiento    = new Empadronamiento( );
        try
        {
            objEmpadronamiento.startApplication(this.driver,this.wait);
        }
        catch ( Exception empadronamientoExcepcion ) { }
        CommonComponent.RecordInLog(" termino de Ingresar a empadronar");

    }
}
