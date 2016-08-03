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
 * Façade para criação dos casos de testes
 * 
 * @author Rafael Henrique Ap. Gonçalves <rafael.goncalves19@fatec.sp.gov.br>
 * @author Estevam Herculano
 */
public class JTCGenenerator implements TestCaseGenerable {

	/**
	 * função sobrecarregada para aceitar var-args de objetos
	 * 
	 * @param Objects...
	 *            objects
	 */
	public void generateTests(Object... objects) {
		int i = 0;
		Class<?>[] classes = new Class<?>[objects.length];
		for (Object obj : objects)
			classes[i++] = obj.getClass();

		generate(classes);

		System.out.println("Casos de testes criados.");
	}

	/**
	 * direciona a reflexão de todas as classes que serão gerados os casos de
	 * testes
	 * 
	 * @param classe
	 */
	public void generateTests(Class<?>... classes) {
		generate(classes);
	}

	public void generateTests() {
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
							e.printStackTrace();
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

		generate(arrClasses);
	}

	private void generate(Class<?>... classes) {
		for (Class<?> classe : classes) {
			if (classe.isAnnotationPresent(JTCGen.class)) {
				StringBuffer buffer = new StringBuffer();

				List<TestGenerator> gens = TestGeneratorFactory.createGenerators(classe);
				for (TestGenerator gen : gens) {
					buffer.append(gen.generate());
				}

				DirectoryGenerator dir = TestGeneratorFactory.createDirectoryGenerator(classe);
				dir.createTest(buffer.toString().replaceFirst("\\{\\{OTHER_IMPORTS\\}\\}", ImportManager.getImports()));
			}
		}
	}
}
