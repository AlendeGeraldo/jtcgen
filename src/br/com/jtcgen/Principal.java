package br.com.jtcgen;

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

class Principal {

	public static void main(String[] args) throws Exception {
		Class<?> classe = ContaAplicacao.class;

		if (classe.isAnnotationPresent(JTCGen.class)) {
			StringBuffer buffer = new StringBuffer();

			TestGenerator testClass = new TestClassGenerator(classe);
			System.out.println(testClass.generate());

			TestGenerator setUp = new SetUpGenerator(classe);

			System.out.println(setUp.generate());

			TestGenerator tearDown = new TearDownGenerator(classe);

			System.out.println(tearDown.generate());

			TestGenerator annotatedMethods = new TestMethodsGenerator(classe);

			System.out.println(annotatedMethods.generate());

			TestGenerator endTest = new EndTestGenerator(classe);

			System.out.println(endTest.generate());
		}

	}

}
