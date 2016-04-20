package br.com.jtcgen;

import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

import br.com.jtcgen.annotations.JTCGen;
import br.com.jtcgen.builder.EndTestGenerator;
import br.com.jtcgen.builder.SetUpGenerator;
import br.com.jtcgen.builder.TearDownGenerator;
import br.com.jtcgen.builder.TestClassGenerator;
import br.com.jtcgen.builder.TestGenerator;
import br.com.jtcgen.builder.TestMethodsGenerator;
import example.classes.ContaAplicacao;
import example.classes.ContaCorrente;
import javafx.stage.DirectoryChooser;

public class JTCGenenerator {

	public void generateTests(Object... objects) {
		int i = 0;
		Class<?>[] classes = new Class<?>[objects.length];
		for (Object obj : objects)
			classes[i++] = obj.getClass();

		generate(classes);
	}

	public void generateTests(Class<?>... classes) {
		generate(classes);
	}

	private void generate(Class<?>... classes) {
		for (Class<?> classe : classes) {
			if (classe.isAnnotationPresent(JTCGen.class)) {
				StringBuffer buffer = new StringBuffer();

				List<TestGenerator> gens = TestGeneratorFactory.createGenerators(classe);
				for (TestGenerator gen : gens) {
					buffer.append(gen.generate());
				}

				TestDirectoryGenerator gen = new TestDirectoryGenerator(classe.getPackage());
				String testName = classe.getSimpleName() + "Test.java";
				try {
					gen.createFileTestCase(gen.createDiretories(), testName, buffer.toString());
				} catch (FileNotFoundException e) {
					System.out.println("Não foi possível criar o caso de teste " + testName);
				}
			}
		}
	}
}
