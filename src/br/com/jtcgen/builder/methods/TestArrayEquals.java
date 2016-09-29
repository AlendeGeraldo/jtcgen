package br.com.jtcgen.builder.methods;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import br.com.jtcgen.annotations.TestEquals;
import br.com.jtcgen.exceptions.InvalidParamDeclarationException;
import br.com.jtcgen.helpers.TextEditor;

public class TestArrayEquals extends TestMethodTemplate {

	public TestArrayEquals(Method method, Class<?> clazz) {
		super(method, clazz);
	}

	@Override
	public String getContent() {

		TestEquals test = (TestEquals) method.getAnnotation(TestEquals.class);
		String parametro = test.value()[0];
		String expected = test.value()[1];

		String[] params = getParams(parametro);

		Parameter[] pts = method.getParameters();

		if (params.length != method.getParameterCount())
			throw new InvalidParamDeclarationException("Valor total de parametros incorretos");

		StringBuilder assinaturaMetodo = new StringBuilder();

		assinaturaMetodo.append(createMethodCall(pts, params));

		String paramAdicionais = getParamAdicional();
		String[] array = expected.split("\\s?");
		String strExpected = TextEditor.newLine(array[0] + "[] expected = " + array[1] + ";", 2);
		assinaturaMetodo.append(strExpected);
		String content = TextEditor.newLine("assertArrayEquals(expected , resultado" + paramAdicionais + ");", 2);
		assinaturaMetodo.append(content);

		return assinaturaMetodo.toString();
	}
}
