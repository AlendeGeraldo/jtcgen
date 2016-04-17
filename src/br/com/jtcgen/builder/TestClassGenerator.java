package br.com.jtcgen.builder;

public class TestClassGenerator extends TestGenerator {

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
		buffer.append("import " + importar + ";");
		buffer.append("\n\n");
		buffer.append("public class " + classe + "Test {");
		buffer.append("\n\n");
		buffer.append("\t");
		buffer.append("private " + classe + " instance;");
		buffer.append("\n");

		return buffer.toString();
	}

}
