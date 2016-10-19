package br.com.jtcgen.helpers;

import java.util.ArrayList;
import java.util.List;

public class NashornBag {
	
	private static List<String> props = new ArrayList<String>();
	
	public static void add(String s) {
		props.add(s);
	}
	
	public static List<String> get() {
		return props;
	}
	
	public static void clear() {
		props.clear();
	}
}
