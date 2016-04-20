package example.classes;

import br.com.jtcgen.generator.annotations.Expected;
import br.com.jtcgen.generator.annotations.GenerateTestEquals;
import br.com.jtcgen.generator.annotations.GenerateTestVoidEquals;
import br.com.jtcgen.generator.annotations.JTCGen;
import br.com.jtcgen.generator.annotations.MethodCompare;
import br.com.jtcgen.generator.annotations.Param;
import br.com.jtcgen.generator.annotations.Return;
import br.com.jtcgen.generator.annotations.SetUp;

@JTCGen
public class ContaAplicacao extends Conta implements Tributavel {

	@SetUp(@Param("1000; 2200; 500.0"))
	public ContaAplicacao(int numero, int agencia, double saldo) {
		super(numero, agencia, saldo);
	}

	@GenerateTestVoidEquals(param = @Param("1000.0"), compare = @MethodCompare("getSaldo"), expected = @Expected("1499.5"))
	public void deposita(double quantia) {
		this.saldo += this.taxaMovimentacao(quantia);
	}

	@GenerateTestEquals(param = @Param("5000.0"), expected = @Expected("4999.5"))
	public double taxaMovimentacao(double quantia) {
		return quantia - 0.50;
	}

	@GenerateTestEquals(param = @Param("0.2"), expected = @Expected("100.0"))
	@Override
	public double calculaImpostos(double taxa) {

		return this.getSaldo() * taxa;

	}

}
