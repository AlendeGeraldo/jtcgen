package br.com.jtcgen.builder.methods;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import br.com.jtcgen.annotations.TestEquals;
import br.com.jtcgen.annotations.TestsEquals;
import br.com.jtcgen.exceptions.InvalidParamDeclarationException;
import br.com.jtcgen.helpers.TextEditor;

public class TestAssertEquals extends TestMethodTemplate {

	public TestAssertEquals(Method method, Class<?> clazz) {
		super(method, clazz);
	}

	@Override
	public String getContent() {
		
		TestEquals[] tests = method.getAnnotationsByType(TestEquals.class);
		
		StringBuffer todosMetodos = new StringBuffer();
		int countAnn = 1;
		for(TestEquals test: tests) {
			String strCountAnn = String.valueOf(countAnn);
			
			String parametro = "", expected = "";
			if(test.value().length == 2){
				parametro = test.value()[0];
				expected = test.value()[1];
			} else 
				expected = test.value()[0];
			
	
			String[] params = getParams(parametro);
	
			Parameter[] pts = method.getParameters();
	
			if (!isValidParams(params))
				throw new InvalidParamDeclarationException("Valor total de parametros incorretos: " 
						+ method.getName());
	
			StringBuilder assinaturaMetodo = new StringBuilder();
			
			String paramAdicionais = getParamAdicional();
	
			String resultExpected = parseExpectedValue(expected, method);
	
			assinaturaMetodo.append(createMethodCall(pts, params, strCountAnn));
	
			String content = TextEditor.newLine("assertEquals(" + resultExpected + ", resultado" + strCountAnn + " " + paramAdicionais + ");",
					2);
			assinaturaMetodo.append(content);
	
			todosMetodos.append(assinaturaMetodo.toString());
			
			countAnn++;
		}
		
		return todosMetodos.toString();
	}
}