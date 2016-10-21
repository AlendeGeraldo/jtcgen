package br.com.jtcgen.builder.methods;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import br.com.jtcgen.helpers.ImportManager;
import br.com.jtcgen.testdatabuilder.ClazzLoadDataBuilder;
import br.com.jtcgen.testdatabuilder.ClazzTestDataBuilder;

public class TestExpressionOtherAssertsTest {
	
	private Class<?> test;
	private Method[] declaredMethods;

	@Before
	public void setUp() {
		test = ClazzTestDataBuilder.criaClasseExemplo();
		
		declaredMethods = test.getDeclaredMethods();
		
		ClazzLoadDataBuilder.getLocalClasses();
	}
	
	@Test
	public void deveCriarCenarioRetonandoTrueOuFalse() {
		Method choose = null;
		for(Method m : declaredMethods) {
			 if("saldoEhPositivo".equals(m.getName()))
				 choose = m;
			 
		}

		assertNotNull(choose);
		
		TestExpression testExpression = new TestExpression(choose, test);
		String createMethod = testExpression.createMethod().trim();
		String templateExpected = "@Test\n" +
			"\tpublic void saldoEhPositivo() {" + "\n" +
		    "\t\tContaAplicacao contaaplicacao = new ContaAplicacao(10, 12, 0.0);"+ "\n" +
		    "\n" +
		    "\t\tContaPoupanca contapoupanca = mock(ContaPoupanca.class);" + "\n" +
		    "\t\twhen(contapoupanca.getSaldo()).thenReturn(200.0);" + "\n" +
		    "\n" +
	    	"\t\tboolean expected = contaaplicacao.saldoEhPositivo(contapoupanca);" + "\n" +
	    	"\t\tassertTrue(expected);" + "\n" +
	    	"\n" +
	    	"\t\tContaAplicacao contaaplicacao1 = new ContaAplicacao(10, 12, -2.2);"+ "\n" +
		    "\n" +
		    "\t\tContaPoupanca contapoupanca1 = mock(ContaPoupanca.class);" + "\n" +
		    "\t\twhen(contapoupanca1.getSaldo()).thenReturn(0.0);" + "\n" +
		    "\n" +
	    	"\t\tboolean expected1 = contaaplicacao1.saldoEhPositivo(contapoupanca1);" + "\n" +
	    	"\t\tassertFalse(expected1);" + "\n" +
	    	"\t}";
		
		/*System.out.println(createMethod);
		System.out.println(templateExpected);*/
		
		//Annotation template
		/*@Test("setup([10, 12, 0.0])"
				+ ".parameter([{c: 'ContaPoupanca@getSaldo()', v: 200.0}])"
				+ ".isTrue()")
		@Test("setup([10, 12, -2.2])"
				+ ".parameter([{c: 'ContaPoupanca@getSaldo()', v: 0.0}])"
				+ ".isFalse()")*/
		assertEquals(templateExpected, createMethod);
	}
	
	@Test
	public void deveCriarCenarioRetonandoNotNull() {
		Method choose = null;
		for(Method m : declaredMethods) {
			 if("obtemContaCorrente".equals(m.getName()))
				 choose = m;
			 
		}

		assertNotNull(choose);
		
		TestExpression testExpression = new TestExpression(choose, test);
		String createMethod = testExpression.createMethod().trim();
		String templateExpected = "@Test\n" +
			"\tpublic void obtemContaCorrente() {" + "\n" +
		    "\t\tContaAplicacao contaaplicacao = new ContaAplicacao(10, 12, 0.0);"+ "\n" +
		    "\n" +
		    "\t\tContaCorrente expected = contaaplicacao.obtemContaCorrente();" + "\n" +
	    	"\t\tassertNotNull(expected);" + "\n" +
	    	"\t}";
		
		/*System.out.println(createMethod);
		System.out.println(templateExpected);*/
		
		//Annotation template
		//@Test("setup([10, 12, 0.0]).isNotNull()")
		assertEquals(templateExpected, createMethod);
	}
	
	@Test
	public void deveCriarCenarioRetonandoNull() {
		Method choose = null;
		for(Method m : declaredMethods) {
			 if("obtemContaPoupanca".equals(m.getName()))
				 choose = m;
			 
		}

		assertNotNull(choose);
		
		TestExpression testExpression = new TestExpression(choose, test);
		String createMethod = testExpression.createMethod().trim();
		String templateExpected = "@Test\n" +
			"\tpublic void obtemContaPoupanca() {" + "\n" +
		    "\t\tContaAplicacao contaaplicacao = new ContaAplicacao(5, 5, 0.0);"+ "\n" +
		    "\n" +
		    "\t\tContaPoupanca expected = contaaplicacao.obtemContaPoupanca();" + "\n" +
	    	"\t\tassertNull(expected);" + "\n" +
	    	"\t}";
		
		System.out.println(createMethod);
		System.out.println(templateExpected);
		
		//Annotation template
		//@Test("setup([10, 12, 0.0]).isNotNull()")
		assertEquals(templateExpected, createMethod);
	}
}
