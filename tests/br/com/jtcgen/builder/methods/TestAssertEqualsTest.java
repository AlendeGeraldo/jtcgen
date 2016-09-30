package br.com.jtcgen.builder.methods;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.Test;

import br.com.jtcgen.testdatabuilder.ClazzTestDataBuilder;

public class TestAssertEqualsTest {
	
	/*@Test
	public void deveCriarCorpoParaMetodoComParametros() throws NoSuchMethodException, SecurityException {
		
		Class<?> ex = ClazzTestDataBuilder.criaClasseExemplo();
		
		Method method = ex.getMethod("taxaMovimentacao", double.class);
		
		TestArrayEquals testArrayEquals = new TestArrayEquals(method, ex);
		
		String createMethod = testArrayEquals.createMethod();
		
		System.out.println(createMethod);
		
		String compare = "\t@Test" +
			"\tpublic void taxaMovimentacao() {\n\n" +
			"\t\tdouble resultado = this.instance.taxaMovimentacao(5000.0);" +
			"\t\tdouble expected = 9;" +
			"\t\tassertArrayEquals(expected , resultado, 0.00001);" +
			"\t}";
		
		assertEquals(compare, createMethod);
		
	}*/
}
