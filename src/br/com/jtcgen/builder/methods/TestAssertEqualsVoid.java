package br.com.jtcgen.builder.methods;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import br.com.jtcgen.annotations.Expected;
import br.com.jtcgen.annotations.GenerateTestVoidEquals;
import br.com.jtcgen.annotations.MethodCompare;
import br.com.jtcgen.annotations.Param;
import br.com.jtcgen.exceptions.InvalidParamDeclarationException;
import br.com.jtcgen.helpers.TextEditor;

public class TestAssertEqualsVoid extends TestMethodTemplate {

	public TestAssertEqualsVoid(Method method, Class<?> clazz) {
		super(method, clazz);
	}

	@Override
	public String getContent() {
		GenerateTestVoidEquals test = (GenerateTestVoidEquals) method.getAnnotation(GenerateTestVoidEquals.class);
		Param parametro = test.param();
		MethodCompare compare = test.compare();
		Expected expected = test.expected();

		String[] params = getParams(parametro);

		Parameter[] pts = method.getParameters();

		if (!isValidParams(params))
			throw new InvalidParamDeclarationException("Valor total de parametros incorretos");

		StringBuilder assinaturaMetodo = new StringBuilder();

		assinaturaMetodo.append(createMethodCall(pts, params));

		Method methodCompare;
		String paramAdicionais;
		try {
			methodCompare = clazz.getMethod(compare.value());

			paramAdicionais = (methodCompare.getReturnType() == double.class) ? ", 0.00000000001" : "";
		} catch (NoSuchMethodException | SecurityException e) {
			// Metodo não existe, criar log;
			paramAdicionais = "";
		}

		String comparado = "this.instance." + compare.value() + "()";
		String content = TextEditor
				.newLine("assertEquals(" + expected.value() + ", " + comparado + paramAdicionais + ");", 2);
		assinaturaMetodo.append(content);

		return assinaturaMetodo.toString();

	}
}
