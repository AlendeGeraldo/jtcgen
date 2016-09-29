package br.com.jtcgen.builder;

import java.util.List;
import java.util.ArrayList;

/**
 * Factory of strategy patterns Test generators objects
 * 
 * @author Rafael Henrique Ap. Gonçalves <rafael.goncalves19@fatec.sp.gov.br>
 */
public class TestGeneratorFactory {
	/**
	 * make all generators instance
	 * 
	 * @param clazz
	 * @return List<TestGenerator>
	 */
	public static List<TestGenerator> createGenerators(Class<?> clazz) {
		List<TestGenerator> gens = new ArrayList<TestGenerator>();

		gens.add(new TestClassGenerator(clazz));

		gens.add(new SetUpGenerator(clazz));

		gens.add(new TearDownGenerator(clazz));

		gens.add(new TestMethodsGenerator(clazz));

		gens.add(new EndTestGenerator(clazz));

		return gens;
	}

	/**
	 * Cria a instancia do gerador de diretorios e arquivos
	 * 
	 * @param clazz
	 * @return {@link DirectoryGenerator}
	 */
	public static DirectoryGenerator createDirectoryGenerator(Class<?> clazz) {
		return new DirectoryGenerator(clazz);
	}
}
