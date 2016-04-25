package br.com.jtcgen.builder;

class TestClassGenerator extends TestGenerator {

	private StringBuffer buffer;

	public TestClassGenerator(Class<?> clazz) {
		super(clazz);
	}

	public String generate() {
		StringBuffer buffer = new StringBuffer();

		String classe = clazz.getSimpleName();
		String importar = clazz.getName();
		String pacote = clazz.getPackage().getName();

		buffer.append("package test." + pacote + ";");
		buffer.append("\n\n");
		buffer.append("import static org.junit.Assert.*;");
		buffer.append("\n\n");
		buffer.append("import org.junit.After;");
		buffer.append("\n");
		buffer.append("import org.junit.Before;");
		buffer.append("\n");
		buffer.append("import org.junit.Test;");
		buffer.append("\n\n");
		buffer.append("import " + importar + ";");
		buffer.append("\n\n");
		buffer.append("public class " + classe + "Test {");
		buffer.append("\n\n");
		buffer.append("\t");
		buffer.append("private " + classe + " instance;");
		buffer.append("\n");
		buffer.append("\n");

		return buffer.toString();
	}

}
