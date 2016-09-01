package br.com.jtcgen.builder.methods;

import java.lang.reflect.Method;

public class MockParam extends TestMethodTemplate{
	
	public MockParam(Method method, Class<?> clazz) {
		super(method, clazz);
	}
	
	@Override
	public String createMethod() {
		// TODO Auto-generated method stub
		return super.createMethod();
	}

	@Override
	protected String getContent() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
