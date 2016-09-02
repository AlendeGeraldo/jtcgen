package br.com.jtcgen.builder.methods;

import static br.com.jtcgen.builder.methods.TestInternalBehaviors.mocks;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import br.com.jtcgen.annotations.DataBuilder;
import static br.com.jtcgen.builder.methods.TestInternalBehaviors.*;

public class DataBuilderInterpreter implements IInternalBehaviors{

	@Override
	public String behave(Method m) {
		StringBuffer sb = new StringBuffer();
		List<Annotation> dataBuilders = dataBuilders();
		
		for (Annotation ann : dataBuilders) {
			DataBuilder dataBuilder = m.getAnnotation(DataBuilder.class);
			String expr = dataBuilder.value();
			
			//do something
		}
		
		return sb.toString();
	}

}
