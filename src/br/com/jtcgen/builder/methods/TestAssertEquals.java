package br.com.jtcgen.builder.methods;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import br.com.jtcgen.annotations.TestEquals;
import br.com.jtcgen.exceptions.InvalidParamDeclarationException;
import br.com.jtcgen.helpers.TextEditor;

public class TestAssertEquals extends TestMethodTemplate {

	public TestAssertEquals(Method method, Class<?> clazz) {
		super(method, clazz);
	}

	@Override
	public String getContent() {

		TestEquals test = (TestEquals) method.getAnnotation(TestEquals.class);
		
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

		assinaturaMetodo.append(createMethodCall(pts, params));

		String content = TextEditor.newLine("assertEquals(" + resultExpected + ", resultado" + paramAdicionais + ");",
				2);
		assinaturaMetodo.append(content);

		return assinaturaMetodo.toString();
	}
}