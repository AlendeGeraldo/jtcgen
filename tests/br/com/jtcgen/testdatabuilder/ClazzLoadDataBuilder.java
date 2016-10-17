package br.com.jtcgen.testdatabuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import br.com.jtcgen.helpers.ImportManager;

public class ClazzLoadDataBuilder {
	
	public static void getLocalClasses() {
		String pathName = System.getProperty("user.dir") + "\\src";
		List<Class<?>> classes = new ArrayList<Class<?>>();
		try {
			Files.walk(Paths.get(pathName)).forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					if (filePath.getFileName().toString().trim().matches("[A-Za-z0-9]+.java$")) {
						try {
							String className = filePath.toFile().getAbsolutePath().replaceAll(".+src\\\\", "")
									.replaceAll("\\\\", ".").replaceAll("\\.java$", "").toString().trim();
							classes.add(Class.forName(className));
						} catch (Exception e) {
							//e.printStackTrace();
							System.out.println("não foi possivel encontrar a classe");
						}
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		Class<?>[] arrClasses = new Class<?>[classes.size()];
		int i = 0;

		for (Class<?> classe : classes)
			arrClasses[i++] = classe;
		
		ImportManager.addMapedReflections(arrClasses);
	}
	
}
