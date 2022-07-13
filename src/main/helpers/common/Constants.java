package main.helpers.common;

import java.io.File;

/**
 * @description Se definen constantes comunes generales.
 * @date 24/02/2022
 * @author Faustina Chambi
 */
public class Constants 
{
	public static final int     TIME_OUT                                                 = 60;	
	public static final String  DIRECTORIO_PROYECTOS                                     = System.getProperty("user.dir").replace('\\', '/') + "/";
	public static final String  GECKO_DRIVER                                             = "./resource/geckodriver.exe";
	public static final String  DIRECTORIO_PERFIL_FIREFOX                                = System.getProperty("user.home").replace('\\', '/') + "/AppData/Roaming/Mozilla/Firefox/Profiles/" + new File(new File(System.getProperty("user.home") + "/AppData/Roaming/Mozilla/Firefox/Profiles/").list()[0]).getName();
	public static final String  DIRECTORIO_REPORTES                                      = System.getProperty("user.home").replace('\\', '/') + "/Downloads/";
	public static final String  DIRECTORIO_LOGS                                          = "./logs/";
	public static final String  ARCHIVO_LOG                                              = "log.log";
	public static final String  ARCHIVO_LOG_GECKODRIVER                                  = "logGecko.log";

	public static final String  HOJA_DATOS_O_PARAMETRO_AMBIENTE                          = "AMBIENTE";
	public static final String  HOJA_DATOS_CONTRIBUYENTES                                = "Contribuyentes";
	public static final String  CONTRASENIA_DEFECTO                                      = "1234567";
	
	/*
	public static final String  SUBSISTEMA_REGISTRO_TECNICO                              = "REGISTRO TECNICO";
	public static final String  SUBSISTEMA_REGISTRO_TRIBURARIO                           = "REGISTRO TRIBUTARIO";
	public static final String  SUBSISTEMA_REGISTRO_ADMINISTRATIVO                       = "REGISTRO ADMINISTRATIVO";
	public static final String  SUBSISTEMA_LIQUIDACION                                   = "LIQUIDACION";
	public static final String  SUBSISTEMA_TRAMITES                                      = "TRAMITES";
	public static final String  SUBSISTEMA_REPORTES_CONSULTAS                            = "REPORTES Y CONSULTAS";
	
	public static final String  TIPO_CONTRIBUYENTE_NATURAL                               = "NATURAL";
	public static final String  TIPO_CONTRIBUYENTE_JURIDICO                              = "JURÍDICO";

	public static final String  ID_NAME_BOTON_ACEPTAR                                    = "btnAceptar";
	public static final String  ENLACE_ADICIONAR                                         = "Adicionar";
	public static final String  ENLACE_ANTERIOR                                          = "Anterior";
	public static final String  ENLACE_REGISTRAR                                         = "Registrar";
	public static final String  ENLACE_MODIFICAR                                         = "Modificar";
	public static final String  ENLACE_ANULAR                                            = "Anular";
	public static final String  ENLACE_ASOCIAR                                           = "Asociar";
	
	public static final String  PARAMETRO_MUNICIPIO                                      = "MUNICIPIO";
	public static final String  PARAMETRO_TIPO_CONTRIBUYENTE                             = "TIPO CONTRIBUYENTE";

	public static final String  SELECTOR_CSS_CODIGO_ERROR                                = "span.error";
	public static final String  PATH_VALIDACIONES                                        = "//*[@id='ventana']/table/tbody/tr[4]/td";
	public static final String  PATH_TITULO_VISTA                                        = "//*[@id='ventana']/form/table/tbody/tr[1]/td/h2";
	public static final String  PATH_VISTA_DATOS_COD_CATASTRAL                           = "/html/body/div[2]/form/table/tbody/tr[1]/td/div[1]/h2";
	public static final String  PATH_ERROR_404                                           = "/html/body/font/table[1]/tbody/tr/td/font/h2";
	public static final String  CADENA_FIN_ITERACION                                     = "---------------------------------------------------------------------------------------------------------------";
	public static final String  CADENA_FIN_EJECUCION_TOTAL                               = "===============================================================================================================";
	public static final String  CADENA_ERROR_APLICATIVO                                  = "|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||";				//ATINM-97 (AP-057/2018)
	public static final String  CADENA_CONEXION_MITUDS                                   = "jdbc:oracle:thin:@mituds.ruat.gob.bo:1535:RDIS";    
	public static final String  CADENA_CONEXION_IMPUDS                                   = "jdbc:oracle:thin:@impuds.ruat.gob.bo:1533:RDIS";
	public static final String  CADENA_CONEXION_CONUDS                                   = "jdbc:oracle:thin:@conuds.ruat.gob.bo:1537:INTEGUDS";
	public static final String  USUARIO_BASE_DATOS                                       = "HERRAMIENTASCALIDAD";
	public static final String  CONTRASENIA_BASE_DATOS                                   = "h123";
	
	public static final String  TIPO_LOCALIZADOR_ID                                      = "id";
	public static final String  TIPO_LOCALIZADOR_NAME                                    = "name";
	public static final String  TIPO_LOCALIZADOR_XPATH                                   = "xpath";
	public static final String  TIPO_LOCALIZADOR_LINK_TEXT                               = "linkText";
	public static final String  TIPO_LOCALIZADOR_CSS_SELECTOR                            = "cssSelector";*/
	
	public static final String  MASCARA_FECHA_HORA                                       = "dd/MM/yyyy HH:mm:ss.SSS";
	public static final String  MASCARA_FECHA                                            = "dd/MM/yyyy";
	public static final String  TITULO_VENTANA_REPORTE                                   = "RUAT.NET";
}
