package br.com.jtcgen.builder;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import br.com.jtcgen.annotations.Expected;
import br.com.jtcgen.annotations.GenerateTestEquals;
import br.com.jtcgen.annotations.GenerateTestVoidEquals;
import br.com.jtcgen.annotations.MethodCompare;
import br.com.jtcgen.annotations.Param;

class TestMethodsGenerator extends TestGenerator {

	private StringBuffer buffer;

	private List<Method> metodos;

	public TestMethodsGenerator(Class<?> clazz) {
		super(clazz);
		metodos = new ArrayList<Method>();
		for (Method method : clazz.getMethods())
			this.metodos.add(method);
	}

	@Override
	public String generate() {
		StringBuffer metodosDeTeste = new StringBuffer();

		for (Method method : metodos) {
			if (method.isAnnotationPresent(GenerateTestVoidEquals.class)) {
				metodosDeTeste.append("\n\t" + "@Test");
				metodosDeTeste.append("\n\t public void " + method.getName() + "() {");
				GenerateTestVoidEquals test = (GenerateTestVoidEquals) method
						.getAnnotation(GenerateTestVoidEquals.class);
				Param parametro = test.param();
				MethodCompare compare = test.compare();
				Expected expected = test.expected();

				String[] params = parametro.value().split(";");

				Parameter[] pts = method.getParameters();

				if (params.length != method.getParameterCount())
					throw new InvalidParamDeclarationExeption("Valor total de parametros incorretos");

				int count = 0;
				StringBuilder assinaturaMetodo = new StringBuilder();
				assinaturaMetodo.append("\n\t\t");

				if (!(method.getReturnType() == void.class))
					assinaturaMetodo.append(method.getReturnType() + " resultado = ");

				assinaturaMetodo.append("this.instance." + method.getName() + "(");
				for (Parameter p : pts) {
					Class<?> type = p.getType().getClass();

					String param;
					if (type == String.class) {
						param = '"' + params[count] + '"';
					} else {
						param = params[count];
					}

					if (count == params.length - 1)
						assinaturaMetodo.append(param);
					else
						assinaturaMetodo.append(param + ",");
					count++;
				}

				boolean deltaParam;
				try {
					Method met = clazz.getMethod(compare.value());

					deltaParam = met.getReturnType() == double.class;
				} catch (NoSuchMethodException e) {
					deltaParam = false;
				}
				assinaturaMetodo.append(");");
				assinaturaMetodo.append("\n\t\t");
				String delta;
				if (deltaParam)
					delta = ", 0.00000000000001";
				else
					delta = "";
				String comparado = "this.instance." + compare.value() + "()" + delta;
				assinaturaMetodo.append("assertEquals(" + expected.value() + ", " + comparado + ");");
				assinaturaMetodo.append("\n\t" + "}");
				assinaturaMetodo.append("\n");

				metodosDeTeste.append(assinaturaMetodo.toString());
			}

			else if (method.isAnnotationPresent(GenerateTestEquals.class)) {
				metodosDeTeste.append("\n\t" + "@Test");
				metodosDeTeste.append("\n\t public void " + method.getName() + "() {");
				GenerateTestEquals test = (GenerateTestEquals) method.getAnnotation(GenerateTestEquals.class);
				Param parametro = test.param();
				Expected expected = test.expected();

				String[] params = parametro.value().split(";");

				Parameter[] pts = method.getParameters();

				if (params.length != method.getParameterCount())
					throw new InvalidParamDeclarationExeption("Valor total de parametros incorretos");

				int count = 0;
				StringBuilder assinaturaMetodo = new StringBuilder();
				assinaturaMetodo.append("\n\t\t");

				assinaturaMetodo.append(method.getReturnType() + " resultado = ");

				assinaturaMetodo.append("this.instance." + method.getName() + "(");
				for (Parameter p : pts) {
					Class<?> type = p.getType().getClass();

					String param;
					if (type == String.class) {
						param = '"' + params[count] + '"';
					} else {
						param = params[count];
					}

					if (count == params.length - 1)
						assinaturaMetodo.append(param);
					else
						assinaturaMetodo.append(param + ",");
					count++;
				}

				boolean deltaParam = method.getReturnType() == double.class;

				String delta;
				if (deltaParam)
					delta = ", 0.00000000000001";
				else
					delta = "";

				assinaturaMetodo.append(");");
				assinaturaMetodo.append("\n\t\t");
				assinaturaMetodo.append("assertEquals(" + expected.value() + ", resultado" + delta + ");");
				assinaturaMetodo.append("\n\t" + "}");
				assinaturaMetodo.append("\n");

				metodosDeTeste.append(assinaturaMetodo.toString());
			}
		}

		return metodosDeTeste.toString();
	}

}
