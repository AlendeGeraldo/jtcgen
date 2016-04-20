package br.com.jtcgen;

import java.util.List;
import java.util.ArrayList;

import br.com.jtcgen.builder.EndTestGenerator;
import br.com.jtcgen.builder.SetUpGenerator;
import br.com.jtcgen.builder.TearDownGenerator;
import br.com.jtcgen.builder.TestClassGenerator;
import br.com.jtcgen.builder.TestGenerator;
import br.com.jtcgen.builder.TestMethodsGenerator;

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
		return null;
	}
}
