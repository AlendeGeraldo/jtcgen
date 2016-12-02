package br.com.jtcgen.builder.methods;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import br.com.jtcgen.annotations.TestEquals;
import br.com.jtcgen.annotations.TestVoidEquals;
import br.com.jtcgen.exceptions.InvalidParamDeclarationException;
import br.com.jtcgen.helpers.TextEditor;

public class TestAssertEqualsVoid extends TestMethodTemplate {

	public TestAssertEqualsVoid(Method method, Class<?> clazz) {
		super(method, clazz);
	}

	@Override
	public String getContent() {
		TestVoidEquals[] tests = method.getAnnotationsByType(TestVoidEquals.class);
		
		StringBuffer todosMetodos = new StringBuffer();
		int countAnn = 1;
		for(TestVoidEquals test: tests) {
			String strCountAnn = String.valueOf(countAnn);
		
			String parametro = test.value()[0];
			String compare = test.value()[1];
			String expected = test.value()[2];
	
			String[] params = getParams(parametro);
	
			Parameter[] pts = method.getParameters();
	
			if (!isValidParams(params))
				throw new InvalidParamDeclarationException("Valor total de parametros incorretos");
	
			StringBuilder assinaturaMetodo = new StringBuilder();
			
			assinaturaMetodo.append(createMethodCall(pts, params));
	
			Method methodCompare;
			String paramAdicionais;
			try {
				methodCompare = clazz.getMethod(compare);
	
				paramAdicionais = (methodCompare.getReturnType() == double.class) ? ", 0.00001" : "";
			} catch (NoSuchMethodException | SecurityException e) {
				// Metodo não existe, criar log;
				paramAdicionais = "";
			}
	
			String resultExpected = parseExpectedValue(expected, method);
	
			String comparado = "this.instance." + compare + "()";
			String content = TextEditor
					.newLine("assertEquals(" + resultExpected + ", " + comparado + paramAdicionais + ");", 2);
			assinaturaMetodo.append(content);
	
			todosMetodos.append(assinaturaMetodo.toString());
			
			countAnn++;
		}
		
		return todosMetodos.toString();

	}
}
