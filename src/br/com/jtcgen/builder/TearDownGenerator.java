package br.com.jtcgen.builder;

class TearDownGenerator extends TestGenerator {

	public TearDownGenerator(Class<?> clazz) {
		super(clazz);
	}

	@Override
	public String generate() {
		StringBuffer buffer = new StringBuffer("\n\t" + "@After");

		buffer.append("\n\t");
		buffer.append("public void tearDown() throws Exception {");
		buffer.append("\n\t\t");
		buffer.append("this.instance = null;");
		buffer.append("\n\t");
		buffer.append("}");
		buffer.append("\n");

		return buffer.toString();
	}
}