package br.com.jtcgen.builder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;

import sun.print.PrinterJobWrapper;

public class TestDirectoryGenerator {

	private String basePath;
	private Class<?> classe;

	public TestDirectoryGenerator(Class<?> classe) {

		this.classe = classe;
		this.basePath = "src/test";
	}

	public void createTest(String content) {
		String path = createDirectories();
		String file = path + classe.getSimpleName() + "Test.java";

		try {
			createFileTestCase(file, content);
		} catch (FileNotFoundException e) {
			System.out.println("Não foi possivel gerar a classe fisica: " + classe.getName());
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
