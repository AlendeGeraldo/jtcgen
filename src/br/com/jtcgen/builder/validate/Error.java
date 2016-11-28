package br.com.jtcgen.builder.validate;

public class Error extends Validate{

	public Error(String code, String description) {
		super(code, description, ValidateType.ERROR);
	}
	
	
}
