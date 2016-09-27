package example.classes;

import org.junit.Test; 
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList; 
import java.util.List; 

public class AvaliadorTestBak {
	
	@Test
	public void testaValorMedioComTresLances() {
		
		Lance l1 = new Lance(new Usuario("Rafael"), 200.0);
		Lance l2 = new Lance(new Usuario("Ana"), 200.0);
		Lance l3 = new Lance(new Usuario("Mariana"), 200.0);
		
		Leilao leilao = new Leilao("Casa Propria");
		
		leilao.propoe(l1);
		leilao.propoe(l2);
		leilao.propoe(l3);
		
		Avaliador avaliador = new Avaliador();
		
		double media = avaliador.obtemValorMedioDosLances(leilao);
		
		assertEquals(media, 200.0, 0.00001); 
	}
	
	@Test
	public void testaValorMedioComTresLancesMockado() {
		
		Lance lance = mock(Lance.class);
		when(lance.getValor()).thenReturn(200.0);
		Lance lance1 = mock(Lance.class);
		when(lance1.getValor()).thenReturn(300.0);
		Lance lance2 = mock(Lance.class);
		when(lance2.getValor()).thenReturn(400.0);
		
		List<Lance> list = new ArrayList<Lance>();
		list.add(lance);
		list.add(lance1);
		list.add(lance2);
		
		Leilao leilao = mock(Leilao.class);
		when(leilao.getLances()).thenReturn(list);
		
		System.out.println(leilao.getLances());
		
		Avaliador avaliador = new Avaliador();
		
		double media = avaliador.obtemValorMedioDosLances(leilao);
		
		assertEquals(media, 300.0, 0.00001); 
	}
}
