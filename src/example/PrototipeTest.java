package example;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import example.classes.ContaPoupanca;

public class PrototipeTest {

	private ContaPoupanca contaPoupanca;

	@Before
	public void setUp() throws Exception {
		contaPoupanca = new ContaPoupanca(1010, 100, 50000.0);
	}

	@After
	public void tearDown() throws Exception {
		contaPoupanca = null;
	}

	@Test
	public void verificaDepoisito() {
		contaPoupanca.deposita(5000);
		assertEquals(contaPoupanca.getSaldo(), 54999.9, 0.0000000001);
	}

}
