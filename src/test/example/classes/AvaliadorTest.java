package test.example.classes;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import example.classes.Avaliador;
import example.classes.Leilao;
import java.util.List;
import java.util.ArrayList;
import example.classes.Lance;
import static org.mockito.Mockito.*;


public class AvaliadorTest {

	private Avaliador instance;

	@Before
	public void setUp() throws Exception {
		
			this.instance = new Avaliador();
	}

	@After
	public void tearDown() throws Exception {
		this.instance = null;
	}

	@Test
	public void obtemValorMedioDosLances() {
		Lance lance = mock( Lance.class );
		when(lance.getValor()).thenReturn(200.0);

		Lance lance1 = mock( Lance.class );
		when(lance1.getValor()).thenReturn(300.0);

		Lance lance2 = mock( Lance.class );
		when(lance2.getValor()).thenReturn(400.0);


		List<Lance> list = new ArrayList<Lance>();

		list.add(lance);
		list.add(lance1);
		list.add(lance2);

		Leilao leilao = mock( Leilao.class );
		when(leilao.getLances()).thenReturn(list);


		
		
		double resultado = this.instance.obtemValorMedioDosLances(leilao);
		assertEquals(300.0, resultado, 0.00001);
	}
	
}