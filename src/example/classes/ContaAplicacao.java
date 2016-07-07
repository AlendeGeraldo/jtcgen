package example.classes;

import br.com.jtcgen.annotations.Expected;
import br.com.jtcgen.annotations.GenerateTestEquals;
import br.com.jtcgen.annotations.GenerateTestFalse;
import br.com.jtcgen.annotations.GenerateTestNotNull;
import br.com.jtcgen.annotations.GenerateTestNull;
import br.com.jtcgen.annotations.GenerateTestTrue;
import br.com.jtcgen.annotations.GenerateTestVoidEquals;
import br.com.jtcgen.annotations.JTCGen;
import br.com.jtcgen.annotations.MethodCompare;
import br.com.jtcgen.annotations.Param;
import br.com.jtcgen.annotations.SetUp;

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

	@GenerateTestTrue(@Param)
	public boolean saldoPositivo() {
		if (this.saldo >= 0)
			return true;

		return false;
	}

	@GenerateTestFalse(@Param)
	public boolean saldoNegativo() {
		if (this.saldo >= 0)
			return false;

		return true;
	}

	@GenerateTestNotNull(@Param)
	public ContaCorrente obtemContaCorrente() {
		return new ContaCorrente(this.numero, this.getAgencia(), this.saldo);
	}

	@GenerateTestNull(@Param)
	@GenerateTestEquals(param = @Param(""), expected = @Expected("null"))
	public ContaPoupanca obtemContaPoupanca() {
		if (String.valueOf(this.numero).length() > 10)
			return new ContaPoupanca(Integer.parseInt(new String(this.numero + "500")), this.getAgencia(), this.saldo);

		return null;
	}

	@GenerateTestEquals(param = @Param(), expected = @Expected("123.456.789-10"))
	public String retornaCpfComMascara() {
		String cpf = "123.456.789-10";
		return cpf;
	}

	@GenerateTestEquals(param = @Param("Rafael"), expected = @Expected("Bem Vindo! Rafael, seu saldo e de R$ 500.0"))
	public String boasVindas(String mensagem) {
		return "Bem Vindo! " + mensagem + ", seu saldo e de R$ " + saldo;
	}

}
