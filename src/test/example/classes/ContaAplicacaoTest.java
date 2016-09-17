package test.example.classes;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import example.classes.ContaAplicacao;
import example.classes.ContaPoupanca;
import example.classes.ContaCorrente;


public class ContaAplicacaoTest {

	private ContaAplicacao instance;

	@Before
	public void setUp() throws Exception {
		this.instance = new ContaAplicacao(1000, 2200, 500.0);
	}

	@After
	public void tearDown() throws Exception {
		this.instance = null;
	}

	@Test
	public void obtemContaCorrente() {
		
		
		ContaCorrente resultado = this.instance.obtemContaCorrente();
		assertNotNull(resultado);
	}
	
	@Test
	public void obtemContaPoupanca() {
		
		
		ContaPoupanca resultado = this.instance.obtemContaPoupanca();
		assertNull(resultado);
	}
	
	@Test
	public void obtemContaPoupancaEquals() {
		assertEquals(null, resultado);
	}
	
	@Test
	public void retornaCpfComMascara() {
		assertEquals("123.456.789-10", resultado);
	}
	
	@Test
	public void deposita() {
		
		
		this.instance.deposita(1000.0);
		assertEquals(1499.5, this.instance.getSaldo(), 0.00000000001);
	}
	
	@Test
	public void taxaMovimentacao() {
		assertEquals(4999.5, resultado, 0.00001);
	}
	
	@Test
	public void calculaImpostos() {
		assertEquals(100.0, resultado, 0.00001);
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
		assertEquals("Bem Vindo! Rafael, seu saldo e de R$ 500.0", resultado);
	}
	
}