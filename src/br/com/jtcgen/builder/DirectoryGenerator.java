package br.com.jtcgen.builder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;

import sun.print.PrinterJobWrapper;

public class DirectoryGenerator {

	private final String basePath = "src/test";
	private final String fileSufix = "Test.java";
	private Class<?> classe;

	public DirectoryGenerator(Class<?> classe) {

		this.classe = classe;
	}

	public void createTest(String content) {
		String path = createDirectories();
		String file = path + classe.getSimpleName() + fileSufix;

		try {
			createFileTestCase(file, content);
		} catch (FileNotFoundException e) {
			System.out.println("N�o foi possivel criar o arquivo da classe diret�rio: " + classe.getName());
		}
	}

	private String createDirectories() {
		String[] strings = this.classe.getPackage().getName().split("\\.");

		StringBuffer path = new StringBuffer(basePath + "/");
		for (String string : strings)
			path.append(string + "/");

		new File(path.toString()).mkdirs();

		return path.toString();
	}

	private void createFileTestCase(String file, String content) throws FileNotFoundException {
		PrintStream input = new PrintStream(new File(file));

		input.print(content);

		input.close();
	}

}
