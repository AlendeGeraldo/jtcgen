package br.com.jtcgen.builder.methods;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import br.com.jtcgen.annotations.Expected;
import br.com.jtcgen.annotations.GenerateTestNotSame;
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
		GenerateTestNotSame test = (GenerateTestNotSame) method.getAnnotation(GenerateTestNotSame.class);
		String parameter = test.param();
		String expected = test.expected();
		String metCompare = test.method();

		String[] params = getParams(parameter);

		Parameter[] pts = method.getParameters();

		if (!isValidParams(params))
			throw new InvalidParamDeclarationException("Valor total de parametros incorretos");

		if (hasMethodCompareOrExpected(metCompare, expected))
			throw new InvalidDeclaredAnnotationException(
					"A anota��o" + test.getClass().getSimpleName() + " foi anotada de maneira errada.");

		StringBuilder methodSignature = new StringBuilder();

		methodSignature.append(createMethodCall(pts, params));

		// RESOLVER PROBLEMAS DE INVOCA��O DO M�TODO;
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
			// RESOLVER PROBLEMAS DE DECLARA��O DA VARIAVEL DE COMPARA��O;
			result = expected;
		}

		String content = TextEditor.newLine("assertNotSame(resultado);", 2);
		methodSignature.append(content);

		return methodSignature.toString();
	}
}