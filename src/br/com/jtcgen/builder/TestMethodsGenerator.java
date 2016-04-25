package br.com.jtcgen.builder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import br.com.jtcgen.annotations.GenerateTestEquals;
import br.com.jtcgen.annotations.GenerateTestVoidEquals;
import br.com.jtcgen.builder.methods.TestAssertEquals;
import br.com.jtcgen.builder.methods.TestMethodTemplate;
import br.com.jtcgen.builder.methods.TestVoid;

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
			TestMethodTemplate tmp = getAnnotatedGeneratorMethod(method);
			if (tmp != null)
				metodosDeTeste.append(tmp.createMethod());
		}

		return metodosDeTeste.toString();
	}

	private TestMethodTemplate getAnnotatedGeneratorMethod(Method method) {
		TestMethodTemplate tmp = null;
		if (method.isAnnotationPresent(GenerateTestVoidEquals.class))
			tmp = new TestVoid(method, clazz);
		else if (method.isAnnotationPresent(GenerateTestEquals.class))
			tmp = new TestAssertEquals(method, clazz);

		return tmp;

	}

}
