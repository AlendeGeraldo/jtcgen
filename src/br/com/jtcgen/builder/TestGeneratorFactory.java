package br.com.jtcgen.builder;

import java.util.List;
import java.util.ArrayList;

/**
 * Fabrica da estrategia de objetos geradores
 * 
 * @author Rafael Henrique Ap. Gonçalves <rafael.goncalves19@fatec.sp.gov.br>
 */
public class TestGeneratorFactory {
	/**
	 * Cria todas as instancias dos geradores
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
