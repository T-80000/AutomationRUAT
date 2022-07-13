package main.helpers.dataUtility;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelReader {
	/**
	 * Archivo Excel a leer.
	 */
	private Workbook libro = null;

	/**
	 * Hoja del archivo Excel.
	 */
	private Sheet hoja;

	/**
	 * Almacena datos de columnas.
	 */
	private Hashtable<String, Integer> columnas = new Hashtable<String, Integer>();

	/**
	 * Constructor
	 *
	 * @param rutaArchivoExcel
	 * @throws BiffException
	 * @throws IOException
	 */
	public ExcelReader(String rutaArchivoExcel) {
		this(rutaArchivoExcel, 0);
	}

	public ExcelReader(String rutaArchivoExcel, int nroHoja) {
		try {
			libro = Workbook.getWorkbook(new File(rutaArchivoExcel));
			hoja = libro.getSheet(nroHoja);

			crearDiccionarioColumnas();
		} catch (IOException e) {
		} catch (BiffException e) {
		}
	}

	public ExcelReader(String rutaArchivoExcel, String nombreHoja) {
		try {
			libro = Workbook.getWorkbook(new File(rutaArchivoExcel));
			hoja = libro.getSheet(nombreHoja);

			crearDiccionarioColumnas();
		} catch (IOException e) {
		} catch (BiffException e) {
		}
	}

	/**
	 * Crea un Diccionario con el nombre de todas las columnas.
	 */
	public void crearDiccionarioColumnas() {
		for (int i = 0; i < hoja.getColumns(); i++)
			columnas.put(leerCelda(i, 0), i);
	}

	/**
	 * Retorna el número de filas.
	 *
	 * @return filas
	 */
	public int getNumeroFilas() {
		int nroFilas = 0;

		for (int i = 1; i < hoja.getRows(); i++) {
			if (!hoja.getCell(1, i).getContents().equals("")) {
				nroFilas++;
			}
		}
		return nroFilas;
	}

	/**
	 * Retorna el valor de una celda por columna y fila.
	 *
	 * @param columna
	 * @param fila
	 * @return Contenido celda
	 */
	public String leerCelda(int columna, int fila) {
		return hoja.getCell(columna, fila).getContents();
	}

	/**
	 * Lee nombres de columna.
	 *
	 * @param nombreColumna
	 * @return valor
	 */
	public int getCelda(String nombreColumna) {
		try {
			int value = columnas.get(nombreColumna).intValue();

			return value;
		} catch (NullPointerException e) {
			return 0;
		}
	}

	public Hashtable<String, Integer> getColumnas() {
		return columnas;
	}

}
