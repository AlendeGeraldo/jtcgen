package br.com.jtcgen.builder.validate;

public enum ValidateType {

	ERROR(1), WARNING(2);
	
	private int type;
	
	ValidateType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return this.type;
	}
}
