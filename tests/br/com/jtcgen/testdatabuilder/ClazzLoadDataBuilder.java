package br.com.jtcgen.testdatabuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import br.com.jtcgen.helpers.ImportManager;

public class ClazzLoadDataBuilder {
	
	public static void getLocalClasses() {
		String separator = String.valueOf(File.separatorChar);
		if(separator.equals("\\"))
			separator = new String("\\\\");
		String pathName = System.getProperty("user.dir") + separator + "src";
		List<Class<?>> classes = new ArrayList<Class<?>>();
		try {
			Files.walk(Paths.get(pathName)).forEach(filePath -> {
				String separator2 = String.valueOf(File.separatorChar);
				if(separator2.equals("\\"))
					separator2 = new String("\\\\");
				if (Files.isRegularFile(filePath)) {
					if (filePath.getFileName().toString().trim().matches("[A-Za-z0-9]+.java$")) {
						try {
							String className = filePath.toFile().getAbsolutePath().replaceAll(".+src" + separator2, "")
									.replaceAll(separator2, ".").replaceAll("\\.java$", "").toString().trim();
							System.out.println(className);
							classes.add(Class.forName(className));
						} catch (ClassNotFoundException e) {
							System.out.println(e.getMessage() + " ");
							//e.printStackTrace();
							System.out.println("nao foi possivel encontrar a classe");
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
