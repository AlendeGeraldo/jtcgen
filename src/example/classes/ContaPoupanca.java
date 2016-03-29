package example.classes;

import br.com.jtcgen.generator.annotations.CompareTarget;
import br.com.jtcgen.generator.annotations.JTCGen;
import br.com.jtcgen.generator.annotations.Number;

@JTCGen
public class ContaPoupanca extends Conta implements Tributavel {

	public ContaPoupanca(@Number(minLength = 5, maxLength = 5) int numero,
			@Number(minLength = 5, maxLength = 5) int agencia,
			@Number(minLength = 5, maxLength = 5, precision = 2) double saldo) {
		super(numero, agencia, saldo);
	}

	@CompareTarget(target = ContaPoupanca.class, method = "getSaldo")
	public void deposita(@Number(minLength = 3, maxLength = 20, precision = 2) double quantia) {
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
