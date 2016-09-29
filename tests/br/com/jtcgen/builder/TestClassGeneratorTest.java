package br.com.jtcgen.builder;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.jtcgen.testdatabuilder.ClazzTestDataBuilder;


public class TestClassGeneratorTest {
	
	@Test
	public void deveCriarParteSuperiorDoTeste() {
		
		TestClassGenerator tcg = new TestClassGenerator(ClazzTestDataBuilder.criaClasseExemplo());
		
		String expected = "package test.br.com.jtcgen.testdatabuilder;" +
			"\n\n" +
			"import static org.junit.Assert.*;" +
			"\n\n" +
			"import org.junit.After;" +
			"\n" +
			"import org.junit.Before;" +
			"\n" +
			"import org.junit.Test;" +
			"\n\n" +
			"import br.com.jtcgen.testdatabuilder.ClazzTestDataBuilder$1ContaAplicacao;" +
			"\n" +
			"{{OTHER_IMPORTS}}" +
			"\n\n" +
			"public class ContaAplicacaoTest {" +
			"\n\n" +
			"\t" +
			"private ContaAplicacao instance;" +
			"\n" +
			"\n" ;
		
			System.out.println(tcg.generate());
		
		assertEquals(expected, tcg.generate());
	}
}
