package br.com.jtcgen.builder.validate;

public class Validate {
	
	private String code;
	private String description;
	private ValidateType type;
	
	public Validate(String code, String description, ValidateType type) {
		this.code = code;
		this.description = description;
		this.type = type;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getType() {
		return this.type.getType();
	}
}
