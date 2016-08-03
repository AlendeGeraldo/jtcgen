package br.com.jtcgen.helpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.jtcgen.annotations.JTCGen;

public class ImportManager {

	private static Set<Class<?>> imports = new HashSet<Class<?>>();

	/**
	 * adiciona imports encontrados pelo caminho
	 * 
	 */
	public static void addImport(Class<?> clazz) {
		imports.add(clazz);
	}

	/**
	 * Consumir o método para substituir o {{OTHER_IMPORTS}} na string final
	 * 
	 */
	public static String getImports() {
		StringBuffer strImport = new StringBuffer();
		for (Class<?> im : imports)
			strImport.append("import " + im.getName() + ";" + "\n");

		imports.clear();

		return strImport.toString();
	}

}
