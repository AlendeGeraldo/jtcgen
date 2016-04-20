package test.example.classes;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import example.classes.ContaAplicacao;

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
	 public void calculaImpostos() {
		double resultado = this.instance.calculaImpostos(0.2);
		assertEquals(100.0, resultado, 0.00000000000001);
	}

	@Test
	 public void deposita() {
		this.instance.deposita(1000.0);
		assertEquals(1499.5, this.instance.getSaldo(), 0.00000000000001);
	}

	@Test
	 public void taxaMovimentacao() {
		double resultado = this.instance.taxaMovimentacao(5000.0);
		assertEquals(4999.5, resultado, 0.00000000000001);
	}
}