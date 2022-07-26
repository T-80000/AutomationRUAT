package main.actions;

import main.helpers.common.CommonComponent;
import main.helpers.common.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogTime {
    static long InicitalExecTime;

    public static void start(){
        InicitalExecTime = 0;
        InicitalExecTime = System.currentTimeMillis();
     }

    public static void end(){
        Log.recordInLog("Tiempo Total de Ejecución: ".concat(CommonComponent.formatearTiempoEjecucion(System.currentTimeMillis() - InicitalExecTime)));
    }
}
