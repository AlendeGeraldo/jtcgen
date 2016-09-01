package br.com.jtcgen.builder.methods;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import br.com.jtcgen.annotations.GenerateTestFalse;
import br.com.jtcgen.annotations.Param;
import br.com.jtcgen.exceptions.InvalidParamDeclarationException;
import br.com.jtcgen.helpers.TextEditor;

public class TestAssertFalse extends TestMethodTemplate {

	public TestAssertFalse(Method method, Class<?> clazz) {
		super(method, clazz);
	}

	@Override
	public String getContent() {
		GenerateTestFalse test = (GenerateTestFalse) method.getAnnotation(GenerateTestFalse.class);
		String parametro = test.value();

		String[] params = getParams(parametro);

		Parameter[] pts = method.getParameters();

		if (!isValidParams(params))
			throw new InvalidParamDeclarationException("Valor total de parametros incorretos");

		StringBuilder assinaturaMetodo = new StringBuilder();

		assinaturaMetodo.append(createMethodCall(pts, params));

		String content = TextEditor.newLine("assertFalse(resultado);", 2);
		assinaturaMetodo.append(content);

		return assinaturaMetodo.toString();
	}
}