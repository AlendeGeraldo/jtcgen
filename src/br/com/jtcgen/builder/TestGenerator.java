package br.com.jtcgen.builder;

public abstract class TestGenerator {

	protected Class<?> clazz;
	private StringBuffer buffer;

	public TestGenerator(Class<?> clazz) {
		this.clazz = clazz;
	}

	public abstract String generate();

	public StringBuffer getBuffer() {
		return buffer;
	}

	public void setBuffer(StringBuffer buffer) {
		this.buffer = buffer;
	}
	
	protected String makeInternalBehaviors(){
		StringBuilder sb = new StringBuilder();
		
		
			
		return null;
	}

}
