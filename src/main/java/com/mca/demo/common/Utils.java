package com.mca.demo.common;

import com.google.gson.Gson;

public class Utils {

	private Utils() {

	}
	
	/**
	 * Genera un prefijo de linea para el LOG
	 * 
	 * @return texto tipo "[ClaseActual] MetodoActual -> "
	 */
	public static String prefLog() {		

		String sMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		String sClassName  = Thread.currentThread().getStackTrace()[2].getClassName();

		String[] ruta = sClassName.split("[.]");

		if (ruta==null || ruta.length == 0) return "";

		return "["+ruta[ruta.length-1]+"] "+sMethodName+" -> ";
	}
	
	/**
	 * Metodo para convertir un objeto en un json
	 * 
	 * @param object Objeto de entrada
	 * @return El json generado
	 */
	public static String toJson(Object object) {
		Gson gson = new Gson();
		return gson.toJson(object);
	}

}
