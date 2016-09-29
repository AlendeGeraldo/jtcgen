package br.com.jtcgen.builder.methods;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import br.com.jtcgen.annotations.DataBuilder;
import br.com.jtcgen.annotations.Mock;

public class TestInternalBehaviors {
	
	private static List<Annotation> mocks = new ArrayList<Annotation>();
	private static List<Annotation> dataBuilders = new ArrayList<Annotation>();
	
	public static boolean isMock(Annotation ann) {
		return ann.annotationType() == Mock.class;
	}
	
	public static boolean isDataBuilder(Annotation ann) {
		return ann.annotationType() == DataBuilder.class;
	}
	
	public static void addMock(Annotation m) {
		mocks.add(m);
	}
	
	public static void addDataBuilder(Annotation m) {
		dataBuilders.add(m);
	}
	
	public static List<Annotation> mocks() {
		return mocks;
	}
	
	public static List<Annotation> dataBuilders() {
		return dataBuilders;
	}
	
	public static void clear(){
		dataBuilders = new ArrayList<Annotation>();
		mocks = new ArrayList<Annotation>();
	}
	
	public static IInternalBehaviors[] make() {
		
		IInternalBehaviors[] ib = {new MockInterpreter(), new DataBuilderInterpreter()};
		
		return ib;
	}
}
