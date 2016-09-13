package example.classes;

import br.com.jtcgen.annotations.TestEquals;
import br.com.jtcgen.annotations.TestFalse;
import br.com.jtcgen.annotations.TestNotNull;
import br.com.jtcgen.annotations.TestNull;
import br.com.jtcgen.annotations.TestTrue;
import br.com.jtcgen.annotations.TestVoidEquals;
import br.com.jtcgen.annotations.JTCGen;
import br.com.jtcgen.annotations.SetUp;

@JTCGen
public class ContaAplicacao extends Conta implements Tributavel {

	@SetUp("1000; 2200; 500.0")
	public ContaAplicacao(int numero, int agencia, double saldo) {
		super(numero, agencia, saldo);
	}

	@TestVoidEquals(param="1000.0", compare="getSaldo", expected="1499.5")
	public void deposita(double quantia) {
		this.saldo += this.taxaMovimentacao(quantia);
	}

	@TestEquals(param="5000.0", expected="4999.5")
	public double taxaMovimentacao(double quantia) {
		return quantia - 0.50;
	}

	@TestEquals(param="0.2", expected="100.0")
	@Override
	public double calculaImpostos(double taxa) {

		return this.getSaldo() * taxa;

	}

	@TestTrue()
	public boolean saldoPositivo() {
		if (this.saldo >= 0)
			return true;

		return false;
	}

	@TestFalse()
	public boolean saldoNegativo() {
		if (this.saldo >= 0)
			return false;

		return true;
	}

	@TestNotNull()
	public ContaCorrente obtemContaCorrente() {
		return new ContaCorrente(this.numero, this.getAgencia(), this.saldo);
	}

	@TestNull()
	@TestEquals(param="", expected="null")
	public ContaPoupanca obtemContaPoupanca() {
		if (String.valueOf(this.numero).length() > 10)
			return new ContaPoupanca(Integer.parseInt(new String(this.numero + "500")), this.getAgencia(), this.saldo);

		return null;
	}

	@TestEquals(param="",expected="123.456.789-10")
	public String retornaCpfComMascara() {
		String cpf = "123.456.789-10";
		return cpf;
	}

	@TestEquals(param="Rafael",expected="Bem Vindo! Rafael, seu saldo e de R$ 500.0")
	public String boasVindas(String mensagem) {
		return "Bem Vindo! " + mensagem + ", seu saldo e de R$ " + saldo;
	}

}
