package br.com.jtcgen.builder.methods;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import br.com.jtcgen.annotations.Expected;
import br.com.jtcgen.annotations.GenerateTestVoidEquals;
import br.com.jtcgen.annotations.MethodCompare;
import br.com.jtcgen.annotations.Param;
import br.com.jtcgen.exceptions.InvalidParamDeclarationExeption;

public class TestVoid extends TestMethodTemplate {

	public TestVoid(Method method, Class<?> clazz) {
		super(method, clazz);
	}

	@Override
	public String getContent() {
		GenerateTestVoidEquals test = (GenerateTestVoidEquals) method.getAnnotation(GenerateTestVoidEquals.class);
		Param parametro = test.param();
		MethodCompare compare = test.compare();
		Expected expected = test.expected();

		String[] params = parametro.value().split(";");
		Parameter[] pts = method.getParameters();

		if (params.length != method.getParameterCount())
			throw new InvalidParamDeclarationExeption("Valor total de parametros incorretos");

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
		String content = newLine("assertEquals(" + expected.value() + ", " + comparado + paramAdicionais + ");", 2);
		assinaturaMetodo.append(content);

		return assinaturaMetodo.toString();

	}
}
