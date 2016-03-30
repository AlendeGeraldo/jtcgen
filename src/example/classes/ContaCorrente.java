package example.classes;

import br.com.jtcgen.generator.annotations.CompareTarget;
import br.com.jtcgen.generator.annotations.JTCGen;
import br.com.jtcgen.generator.annotations.Number;

@JTCGen
public class ContaCorrente extends Conta implements Tributavel {

	public ContaCorrente(@Number({ 5, 5 }) int numero, @Number({ 5, 5 }) int agencia,
			@Number({ 3, 12, 2 }) double saldo) {
		super(numero, agencia, saldo);
	}

	@CompareTarget(target = ContaCorrente.class, method = "getSaldo")
	public void deposita(@Number({ 3, 12, 2 }) double quantia) {
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
