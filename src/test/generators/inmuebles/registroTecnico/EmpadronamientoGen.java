package test.generators.inmuebles.registroTecnico;

import main.actions.Log;
import main.helpers.common.CommonComponent;
import main.tasks.inmuebles.registroTecnico.Empadronamiento;
import org.testng.annotations.Test;
import test.baseTest.BaseTest;

/**
 * @description Generar inmuebles empadronados
 * @date 21/07/2022
 * @author Faustina Chambi
 */
public class EmpadronamientoGen extends BaseTest {

    @Test
    public void proforma(){

        Empadronamiento empadronamiento    = new Empadronamiento( );
        try
        {
            empadronamiento.run(this.driver,this.wait);
        }
        catch ( Exception empadronamientoExcepcion ) { }
        Log.recordInLog(" termino de Ingresar a empadronar");

    }
}
