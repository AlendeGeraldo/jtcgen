package br.com.jtcgen.testdatabuilder;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.jtcgen.testdatabuilder.ContaAplicacao;
import java.lang.String;
import static org.mockito.Mockito.*;


public class ContaAplicacaoTest {

	private ContaAplicacao instance;

	@Before
	public void setUp() throws Exception {
		this.instance = new ContaAplicacao(1000,2200,500.0);
	}

	@After
	public void tearDown() throws Exception {
		this.instance = null;
	}

	@Test
	public void somaValoresDasContasPoupancaECorrente() {
		ContaAplicacao contaaplicacao = new ContaAplicacao(10, 12, 100.0);

		ContaPoupanca contapoupanca = mock(ContaPoupanca.class);
		when(contapoupanca.getSaldo()).thenReturn(200.0);

		ContaCorrente contacorrente = mock(ContaCorrente.class);
		when(contacorrente.getSaldo()).thenReturn(300.0);

		double expected = contaaplicacao.somaValoresDasContasPoupancaECorrente(contapoupanca, contacorrente);
		assertEquals(600.0, expected, 0.0001);
	}
	
	@Test
	public void retornaCpfComMascara() {
		
		
		String resultado = this.instance.retornaCpfComMascara();
		assertEquals("123.456.789-10", resultado);
	}
	
	@Test
	public void somaValoresDasContas() {
		ContaAplicacao contaaplicacao = new ContaAplicacao(10, 12, 100.0);

		ContaPoupanca contapoupanca = mock(ContaPoupanca.class);
		when(contapoupanca.getSaldo()).thenReturn(200.0);

		double expected = contaaplicacao.somaValoresDasContas(contapoupanca);
		assertEquals(300.0, expected, 0.0001);
	}
	
	@Test
	public void calculaJurosAcimaSobContas() {
		ContaAplicacao contaaplicacao = new ContaAplicacao(10, 12, 100.0);

		ContaPoupanca contapoupanca = mock(ContaPoupanca.class);
		when(contapoupanca.getSaldo()).thenReturn(200.0);

		double expected = contaaplicacao.calculaJurosAcimaSobContas(contapoupanca, 1.1);
		assertEquals(330.0, expected, 0.0001);
	}
	
	@Test
	public void obtemContaCorrente() {
		ContaAplicacao contaaplicacao = new ContaAplicacao(10, 12, 0.0);

		ContaCorrente expected = contaaplicacao.obtemContaCorrente();
		assertNotNull(expected);
	}
	
	@Test
	public void obtemContaPoupanca() {
		ContaAplicacao contaaplicacao = new ContaAplicacao(5, 5, 0.0);

		ContaPoupanca expected = contaaplicacao.obtemContaPoupanca();
		assertNull(expected);
	}
	
	@Test
	public void calculaImpostos() {
		
		
		double resultado = this.instance.calculaImpostos(0.2);
		assertEquals(100.0, resultado, 0.00001);
	}
	
	@Test
	public void deposita() {
		
		
		this.instance.deposita(1000.0);
		assertEquals(1499.5, this.instance.getSaldo(), 0.00001);
	}
	
	@Test
	public void taxaMovimentacao() {
		
		
		double resultado = this.instance.taxaMovimentacao(5000.0);
		assertEquals(4999.5, resultado, 0.00001);
	}
	
	@Test
	public void saldoPositivo() {
		
		
		boolean resultado = this.instance.saldoPositivo();
		assertTrue(resultado);
	}
	
	@Test
	public void saldoNegativo() {
		
		
		boolean resultado = this.instance.saldoNegativo();
		assertFalse(resultado);
	}
	
	@Test
	public void boasVindas() {
		
		
		String resultado = this.instance.boasVindas("Rafael");
		assertEquals("Bem Vindo! Rafael, seu saldo e de R$ 500.0", resultado);
	}
	
}