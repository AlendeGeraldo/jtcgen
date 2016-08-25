package br.com.jtcgen.builder.methods;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import br.com.jtcgen.annotations.Expected;
import br.com.jtcgen.annotations.GenerateTestEquals;
import br.com.jtcgen.annotations.Param;
import br.com.jtcgen.exceptions.InvalidParamDeclarationException;
import br.com.jtcgen.helpers.TextEditor;

public class TestAssertEquals extends TestMethodTemplate {

	public TestAssertEquals(Method method, Class<?> clazz) {
		super(method, clazz);
	}

	@Override
	public String getContent() {

		GenerateTestEquals test = (GenerateTestEquals) method.getAnnotation(GenerateTestEquals.class);
		String parametro = test.param();
		String expected = test.expected();

		String[] params = getParams(parametro);

		Parameter[] pts = method.getParameters();

		if (!isValidParams(params))
			throw new InvalidParamDeclarationException("Valor total de parametros incorretos");

		StringBuilder assinaturaMetodo = new StringBuilder();

		assinaturaMetodo.append(createMethodCall(pts, params));

		String paramAdicionais = getParamAdicional();

		String resultExpected = parseExpectedValue(expected, method);

		String content = TextEditor.newLine("assertEquals(" + resultExpected + ", resultado" + paramAdicionais + ");",
				2);
		assinaturaMetodo.append(content);

		return assinaturaMetodo.toString();
	}
}