package br.com.jtcgen.builder;

import org.junit.Test;

import br.com.jtcgen.testdatabuilder.ClazzTestDataBuilder;
import static org.junit.Assert.*;

public class EndTestGeneratorTest {

	@Test
	public void deveGerarFechamentoDeclaracaoDeClasse() {
		
		EndTestGenerator end = new EndTestGenerator(ClazzTestDataBuilder.criaClasseExemplo());
		
		assertEquals("\n" + "}", end.generate());
	}
}
