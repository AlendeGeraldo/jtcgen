package test.example.classes;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import example.classes.Avaliador;


public class AvaliadorTest {

	private Avaliador instance;

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		this.instance = null;
	}

	@Test
	public void obtemValorMedioDosLances() {
		assertEquals(1200.0, resultado, 0.00001);
	}
	
}