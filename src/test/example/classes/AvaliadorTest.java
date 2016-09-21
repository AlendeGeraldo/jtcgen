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
	public void obtemValorMedioDosLances() {			Lance lance = mock( Lance.class );
			when(lance.getValor()).thenReturns(200);

			Lance lance1 = mock( Lance.class );
			when(lance1.getValor()).thenReturns(300);

			Lance lance2 = mock( Lance.class );
			when(lance2.getValor()).thenReturns(400);


			List<Lance> list = new ArrayList<Lance>();

			list.add(lance);
			list.add(lance1);
			list.add(lance2);

			Leilao leilao = mock( Leilao.class );
			when(leilao.getLances()).thenReturns(list);


		assertEquals(leilao, resultado, 0.00001);
	}
	
}