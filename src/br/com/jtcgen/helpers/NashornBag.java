package br.com.jtcgen.helpers;

import java.util.ArrayList;
import java.util.List;

public class NashornBag {
	
	/**
	 * Utilizado para não deixar repetir nomes de variaveis ja utilizadas
	 * */
	private static List<String> props = new ArrayList<String>();
	
	/**
	 * Usado para não deixar repetir variaveis que recebem o valor de comparação dos testes
	 * */
	private static int countExpected = 0;
	
	public static void add(String s) {
		props.add(s);
	}
	
	public static List<String> get() {
		return props;
	}
	
	public static void clear() {
		props.clear();
	}
	
	public static int getCountExpected() {
		return countExpected;
	}
	
	public static void incrementExpectedCount(int i) {
		countExpected += i;
	}
	
	public static void incrementExpectedCount() {
		countExpected++;
	}
	
	public static void clearCountExpected() {
		countExpected = 0;
	}
	
	public static void clearBag() {
		clear();
		clearCountExpected();
	}
}
