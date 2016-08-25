package br.com.jtcgen.builder.methods;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import br.com.jtcgen.annotations.GenerateTestNotNull;
import br.com.jtcgen.annotations.Param;
import br.com.jtcgen.exceptions.InvalidParamDeclarationException;
import br.com.jtcgen.helpers.TextEditor;

public class TestAssertNotNull extends TestMethodTemplate {

	public TestAssertNotNull(Method method, Class<?> clazz) {
		super(method, clazz);
	}

	@Override
	public String getContent() {
		GenerateTestNotNull test = (GenerateTestNotNull) method.getAnnotation(GenerateTestNotNull.class);
		String parameter = test.value();

		String[] params = getParams(parameter);

		Parameter[] pts = method.getParameters();

		if (!isValidParams(params))
			throw new InvalidParamDeclarationException("Valor total de parametros incorretos");

		StringBuilder methodSignature = new StringBuilder();

		methodSignature.append(createMethodCall(pts, params));

		String content = TextEditor.newLine("assertNotNull(resultado);", 2);
		methodSignature.append(content);

		return methodSignature.toString();
	}
}