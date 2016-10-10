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

	private List<Method> metodos;

	private StringBuffer metodosDeTeste;
	
	private Annotation antecessora;

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
				
				setInternalBehaviors(annotations);
				//antecessora = null;
				for (Annotation ann : annotations) {
					TestMethodTemplate tmp = getAnnotatedGeneratorMethod(ann.annotationType(), method);
					if (tmp != null) {
						//verifyTestScene(tmp);
						
						String sufix = (methodMemory.contains(method.getName())) ? tmp.extractSufix() : "";
						metodosDeTeste.append(tmp.createMethod(sufix));
						methodMemory.add(method.getName() + sufix);
					}
					//antecessora = ann;
				}
			}
		}

		return metodosDeTeste.toString();
	}
	
	/*private void verifyTestScene(TestMethodTemplate tmp) {
		if(antecessora == null)
			return ;
		
		if(antecessora.annotationType() == Test.class)
			tmp.setScene(antecessora);
	}*/

	private TestMethodTemplate getAnnotatedGeneratorMethod(Class<? extends Annotation> ann, Method method) {
		TestMethodTemplate tmp = null;
		if (ann == TestVoidEquals.class)
			tmp = new TestAssertEqualsVoid(method, clazz);
		else if (ann == TestEquals.class)
			tmp = new TestAssertEquals(method, clazz);
		else if (ann == TestFalse.class)
			tmp = new TestAssertFalse(method, clazz);
		else if (ann == TestTrue.class)
			tmp = new TestAssertTrue(method, clazz);
		else if (ann == TestNotNull.class)
			tmp = new TestAssertNotNull(method, clazz);
		else if (ann == TestNull.class)
			tmp = new TestAssertNull(method, clazz);
		else if (ann == Test.class)
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
