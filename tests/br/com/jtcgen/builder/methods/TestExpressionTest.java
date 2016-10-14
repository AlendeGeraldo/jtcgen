package br.com.jtcgen.builder.methods;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.Test;

import br.com.jtcgen.testdatabuilder.ClazzTestDataBuilder;


public class TestExpressionTest {
	
	@Test
	public void deveCriarCenarioDeTesteComSelfEMock() {
		
		Class<?> test = ClazzTestDataBuilder.criaClasseExemplo();
		
		Method[] declaredMethods = test.getDeclaredMethods();
		
		Method choose = null;
		for(Method m : declaredMethods) {
			 if("somaValoresDasContas".equals(m.getName()))
				 choose = m;
		}
		
		assertNotNull(choose);
		
		TestExpression testExpression = new TestExpression(choose, test);
		
		String createMethod = testExpression.createMethod();
		
		System.out.println(createMethod.trim());
		
		String templateExpected = "@Test\n" +
			"\tpublic void somaValoresDasContas() {" + "\n" +
		    "\t\tContaAplicacao contaaplicacao = new ContaAplicacao(10, 12, 100.0);"+ "\n" +
		    "\n" +
		    "\t\tContaPoupanca contapoupanca = mock(ContaPoupanca.class);" + "\n" +
		    "\t\twhen(contapoupanca.getSaldo()).thenReturn(200.0);" + "\n" +
		    "\n" +
	    	"\t\tdouble expected =  contaaplicacao.somaValoresDasContas(contapoupanca);" + "\n" +
	    	"\t\tassertEquals(600.0, expected, 0.0001);" + "\n" +
	    	"\t}";
		
		//@Test("self('saldo', 400.0).parameter( 0 ,'ContaPoupanca@getSaldo()', 200.0).eq(600.0)")
		assertEquals(templateExpected, createMethod.trim());
	}
}
