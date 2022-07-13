package main.actions;

import main.helpers.common.CommonComponent;

public class LogTime {
    static long InicitalExecTime;

    public static void start(){
        InicitalExecTime = 0;
        InicitalExecTime = System.currentTimeMillis();
     }

    public static void end(){
        CommonComponent cc= new CommonComponent();
        cc.StartCommonLog();
        cc.RecordInLog("TIEMPO EJECUCION TOTAL: ".concat(CommonComponent.formatearTiempoEjecucion(System.currentTimeMillis() - InicitalExecTime)));
    }
}
