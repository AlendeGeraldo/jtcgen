package br.com.jtcgen.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class DirectoryGenerator {

	private String basePath = "tests";
	private String fileSufix = "Test.java";
	private boolean useAnotherSourcePath = true;
	private Class<?> classe;

	public DirectoryGenerator(Class<?> classe) {
		this.classe = classe;
	}
	
	public DirectoryGenerator(Class<?> classe, boolean useSourcePath) {

		this.classe = classe;
		this.useAnotherSourcePath = useSourcePath;
	}
	
	public DirectoryGenerator(Class<?> classe, String basePath) {
		this.classe = classe;
		this.basePath = basePath;
	}
	
	public DirectoryGenerator(Class<?> classe, String basePath, boolean useSourcePath) {
		this.classe = classe;
		this.basePath = basePath;
		this.useAnotherSourcePath = useSourcePath;
	}
	
	public DirectoryGenerator(Class<?> classe, String basePath, String fileSufix) {
		this.classe = classe;
		this.basePath = basePath;
		this.fileSufix = fileSufix;
	}
	
	public DirectoryGenerator(Class<?> classe, String basePath, String fileSufix, boolean useSourcePath) {
		this.classe = classe;
		this.basePath = basePath;
		this.fileSufix = fileSufix;
		this.useAnotherSourcePath = useSourcePath;
	}

	public void createTest(String content) {
		String path = createDirectories();
		String file = path + classe.getSimpleName() + fileSufix;
		
		try {
			createFileTestCase(file, content);
		} catch (FileNotFoundException e) {
			System.out.println("Não foi possivel criar o arquivo da classe diretório: " + classe.getName());
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
