package br.com.jtcgen;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import br.com.jtcgen.annotations.JTCGen;
import br.com.jtcgen.builder.DirectoryGenerator;
import br.com.jtcgen.builder.TestGenerator;
import br.com.jtcgen.builder.TestGeneratorFactory;
import br.com.jtcgen.helpers.ImportManager;

/**
 * Facade para cricao dos casos de testes
 * 
 * @author Rafael Henrique Ap. Goncalves <rafael.goncalves19@fatec.sp.gov.br>
 * @author Estevam Herculano
 */
public class JTCGenenerator implements TestCaseGenerable {
	
	public static final boolean ENABLE_BACKUP = true;
	public static final boolean DISABLE_BACKUP = false;

	/**
	 * funcao sobrecarregada para aceitar var-args de objetos
	 * 
	 * @param Objects...
	 *            objects
	 */
	
	public void generateTests(Object... objects) {
		generateTests(ENABLE_BACKUP, objects);
		
	}
	public void generateTests(boolean makeABackup, Object... objects) {
		int i = 0;
		Class<?>[] classes = new Class<?>[objects.length];
		for (Object obj : objects)
			classes[i++] = obj.getClass();

		generate(makeABackup, classes);

		System.out.println("Casos de testes criados.");
	}

	/**
	 * direciona a reflexao de todas as classes que serao gerados os casos de
	 * testes
	 * 
	 * @param classe
	 */
	
	public void generateTests(String sourceDir, Class<?>... classes) {
		generateTests("src/", ENABLE_BACKUP, classes);
	}
	
	/**
	 * direciona a reflexao de todas as classes que serao gerados os casos de
	 * testes
	 * 
	 * @param classe
	 */
	public void generateTests(String sourceDir,  boolean makeABackup, Class<?>... classes) {
		generate(makeABackup, classes);
	}
	
	public void generateTests() {
		generateTests("src/", ENABLE_BACKUP);
	}
	
	public void generateTests(String sourceDir) {
		generateTests(sourceDir, ENABLE_BACKUP);
	}

	public void generateTests(String sourceDir, boolean makeABackup) {
		String separator = String.valueOf(File.separatorChar);
		if(separator.equals("\\"))
			separator = new String("\\\\");
		String pathName = System.getProperty("user.dir") + separator + sourceDir.replaceAll("/", separator);
		List<Class<?>> classes = new ArrayList<Class<?>>();
		try {
			Files.walk(Paths.get(pathName)).forEach(filePath -> {
				String separator2 = String.valueOf(File.separatorChar);
				if(separator2.equals("\\"))
					separator2 = new String("\\\\");
				System.out.println(sourceDir);
				System.out.println(sourceDir.replaceAll("/", separator2));
				System.out.println(separator2);
				if (Files.isRegularFile(filePath)) {
					if (filePath.getFileName().toString().trim().matches("[A-Za-z0-9]+.java$")) {
						try {
							
							String remove = sourceDir.replaceAll("/", separator2);
							String className = filePath.toFile().getAbsolutePath().replaceAll(".+src" + separator2, "")
									.replaceAll(separator2, ".").replaceAll("\\.java$", "").toString().trim();
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
		
		generate(makeABackup, arrClasses);
	}

	private void generate(boolean makeABak, Class<?>... classes) {
		for (Class<?> classe : classes) {
			if (classe.isAnnotationPresent(JTCGen.class)) {
				StringBuffer buffer = new StringBuffer();

				List<TestGenerator> gens = TestGeneratorFactory.createGenerators(classe);
				for (TestGenerator gen : gens) {
					buffer.append(gen.generate());
				}

				DirectoryGenerator dir = TestGeneratorFactory.createDirectoryGenerator(classe);
				dir.createTest(
						buffer.toString().replaceFirst("\\{\\{OTHER_IMPORTS\\}\\}", ImportManager.getImports()),
						makeABak
				);
			}
		}
	}
}
