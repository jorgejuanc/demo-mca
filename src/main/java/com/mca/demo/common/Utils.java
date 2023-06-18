package com.mca.demo.common;

import com.google.gson.Gson;

public class Utils {

	private Utils() {

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
