package test.baseTest;

import com.aventstack.extentreports.Status;
import main.actions.Log;
import main.actions.LogTime;
import main.helpers.common.Constants;
import main.helpers.dataUtility.ReportManager;
import main.helpers.dataUtility.ScreenShotHelper;
import main.tasks.ApplicationExcel;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.ITestResult;
import org.openqa.selenium.WebDriver;

/**
 * @description clase Base para las generadores y Casos de pruebas.
 * @date 21/07/2022
 * @author Faustina Chambi
 */
public class BaseTest{
    protected FirefoxProfile       perfil;
    protected FirefoxOptions       opcionesNavegador;
    protected WebDriverWait        wait;
    protected Actions              acciones;
    public WebDriver driver;
    public ApplicationExcel application;
	// TODO Auto-generated constructor stub
		
		// beforeSuit
		//clase dataExcel()
		//  cargar los datos (nombreArchjivo, hoja);

		// PROVISIONALMENTE INICIAMOS AMBIENTE




   // public static XSSFSheet sheet;


   /* @BeforeSuite
    public static void setUpExcel() throws IOException {
        //configuracion excel
        File file = new File(System.getProperty("user.dir")+"\\data\\"+"datosVehiculos"+".xlsx");
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        sheet = wb.getSheet("libro1");
    }*/

    @BeforeSuite
    public void setUpSuite() throws Exception {
        ScreenShotHelper.setUpSuiteReport();
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, Constants.DIRECTORIO_LOGS.concat(Constants.ARCHIVO_LOG_GECKODRIVER));
        configFirefox();
    }

    @BeforeMethod
    public void configurar(ITestResult iTestResult) {
        ReportManager.getInstance().startTest(iTestResult.getMethod().getMethodName());
    }

    @AfterMethod
    public void concluir(ITestResult iTestResult){
        try {
            switch (iTestResult.getStatus()){
                case ITestResult.FAILURE:
                    ReportManager.getInstance().getTest().log(Status.FAIL, "Test con falla");
                    ScreenShotHelper.takeScreenShotAndAdToHTMLReport(this.driver, Status.INFO, "Test con falla");
                    break;
                case ITestResult.SKIP:
                    ReportManager.getInstance().getTest().log(Status.SKIP, "Test omitida");
                    break;
                case ITestResult.SUCCESS:
                    ReportManager.getInstance().getTest().log(Status.PASS, "Test exitoso");
                    ScreenShotHelper.takeScreenShotAndAdToHTMLReport(this.driver, Status.INFO, "Paso exitoso");
                    break;
                default:
                    ReportManager.getInstance().getTest().log(Status.FAIL, "Test incompleto");
            }

            if(iTestResult.getStatus() != ITestResult.SUCCESS && iTestResult.getThrowable() != null){
                ReportManager.getInstance().getTest().log(Status.FAIL, iTestResult.getThrowable().getMessage());
                ScreenShotHelper.takeScreenShotAndAdToHTMLReport(this.driver, Status.FAIL, "Imagen con falla");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(driver != null)
                driver.quit();
        }
    }

    @AfterSuite
    public void tearDownSuite(){
     ReportManager.getInstance().flush();
    }

    public void configFirefox()
    {
        startDriver();
        this.perfil            = new FirefoxProfile();  //configuración Proxy y directorio de descargas.
        this.perfil.setPreference("network.proxy.type", 1);
        this.perfil.setPreference("network.proxy.socks", "172.21.24.40");
        this.perfil.setPreference("network.proxy.socks_port", 3128);
        this.perfil.setPreference("network.proxy.no_proxies_on", "*.ruat.gob.bo, 127.0.0.1, *.ruat.net.bo, *.ruat.net");

        this.perfil.setPreference("browser.download.folderList", 2);
        this.perfil.setPreference("browser.download.manager.showWhenStarting",false);
        this.perfil.setPreference("browser.download.dir",Constants.DIRECTORIO_REPORTES);
        this.perfil.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream,application/pdf,application/x-pdf,application/vnd.pdf");
        this.perfil.setPreference("browser.helperApps.neverAsk.openFile", "application/octet-stream,application/pdf,application/x-pdf,application/vnd.pdf");
        this.perfil.setPreference("browser.download.manager.useWindow", false);
        this.perfil.setPreference("plugin.scan.plid.all",false);
        this.perfil.setPreference("plugin.scan.Acrobat","99.0");
        this.perfil.setPreference("pdfjs.enabledCache.state",false);
        this.perfil.setPreference("pdfjs.disabled",true);

        this.opcionesNavegador = new FirefoxOptions();
        this.opcionesNavegador.setProfile(this.perfil);
        this.opcionesNavegador.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        this.opcionesNavegador.setCapability(CapabilityType.TAKES_SCREENSHOT, true);

        this.driver            = new FirefoxDriver(this.opcionesNavegador);
        this.wait              = new WebDriverWait(this.driver, Constants.TIME_OUT);
        this.acciones          = new Actions(this.driver);
    }
    /**
     * @description Inicializar webdriver.gecho.driver = GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY
     * @date 24/02/2022
     */
    protected void startDriver()
    {
        LogTime.start();
        System.setProperty(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY, Constants.GECKO_DRIVER);
    }

}
