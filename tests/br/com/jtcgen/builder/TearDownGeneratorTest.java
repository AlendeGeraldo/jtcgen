package br.com.jtcgen.builder;

import static org.junit.Assert.*;
import org.junit.Test;

import br.com.jtcgen.testdatabuilder.ClazzTestDataBuilder;

public class TearDownGeneratorTest {
	
	@Test
	public void deveCriarMetodoTearDown() {
		TearDownGenerator tear = new TearDownGenerator(ClazzTestDataBuilder.criaClasseExemplo());
		
		String expected = "\n\t" + "@After" +
		 	"\n\t" +
			"public void tearDown() throws Exception {" +
			"\n\t\t" +
			"this.instance = null;" +
			"\n\t" +
			"}" +
			"\n";
		
		assertEquals(expected, tear.generate());
		
	}
}
