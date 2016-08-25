package br.com.caelum.leilao.servico;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;

public class PalindromoTest{
	
	@Test
	public void validaQueEhUmPalindromo() {
		
		Palindromo p = new Palindromo();
		
		boolean ehPalindromo = p.ehPalindromo("Anotaram a data da maratona");
		
		Assert.assertTrue(ehPalindromo);
	}

}
