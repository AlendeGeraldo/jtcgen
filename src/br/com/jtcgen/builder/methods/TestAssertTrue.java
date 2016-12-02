package br.com.jtcgen.builder.methods;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import br.com.jtcgen.annotations.TestEquals;
import br.com.jtcgen.annotations.TestTrue;
import br.com.jtcgen.exceptions.InvalidParamDeclarationException;
import br.com.jtcgen.helpers.TextEditor;

public class TestAssertTrue extends TestMethodTemplate {

	public TestAssertTrue(Method method, Class<?> clazz) {
		super(method, clazz);
	}

	@Override
	public String getContent() {
		TestTrue[] tests = method.getAnnotationsByType(TestTrue.class);
		
		StringBuffer todosMetodos = new StringBuffer();
		int countAnn = 1;
		for(TestTrue test: tests) {
			String strCountAnn = String.valueOf(countAnn);
		
			String parametro = test.value();
	
			String[] params = getParams(parametro);
	
			Parameter[] pts = method.getParameters();
	
			if (!isValidParams(params))
				throw new InvalidParamDeclarationException("Valor total de parametros incorretos");
	
			StringBuilder assinaturaMetodo = new StringBuilder();
	
			assinaturaMetodo.append(createMethodCall(pts, params, strCountAnn));
	
			String content = TextEditor.newLine("assertTrue(resultado"+strCountAnn+");", 2);
			assinaturaMetodo.append(content);
	
			todosMetodos.append(assinaturaMetodo.toString());
			
			countAnn++;
		}
		
		return todosMetodos.toString();
	}
}