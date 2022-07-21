package test.generators.inmuebles;

import main.helpers.common.CommonComponent;
import main.tasks.Empadronamiento;
import org.testng.annotations.Test;
import test.baseTest.BaseTest;


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
