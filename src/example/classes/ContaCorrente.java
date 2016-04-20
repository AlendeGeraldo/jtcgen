package example.classes;

import br.com.jtcgen.generator.annotations.JTCGen;

@JTCGen
public class ContaCorrente extends Conta implements Tributavel {

	public ContaCorrente(int numero, int agencia, double saldo) {
		super(numero, agencia, saldo);
	}

	public void deposita(double quantia) {
		this.saldo += this.taxaMovimentacao(quantia);
	}

	private double taxaMovimentacao(double quantia) {
		return quantia - 0.50;
	}

	@Override
	public double calculaImpostos(double taxa) {

		return 0;
	}

}
