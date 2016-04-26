package br.com.jtcgen.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.jtcgen.annotations.GenerateTestEquals;
import br.com.jtcgen.annotations.GenerateTestFalse;
import br.com.jtcgen.annotations.GenerateTestNotNull;
import br.com.jtcgen.annotations.GenerateTestNull;
import br.com.jtcgen.annotations.GenerateTestTrue;
import br.com.jtcgen.annotations.GenerateTestVoidEquals;
import br.com.jtcgen.builder.methods.TestAssertEquals;
import br.com.jtcgen.builder.methods.TestAssertFalse;
import br.com.jtcgen.builder.methods.TestAssertNotNull;
import br.com.jtcgen.builder.methods.TestAssertNull;
import br.com.jtcgen.builder.methods.TestAssertTrue;
import br.com.jtcgen.builder.methods.TestMethodTemplate;
import br.com.jtcgen.builder.methods.TestAssertEqualsVoid;

class TestMethodsGenerator extends TestGenerator {

	private List<Method> metodos;

	private StringBuffer metodosDeTeste;

	public TestMethodsGenerator(Class<?> clazz) {
		super(clazz);
		metodos = new ArrayList<Method>();
		for (Method method : clazz.getMethods())
			this.metodos.add(method);
	}

	@Override
	public String generate() {
		metodosDeTeste = new StringBuffer();
		for (Method method : metodos) {
			Annotation[] annotations = method.getAnnotations();
			if (annotations.length > 0) {
				Set<String> methodMemory = new HashSet<String>();
				for (Annotation ann : annotations) {
					TestMethodTemplate tmp = getAnnotatedGeneratorMethod(ann.annotationType(), method);
					if (tmp != null) {
						String sufix = (methodMemory.contains(method.getName())) ? tmp.extractSufix() : "";
						metodosDeTeste.append(tmp.createMethod(sufix));
						methodMemory.add(method.getName() + sufix);
					}
				}
			}
		}

		return metodosDeTeste.toString();
	}

	private TestMethodTemplate getAnnotatedGeneratorMethod(Class<? extends Annotation> ann, Method method) {
		TestMethodTemplate tmp = null;
		if (ann == GenerateTestVoidEquals.class)
			tmp = new TestAssertEqualsVoid(method, clazz);
		else if (ann == GenerateTestEquals.class)
			tmp = new TestAssertEquals(method, clazz);
		else if (ann == GenerateTestFalse.class)
			tmp = new TestAssertFalse(method, clazz);
		else if (ann == GenerateTestTrue.class)
			tmp = new TestAssertTrue(method, clazz);
		else if (ann == GenerateTestNotNull.class)
			tmp = new TestAssertNotNull(method, clazz);
		else if (ann == GenerateTestNull.class)
			tmp = new TestAssertNull(method, clazz);

		return tmp;

	}

}
