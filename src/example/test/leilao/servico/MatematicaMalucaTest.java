package br.com.caelum.leilao.servico;

import static org.junit.Assert.*;

import org.junit.Test;

public class MatematicaMalucaTest {
	
	
	@Test
	public void deveMultiplicarPorQuatroSeForMaiorQueTrinta() {
		MatematicaMaluca matematica = new MatematicaMaluca();
		
		assertEquals(160, matematica.contaMaluca(40));
	}
	
	@Test
	public void deveMultiplicarPorTresSeForMaiorQueDezEMenorOuIgualATrinta () {
		MatematicaMaluca matematica = new MatematicaMaluca();
		
		assertEquals(33, matematica.contaMaluca(11));
	}
	
	@Test
	public void deveMultiplicarPorDoisSeForMenorOuIgualADez () {
		MatematicaMaluca matematica = new MatematicaMaluca();
		
		assertEquals(20, matematica.contaMaluca(10));
	}
}
