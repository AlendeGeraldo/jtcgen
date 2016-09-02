package br.com.jtcgen.builder.methods;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import br.com.jtcgen.annotations.Mock;

import static br.com.jtcgen.builder.methods.TestInternalBehaviors.*;

public class MockInterpreter implements IInternalBehaviors{

	@Override
	public String behave(Method m) {
		StringBuffer sb = new StringBuffer();
		List<Annotation> mocks = mocks();
		
		for (Annotation ann : mocks) {
			Mock mock = m.getAnnotation(Mock.class);
			String expr = mock.value();
			
			//do something
		}
		
		return sb.toString();
	}
	
}
