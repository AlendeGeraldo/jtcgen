package br.com.jtcgen.builder;

public class EndTestGenerator extends TestGenerator {

	public EndTestGenerator(Class<?> clazz) {
		super(clazz);
	}

	@Override
	public String generate() {
		StringBuffer buffer = new StringBuffer("}");
		return buffer.toString();
	}

}
