package br.com.jtcgen.builder;

class EndTestGenerator extends TestGenerator {

	public EndTestGenerator(Class<?> clazz) {
		super(clazz);
	}

	@Override
	public String generate() {
		StringBuffer buffer = new StringBuffer("\n" + "}");
		return buffer.toString();
	}

}
