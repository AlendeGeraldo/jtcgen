package br.com.jtcgen.builder.methods;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public abstract class TestMethodTemplate {

	protected StringBuffer methodBuffer = new StringBuffer();
	protected Class<?> clazz;
	protected Method method;

	public static final String TAB = "\t";
	public static final String LINE_BREAK = "\n";

	public TestMethodTemplate(Method method, Class<?> clazz) {
		this.method = method;
		this.clazz = clazz;

	}

	public String createMethod() {

		methodBuffer.append(getSignatureMethod());

		methodBuffer.append(getContent());

		methodBuffer.append(getEndMethod());

		return methodBuffer.toString();
	}

	protected final String getSignatureMethod() {
		return newLine("@Test", 1) + newLine("public void " + method.getName() + "() {", 1);
	}

	protected abstract String getContent();

	protected String newLine(String content, int numberOfTabs) {
		StringBuffer line = new StringBuffer(LINE_BREAK);
		for (int i = 0; i < numberOfTabs; i++)
			line.append(TAB);

		line.append(content);

		return line.toString();
	}

	protected final String getEndMethod() {
		return newLine("}" + newLine("", 1), 1);
	}

	protected String createMethodCall(Parameter[] pts, String[] params) {
		int count = 0;
		StringBuilder buffer = new StringBuilder();

		if (!(method.getReturnType() == void.class))
			buffer.append(method.getReturnType() + " resultado = ");

		buffer.append("this.instance." + method.getName() + "(");
		for (Parameter p : pts) {
			Class<?> type = p.getType().getClass();

			String param;
			if (type == String.class) {
				param = '"' + params[count] + '"';
			} else {
				param = params[count];
			}

			buffer.append(((params.length - 1) == count) ? param : param + ",");
			count++;
		}

		buffer.append(");");

		return newLine(buffer.toString(), 2);
	}

	protected String getParamAdicional() {
		StringBuffer retorno = new StringBuffer();
		if (method.getReturnType() == double.class)
			retorno.append(", 0.000000000001");

		return retorno.toString();
	}

}