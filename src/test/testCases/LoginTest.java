package test.testCases;

import main.helpers.common.CommonComponent;
import main.tasks.Login;
import test.baseTest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

	@Test
	public void LoginTestUNO() {
		
		//Arrange
		String usuario = "AALCAZAR.LPZ";
		String pasword = "NNB";
		// parametros de entrada o variables
		
		//Act
		
		 Login.as(webDriver, usuario,pasword);
		
		//Assert
		
		// Assert.true(condicion,esperado);
		Assert.assertTrue(true);
		CommonComponent.RecordInLog(" prueba del registro del assert: ...OK");
		

	}

	@Test
	public void LoginTestDOS() {

		//Arrange
		String usuario = "AALCAZAR.LPZ";
		String pasword = "NNB";
		// parametros de entrada o variables

		//Act

		Login.as(webDriver, usuario,pasword);

		//Assert

		// Assert.true(condicion,esperado);
		Assert.assertTrue(false);
		CommonComponent.RecordInLog(" prueba del registro del assert: ...OK");


	}

}
