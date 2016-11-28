package br.com.jtcgen.builder.validate;

public class Warning extends Validate{

	public Warning(String code, String description) {
		super(code, description, ValidateType.WARNING);
	}

}
