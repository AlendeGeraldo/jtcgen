package br.com.jtcgen.builder.validate;

import java.util.ArrayList;
import java.util.List;

public class Validator {
	
	private static List<Validate> validations;
	
	public static void createError(String code, String description) {
		Validator.add(new Error(code, description));
	}
	
	public static void createWarning(String code, String description) {
		Validator.add(new Error(code, description));
	}
	
	public static void add(Validate v) {
		Validator.validations.add(v);
	}
	
	public static List<Validate> getAll() {
		return Validator.get(0);
	}
	
	public static List<Validate> getErros() {
		return Validator.get(ValidateType.ERROR.getType());
	}
	
	public static List<Validate> getWarnings() {
		return Validator.get(ValidateType.WARNING.getType());
	}
	
	public static List<Validate> get(int type) {
		if(type < 1 && type > 2)
			return Validator.validations;
		
		List<Validate> list = new ArrayList<Validate>();
		for(Validate v : Validator.validations) 
			if(v.getType() == type)
				list.add(v);
		
		return list;
	}
}
