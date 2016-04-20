package br.com.jtcgen.builder;

import java.util.List;
import java.util.ArrayList;

public class TestGeneratorFactory {

	public static List<TestGenerator> createGenerators(Class<?> clazz) {
		List<TestGenerator> gens = new ArrayList<TestGenerator>();

		gens.add(new TestClassGenerator(clazz));

		gens.add(new SetUpGenerator(clazz));

		gens.add(new TearDownGenerator(clazz));

		gens.add(new TestMethodsGenerator(clazz));

		gens.add(new EndTestGenerator(clazz));

		return gens;
	}

	public static TestDirectoryGenerator createDirectoryGenerator(Class<?> clazz) {
		return new TestDirectoryGenerator(clazz);
	}
}
