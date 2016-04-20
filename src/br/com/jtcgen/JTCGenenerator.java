package br.com.jtcgen;

import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import br.com.jtcgen.builder.EndTestGenerator;
import br.com.jtcgen.builder.SetUpGenerator;
import br.com.jtcgen.builder.TearDownGenerator;
import br.com.jtcgen.builder.TestClassGenerator;
import br.com.jtcgen.builder.TestGenerator;
import br.com.jtcgen.builder.TestMethodsGenerator;
import br.com.jtcgen.generator.annotations.JTCGen;
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

				TestGenerator testClass = new TestClassGenerator(classe);
				buffer.append(testClass.generate());

				TestGenerator setUp = new SetUpGenerator(classe);
				buffer.append(setUp.generate());

				TestGenerator tearDown = new TearDownGenerator(classe);
				buffer.append(tearDown.generate());

				TestGenerator annotatedMethods = new TestMethodsGenerator(classe);
				buffer.append(annotatedMethods.generate());

				TestGenerator endTest = new EndTestGenerator(classe);
				buffer.append(endTest.generate());

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
