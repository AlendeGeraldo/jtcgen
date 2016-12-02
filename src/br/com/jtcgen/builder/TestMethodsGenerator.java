package br.com.jtcgen.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.jtcgen.annotations.TestEquals;
import br.com.jtcgen.annotations.TestFalse;
import br.com.jtcgen.annotations.TestNotNull;
import br.com.jtcgen.annotations.TestNull;
import br.com.jtcgen.annotations.Test;
import br.com.jtcgen.annotations.TestTrue;
import br.com.jtcgen.annotations.TestVoidEquals;
import br.com.jtcgen.annotations.Tests;
import br.com.jtcgen.annotations.TestsEquals;
import br.com.jtcgen.annotations.TestsFalse;
import br.com.jtcgen.annotations.TestsNotNull;
import br.com.jtcgen.annotations.TestsNull;
import br.com.jtcgen.annotations.TestsTrue;
import br.com.jtcgen.annotations.TestsVoidEquals;
import br.com.jtcgen.builder.methods.TestAssertEquals;
import br.com.jtcgen.builder.methods.TestAssertFalse;
import br.com.jtcgen.builder.methods.TestAssertNotNull;
import br.com.jtcgen.builder.methods.TestAssertNull;
import br.com.jtcgen.builder.methods.TestAssertTrue;
import br.com.jtcgen.builder.methods.TestExpression;
import br.com.jtcgen.builder.methods.TestMethodTemplate;
import br.com.jtcgen.builder.methods.TestAssertEqualsVoid;

import static br.com.jtcgen.builder.methods.TestInternalBehaviors.*;

class TestMethodsGenerator extends TestGenerator {

	private List<Method> methods;
	private StringBuffer testMethod;
	private Annotation predecessor;

	public TestMethodsGenerator(Class<?> clazz) {
		super(clazz);
		methods = new ArrayList<Method>();
		for (Method method : clazz.getMethods())
			this.methods.add(method);
	}

	@Override
	public String generate() {
		testMethod = new StringBuffer();
		for (Method method : methods) {
			Annotation[] annotations = method.getAnnotations();
			if (annotations.length > 0) {
				Set<String> methodMemory = new HashSet<String>();
				
				setInternalBehaviors(annotations);
				//antecessora = null;
				for (Annotation ann : annotations) {
					TestMethodTemplate tmp = getAnnotatedGeneratorMethod(ann.annotationType(), method);
					if (tmp != null) {
						//verifyTestScene(tmp);
						
						String sufix = (methodMemory.contains(method.getName())) ? tmp.extractSufix() : "";
						testMethod.append(tmp.createMethod(sufix));
						methodMemory.add(method.getName() + sufix);
					}
					//antecessora = ann;
				}
			}
		}

		return testMethod.toString();
	}
	
	private TestMethodTemplate getAnnotatedGeneratorMethod(Class<? extends Annotation> ann, Method method) {
		TestMethodTemplate tmp = null;
		if (ann == TestVoidEquals.class || ann == TestsVoidEquals.class)
			tmp = new TestAssertEqualsVoid(method, clazz);
		else if (ann == TestEquals.class || ann == TestsEquals.class)
			tmp = new TestAssertEquals(method, clazz);
		else if (ann == TestFalse.class || ann == TestsFalse.class)
			tmp = new TestAssertFalse(method, clazz);
		else if (ann == TestTrue.class || ann == TestsTrue.class)
			tmp = new TestAssertTrue(method, clazz);
		else if (ann == TestNotNull.class || ann == TestsNotNull.class)
			tmp = new TestAssertNotNull(method, clazz);
		else if (ann == TestNull.class || ann == TestsNull.class)
			tmp = new TestAssertNull(method, clazz);
		else if (ann == Test.class || ann == Tests.class)
			tmp = new TestExpression(method, clazz);
			

		return tmp;

	}
	
	private void setInternalBehaviors(Annotation[] anns) {
		for (Annotation ann : anns) {
			if(isDataBuilder(ann))
				addDataBuilder(ann);
			else if(isMock(ann))
				addMock(ann);
		}
	}

}
