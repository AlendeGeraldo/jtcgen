package example.classes;

import br.com.jtcgen.generator.annotations.CompareTarget;
import br.com.jtcgen.generator.annotations.Decimal;
import br.com.jtcgen.generator.annotations.GenerateTest;
import br.com.jtcgen.generator.annotations.JTCGen;
import br.com.jtcgen.generator.annotations.Money;
import br.com.jtcgen.generator.annotations.Number;
import br.com.jtcgen.generator.annotations.SetUp;

@JTCGen
public class ContaAplicacao extends Conta implements Tributavel {

	@SetUp({ Number.class, Number.class, Decimal.class })
	public ContaAplicacao(int numero, int agencia, double saldo) {
		super(numero, agencia, saldo);
	}

	@GenerateTest(params = { Decimal.class }, compareMethod = "getSaldo")
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
