package example.classes;

import br.com.jtcgen.annotations.JTCGen;

@JTCGen
public class ContaPoupanca extends Conta implements Tributavel {

	public ContaPoupanca(int numero, int agencia, double saldo) {
		super(numero, agencia, saldo);
	}

	public void deposita(double quantia) {
		this.saldo += this.taxaMovimentacao(quantia);
	}

	private double taxaMovimentacao(double quantia) {
		return quantia - 0.10;
	}

	@Override
	public double calculaImpostos(double taxa) {
		return this.saldo -= this.saldo * taxa;
	}

}
