package br.com.jtcgen.builder.methods;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import br.com.jtcgen.annotations.Expected;
import br.com.jtcgen.annotations.GenerateTestSame;
import br.com.jtcgen.annotations.MethodCompare;
import br.com.jtcgen.annotations.Param;
import br.com.jtcgen.exceptions.InvalidDeclaredAnnotationException;
import br.com.jtcgen.exceptions.InvalidParamDeclarationException;
import br.com.jtcgen.helpers.TextEditor;

public class TestAssertSame extends TestMethodTemplate {

	public TestAssertSame(Method method, Class<?> clazz) {
		super(method, clazz);
	}

	@Override
	public String getContent() {
		GenerateTestSame test = (GenerateTestSame) method.getAnnotation(GenerateTestSame.class);
		String parameter = test.param();
		String expected = test.expected();
		String metCompare = test.method();

		String[] params = getParams(parameter);

		Parameter[] pts = method.getParameters();

		if (!isValidParams(params))
			throw new InvalidParamDeclarationException("Valor total de parametros incorretos");

		if (hasMethodCompareOrExpected(metCompare, expected))
			throw new InvalidDeclaredAnnotationException(
					"A anotação" + test.getClass().getSimpleName() + " foi anotada de maneira errada.");

		StringBuilder methodSignature = new StringBuilder();

		methodSignature.append(createMethodCall(pts, params));

		// RESOLVER PROBLEMAS DE INVOCAÇÃO DO MÉTODO;
		String result = null;
		if (!metCompare.equals("{{NULL}}")) {
			String str = null;
			try {
				Method m = clazz.getDeclaredMethod(metCompare);
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result = metCompare;
		} else {
			// RESOLVER PROBLEMAS DE DECLARAÇÃO DA VARIAVEL DE COMPARAÇÃO;
			result = expected;
		}

		String content = TextEditor.newLine("assertSame(resultado);", 2);
		methodSignature.append(content);

		return methodSignature.toString();
	}
}