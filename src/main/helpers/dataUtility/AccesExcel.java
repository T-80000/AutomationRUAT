package main.helpers.dataUtility;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class AccesExcel {
	private ReaderExcel lectorExcel;
	private Hashtable<String, ArrayList<String>> atributos;
	private Object datos[][];

	public AccesExcel(String archivo)
	{
		this(archivo, 0);		
	}
	
	public AccesExcel(String archivo, int nroHoja)
	{
		lectorExcel = new ReaderExcel("./data/"+archivo, nroHoja);
		atributos   = new Hashtable<String, ArrayList<String>>();	
		cargarDatos();
	}
	
	public AccesExcel(String archivo, String hoja)
	{
		lectorExcel = new ReaderExcel("./data/"+archivo, hoja);
		atributos   = new Hashtable<String, ArrayList<String>>();	
		cargarDatos();
	}
	
	public Hashtable<String, ArrayList<String>> getAtributos()
	{
		return this.atributos;
	}
	
	public Hashtable<String, String> getAtributos(int fila_i)
	{
		Hashtable<String, String> atributosPorFila = new Hashtable<String, String>();
		
		for(String clave_i: getClaves())
		{
			atributosPorFila.put(clave_i, atributos.get(clave_i).get(fila_i));
		}		
		
		return atributosPorFila;
	}
	
	public ArrayList<String> getClaves()
	{
		Enumeration<String> enumeracionClaves = lectorExcel.getColumnas().keys();
		ArrayList<String>   claves            = new ArrayList<String>();
		
		for(int i = 0; i < getNroAtributos(); i ++)
		{
			claves.add(" ");
		}
		while (enumeracionClaves.hasMoreElements())
		{
			String e = enumeracionClaves.nextElement();
			claves.set(lectorExcel.getColumnas().get(e), e);
		}		
		return claves;
	}
	
	public ArrayList<String> getParametro(String i)
	{
		return atributos.get(i);
	}
	
	public void setParametro(String i, ArrayList<String> p)
	{
		atributos.put(i, p);
	}
	
	public int getNroAtributos()
	{		
		return lectorExcel.getColumnas().size();
	}
	
	public int getNroDatosPrueba()
	{
		return lectorExcel.getNumeroFilas();
	}
	
	/**
	* Lee los datos del test desde un archivo Excel
	*
	*/
	public void leerDatosExcel() 
	{		
		ArrayList<String> claves = getClaves();
		ArrayList<String> temp;		
		
		for (String c: claves)
		{
			temp = new ArrayList<String>();
			
			for(int i = 1; i <= lectorExcel.getNumeroFilas(); i ++)
				temp.add(lectorExcel.leerCelda(lectorExcel.getCelda(c), i));			

			setParametro(c, temp);
		}
	}
	
	public void cargarDatos()
	{
		leerDatosExcel();
		datos = new Object[getNroDatosPrueba()][getNroAtributos()]; 
		
		for(int i = 0; i < getNroDatosPrueba(); i++)
			for(int j = 0; j < getNroAtributos(); j++)
				datos[i][j] = getParametro(getClaves().get(j)).get(i);
	}
	
	public String getDatos(int i, int j)
	{
		return datos[i][j].toString();
	}
	
	public Object[] getFilaiDatos(int i)
	{
		return datos[i];
	}	
	
}
