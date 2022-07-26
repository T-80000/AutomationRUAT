package test.generators.inmuebles.liquidacion;

import main.actions.Log;
import main.tasks.inmuebles.liquidacion.Proforma;
import org.testng.annotations.Test;
import test.baseTest.BaseTest;
/**
 * @description Generar inmuebles empadronados
 * @date 21/07/2022
 * @author Faustina Chambi Camata
 */
public class ProformaGen extends BaseTest {

    @Test
    public void proforma(){

        Proforma proforma    = new Proforma();
        try
        {
            proforma.run(this.driver,this.wait);
        }
        catch ( Exception empadronamientoExcepcion ) { }
        Log.recordInLog(" Fin");

    }
}