package br.com.jtcgen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;

import sun.print.PrinterJobWrapper;

public class TestDirectoryGenerator {

	private Package pacote;
	private String basePath;

	public TestDirectoryGenerator(Package pacote) {
		this.pacote = pacote;
		this.basePath = "src/test";
	}

	public String createDiretories() {
		String[] strings = this.pacote.getName().split("\\.");

		StringBuffer path = new StringBuffer(basePath + "/");
		for (String string : strings)
			path.append(string + "/");

		new File(path.toString()).mkdirs();

		return path.toString();
	}

	public void createFileTestCase(String path, String fileName, String content) throws FileNotFoundException {
		PrintStream input = new PrintStream(new File(path + fileName));

		input.print(content);

		input.close();

	}

}
