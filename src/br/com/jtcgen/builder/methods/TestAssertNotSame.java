package br.com.jtcgen.builder.methods;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import br.com.jtcgen.annotations.Expected;
import br.com.jtcgen.annotations.TestNotSame;
import br.com.jtcgen.annotations.MethodCompare;
import br.com.jtcgen.annotations.Param;
import br.com.jtcgen.exceptions.InvalidDeclaredAnnotationException;
import br.com.jtcgen.exceptions.InvalidParamDeclarationException;
import br.com.jtcgen.helpers.TextEditor;

public class TestAssertNotSame extends TestMethodTemplate {

	public TestAssertNotSame(Method method, Class<?> clazz) {
		super(method, clazz);
	}

	@Override
	public String getContent() {
		TestNotSame test = (TestNotSame) method.getAnnotation(TestNotSame.class);
		String parameter = test.value()[0];
		String expected = test.value()[1];
		String metCompare = test.value()[2];

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

		String content = TextEditor.newLine("assertNotSame(resultado);", 2);
		methodSignature.append(content);

		return methodSignature.toString();
	}
}