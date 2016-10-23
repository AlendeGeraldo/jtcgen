package br.com.jtcgen;

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
 * FaÃ§ade para criÃ§Ã£o dos casos de testes
 * 
 * @author Rafael Henrique Ap. GonÃ§alves <rafael.goncalves19@fatec.sp.gov.br>
 * @author Estevam Herculano
 */
public class JTCGenenerator implements TestCaseGenerable {
	
	public static final boolean ENABLE_BACKUP = true;
	public static final boolean DISABLE_BACKUP = false;

	/**
	 * funÃ§Ã£o sobrecarregada para aceitar var-args de objetos
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
	
	public void generateTests(Class<?>... classes) {
		generateTests(ENABLE_BACKUP, classes);
	}
	
	/**
	 * direciona a reflexao de todas as classes que serao gerados os casos de
	 * testes
	 * 
	 * @param classe
	 */
	public void generateTests(boolean makeABackup, Class<?>... classes) {
		generate(makeABackup, classes);
	}
	
	public void generateTests() {
		generateTests(ENABLE_BACKUP);
	}

	public void generateTests(boolean makeABackup) {
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
							System.out.println("nÃ£o foi possivel encontrar a classe");
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
