package br.com.caelum.leilao.servico;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;

public class AvaliadorTest{
	
	@Test
	public void deveEntenderLancesEmOrdemCrescente() {
		Usuario joao = new Usuario("Joao");
		Usuario jose = new Usuario("Jose");
		Usuario maria = new Usuario("Maria");
		
		Leilao leilao = new Leilao("Leilão do PS4 novo.");
		
		leilao.propoe(new Lance(joao, 300.00));
		leilao.propoe(new Lance(jose, 200.00));
		leilao.propoe(new Lance(maria, 100.00));
		
		Avaliador leiloeiro = new Avaliador();
		
		leiloeiro.avalia(leilao);
		
		double maiorLance = 300.00;
		double menorLance = 100.00;
		
		Assert.assertEquals(maiorLance, leiloeiro.getMaiorLance(), 0.00001);
		Assert.assertEquals(menorLance, leiloeiro.getMenorLance(), 0.00001);
	}
	
	@Test
	public void deveCalcularValorMedioDosLances() {
		Usuario joao = new Usuario("Joao");
		Usuario jose = new Usuario("Jose");
		Usuario maria = new Usuario("Maria");
		
		Leilao leilao = new Leilao("Leilão do PS4 novo.");
		
		leilao.propoe(new Lance(joao, 300.00));
		leilao.propoe(new Lance(jose, 200.00));
		leilao.propoe(new Lance(maria, 100.00));
		
		Avaliador leiloeiro = new Avaliador();
		
		double valorMedio = leiloeiro.obtemValorMedioDosLances(leilao);
		
		double valorMedioEsperado = 200.0;
		
		Assert.assertEquals(valorMedioEsperado, valorMedio, 0.00001);
	}
	
	@Test
	public void deveEntenderLeilaoComApenasUmLance() {
		Usuario joao = new Usuario("Joao");
		Leilao leilao = new Leilao("Leilão do PS4 novo.");
		
		leilao.propoe(new Lance(joao, 300.00));
		
		Avaliador leiloeiro = new Avaliador();
		
		leiloeiro.avalia(leilao);
		
		Assert.assertEquals(300.0, leiloeiro.getMaiorLance(), 0.00001);
		Assert.assertEquals(300.0, leiloeiro.getMenorLance(), 0.00001);
	}
	
	@Test
	public void deveEntenderLancesEmOrdemAleatoria() {
		Usuario joao = new Usuario("Joao");
		Usuario jose = new Usuario("Jose");
		Usuario maria = new Usuario("Maria");
		
		Leilao leilao = new Leilao("Leilão do PS4 novo.");
		
		leilao.propoe(new Lance(joao, 200));
		leilao.propoe(new Lance(jose, 450));
		leilao.propoe(new Lance(maria, 120));
		leilao.propoe(new Lance(joao, 700));
		leilao.propoe(new Lance(jose, 630));
		leilao.propoe(new Lance(maria, 230));
		
		Avaliador leiloeiro = new Avaliador();
		
		leiloeiro.avalia(leilao);
		
		Assert.assertEquals(700, leiloeiro.getMaiorLance(), 0.00001);
		Assert.assertEquals(120, leiloeiro.getMenorLance(), 0.00001);
	}
	
	@Test
	public void deveEntenderLancesEmOrdemDecrescente() {
		Usuario joao = new Usuario("Joao");
		Usuario jose = new Usuario("Jose");
		Usuario maria = new Usuario("Maria");
		
		Leilao leilao = new Leilao("Leilão do PS4 novo.");
		
		leilao.propoe(new Lance(joao, 400));
		leilao.propoe(new Lance(jose, 300));
		leilao.propoe(new Lance(maria, 200));
		leilao.propoe(new Lance(joao, 100));
		
		Avaliador leiloeiro = new Avaliador();
		
		leiloeiro.avalia(leilao);
		
		Assert.assertEquals(400, leiloeiro.getMaiorLance(), 0.00001);
		Assert.assertEquals(100, leiloeiro.getMenorLance(), 0.00001);
	}
	
	@Test
	public void deveEncontrarOsTresMaioresLancesDeCinco() {
		Usuario joao = new Usuario("Joao");
		Usuario jose = new Usuario("Jose");
		Usuario maria = new Usuario("Maria");
		
		Leilao leilao = new Leilao("Leilão do PS4 novo.");
		
		leilao.propoe(new Lance(joao, 200));
		leilao.propoe(new Lance(jose, 450));
		leilao.propoe(new Lance(maria, 120));
		leilao.propoe(new Lance(joao, 700));
		leilao.propoe(new Lance(jose, 630));
		leilao.propoe(new Lance(maria, 230));
		
		Avaliador leiloeiro = new Avaliador();
		
		leiloeiro.avalia(leilao);
		
		List<Lance> tresMaiores = leiloeiro.getTresMaiores();
		
		Assert.assertEquals(3, tresMaiores.size(), 0.00001);
		Assert.assertEquals(700, tresMaiores.get(0).getValor(), 0.00001);
		Assert.assertEquals(630, tresMaiores.get(1).getValor(), 0.00001);
		Assert.assertEquals(450, tresMaiores.get(2).getValor(), 0.00001);
	}
	
	@Test
	public void deveEncontrarOsDoisUnicosMaioresLances() {
		Usuario joao = new Usuario("Joao");
		Usuario jose = new Usuario("Jose");
		Usuario maria = new Usuario("Maria");
		
		Leilao leilao = new Leilao("Leilão do PS4 novo.");
		
		leilao.propoe(new Lance(jose, 630));
		leilao.propoe(new Lance(maria, 230));
		
		Avaliador leiloeiro = new Avaliador();
		
		leiloeiro.avalia(leilao);
		
		List<Lance> tresMaiores = leiloeiro.getTresMaiores();
		
		Assert.assertEquals(2, tresMaiores.size(), 0.00001);
		Assert.assertEquals(630, tresMaiores.get(0).getValor(), 0.00001);
		Assert.assertEquals(230, tresMaiores.get(1).getValor(), 0.00001);
	}
	
	@Test
	public void deveDevolverListaVaziaDeLeilaoSemLances() {
		Leilao leilao = new Leilao("Leilão do PS4 novo.");
		
		Avaliador leiloeiro = new Avaliador();
		
		leiloeiro.avalia(leilao);
		
		List<Lance> tresMaiores = leiloeiro.getTresMaiores();
		
		Assert.assertEquals(0, tresMaiores.size(), 0.00001);
	}

}
