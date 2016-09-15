package br.com.jtcgen.builder.methods;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public interface IInternalBehaviors {

	public String behave(Method m, Annotation scene);
	
	
}
