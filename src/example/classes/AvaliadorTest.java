package example.classes;

import static org.junit.Assert.*;
import org.junit.Test;


public class AvaliadorTest {
	
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
}
