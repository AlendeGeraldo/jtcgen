package br.com.jtcgen.builder.methods;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import br.com.jtcgen.annotations.TestEquals;
import br.com.jtcgen.annotations.TestNull;
import br.com.jtcgen.exceptions.InvalidParamDeclarationException;
import br.com.jtcgen.helpers.TextEditor;

public class TestAssertNull extends TestMethodTemplate {

	public TestAssertNull(Method method, Class<?> clazz) {
		super(method, clazz);
	}

	@Override
	public String getContent() {
		TestNull[] tests = method.getAnnotationsByType(TestNull.class);
		
		StringBuffer todosMetodos = new StringBuffer();
		int countAnn = 1;
		for(TestNull test: tests) {
			String strCountAnn = String.valueOf(countAnn);
		
			String parameter = test.value();
	
			String[] params = getParams(parameter);
	
			Parameter[] pts = method.getParameters();
	
			if (!isValidParams(params))
				throw new InvalidParamDeclarationException("Valor total de parametros incorretos");
	
			StringBuilder methodSignature = new StringBuilder();
	
			methodSignature.append(createMethodCall(pts, params, strCountAnn));
	
			String content = TextEditor.newLine("assertNull(resultado"+strCountAnn+");", 2);
			methodSignature.append(content);

			todosMetodos.append(methodSignature.toString());
			
			countAnn++;
		}
		
		return todosMetodos.toString();
	}
}