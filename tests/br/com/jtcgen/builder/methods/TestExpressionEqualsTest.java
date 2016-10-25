package br.com.jtcgen.builder.methods;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import br.com.jtcgen.testdatabuilder.ClazzTestDataBuilder;


public class TestExpressionEqualsTest {
	
	private Class<?> test;
	private Method[] declaredMethods;

	@Before
	public void setUp() {
		test = ClazzTestDataBuilder.criaClasseExemplo();
		
		declaredMethods = test.getDeclaredMethods();
		
		ClazzTestDataBuilder.getLocalClasses();
	}
	
	@Test
	public void deveCriarCenarioDeTesteComSelfEMockEeq() {
		Method choose = null;
		for(Method m : declaredMethods) {
			 if("somaValoresDasContas".equals(m.getName()))
				 choose = m;
		}
		System.out.println(choose);
		assertNotNull(choose);
		
		TestExpression testExpression = new TestExpression(choose, test);
		String createMethod = testExpression.createMethod().trim();
		String templateExpected = "@Test\n" +
			"\tpublic void somaValoresDasContas() {" + "\n" +
		    "\t\tContaAplicacao contaaplicacao = new ContaAplicacao(10, 12, 100.0);"+ "\n" +
		    "\n" +
		    "\t\tContaPoupanca contapoupanca = mock(ContaPoupanca.class);" + "\n" +
		    "\t\twhen(contapoupanca.getSaldo()).thenReturn(200.0);" + "\n" +
		    "\n" +
	    	"\t\tdouble expected = contaaplicacao.somaValoresDasContas(contapoupanca);" + "\n" +
	    	"\t\tassertEquals(300.0, expected, 0.0001);" + "\n" +
	    	"\t}";
		
		//Annotation template
		//@Test("setup([10, 12, 100.0]).parameter([{c: 'ContaPoupanca@getSaldo()', v: 200.0}]).eq(300.0)")
		assertEquals(templateExpected, createMethod);
	}
	
	
	@Test
	public void deveCriarTesteComMaisDeUmParametroAbstrato() {
		Method choose = null;
		for(Method m : declaredMethods) {
			 if("somaValoresDasContasPoupancaECorrente".equals(m.getName()))
				 choose = m;
		}
		
		assertNotNull(choose);
		
		TestExpression testExpression = new TestExpression(choose, test);
		String createMethod = testExpression.createMethod().trim();
		String templateExpected = "@Test\n" +
			"\tpublic void somaValoresDasContasPoupancaECorrente() {" + "\n" +
		    "\t\tContaAplicacao contaaplicacao = new ContaAplicacao(10, 12, 100.0);"+ "\n" +
		    "\n" +
		    "\t\tContaPoupanca contapoupanca = mock(ContaPoupanca.class);" + "\n" +
		    "\t\twhen(contapoupanca.getSaldo()).thenReturn(200.0);" + "\n" +
		    "\n" +
		    "\t\tContaCorrente contacorrente = mock(ContaCorrente.class);" + "\n" +
		    "\t\twhen(contacorrente.getSaldo()).thenReturn(300.0);" + "\n" +
		    "\n" +
	    	"\t\tdouble expected = contaaplicacao.somaValoresDasContasPoupancaECorrente(contapoupanca, contacorrente);" + "\n" +
	    	"\t\tassertEquals(600.0, expected, 0.0001);" + "\n" +
	    	"\t}";
		
		//Annotation template
		//@Test("setup([10, 12, 100.0])"
		//+ ".parameter([{c: 'ContaPoupanca@getSaldo()', v: 200.0}, { c: 'ContaCorrente@getSaldo()', v: 300.0}])"
		//+ ".eq(600.0)")
		assertEquals(templateExpected, createMethod);
	}
	
	@Test
	public void deveCriarCenarioDeTesteComSelfEMockEUsandoParametrosMistos() {
		Method choose = null;
		for(Method m : declaredMethods) {
			 if("calculaJurosAcimaSobContas".equals(m.getName()))
				 choose = m;
		}
		
		assertNotNull(choose);
		
		TestExpression testExpression = new TestExpression(choose, test);
		String createMethod = testExpression.createMethod().trim();
		String templateExpected = "@Test\n" +
			"\tpublic void calculaJurosAcimaSobContas() {" + "\n" +
		    "\t\tContaAplicacao contaaplicacao = new ContaAplicacao(10, 12, 100.0);"+ "\n" +
		    "\n" +
		    "\t\tContaPoupanca contapoupanca = mock(ContaPoupanca.class);" + "\n" +
		    "\t\twhen(contapoupanca.getSaldo()).thenReturn(200.0);" + "\n" +
		    "\n" +
	    	"\t\tdouble expected = contaaplicacao.calculaJurosAcimaSobContas(contapoupanca, 1.1);" + "\n" +
	    	"\t\tassertEquals(330.0, expected, 0.0001);" + "\n" +
	    	"\t}";
		
		//Annotation template
		//@Test("setup([10, 12, 100.0]).parameter([{c: 'ContaPoupanca@getSaldo()', v: 200.0}, 1.1]).eq(330.0)")
		assertEquals(templateExpected, createMethod);
	}
}
