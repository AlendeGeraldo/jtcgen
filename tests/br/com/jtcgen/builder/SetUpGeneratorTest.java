package br.com.jtcgen.builder;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.jtcgen.testdatabuilder.ClazzTestDataBuilder;

public class SetUpGeneratorTest {
	private Class<?> clazz;

	@Before
	public void setUp() {
		clazz = ClazzTestDataBuilder.criaClasseExemplo();
	}
	
	@Test
	public void deveGerarMetodoSetup() {
		SetUpGenerator setup = new SetUpGenerator(clazz);
		
		String expected = "\t@Before\n" +
			"\tpublic void setUp() throws Exception {\n" +
			"\t\tthis.instance = new ContaAplicacao(1000,2200,500.0);\n" +
			"\t}\n" ;
		
		assertEquals(expected, setup.generate());
		
	}
	
	@After
	public void tearDown() {
		clazz = null;
	}
}
