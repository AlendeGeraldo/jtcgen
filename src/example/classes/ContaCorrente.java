package example.classes;

import br.com.jtcgen.generator.annotations.CompareTarget;
import br.com.jtcgen.generator.annotations.JTCGen;
import br.com.jtcgen.generator.annotations.Number;

@JTCGen
public class ContaCorrente extends Conta implements Tributavel {

	public ContaCorrente(@Number(minLength = 5, maxLength = 5) int numero,
			@Number(minLength = 5, maxLength = 5) int agencia,
			@Number(minLength = 3, maxLength = 12, precision = 2) double saldo) {
		super(numero, agencia, saldo);
	}

	@CompareTarget(target = ContaCorrente.class, method = "getSaldo")
	public void deposita(@Number(minLength = 3, maxLength = 12, precision = 2) double quantia) {
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
