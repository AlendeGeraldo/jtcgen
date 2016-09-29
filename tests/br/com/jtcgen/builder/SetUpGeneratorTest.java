package br.com.jtcgen.builder;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.com.jtcgen.testdatabuilder.ClazzTestDataBuilder;
import example.classes.ContaAplicacao;

public class SetUpGeneratorTest {
	
	@Test
	public void deveGerarMetodoSetup() {
		
		SetUpGenerator setup = new SetUpGenerator(ClazzTestDataBuilder.criaClasseExemplo());
		
		String expected = "\t@Before\n" +
			"\tpublic void setUp() throws Exception {\n" +
			"\t\tthis.instance = new ContaAplicacao(1000,2200,500.0);\n" +
			"\t}\n" ;

		assertEquals(expected, setup.generate());
		
	}

}
