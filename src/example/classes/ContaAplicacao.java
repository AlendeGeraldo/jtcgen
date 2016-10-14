package example.classes;

import br.com.jtcgen.annotations.TestEquals;
import br.com.jtcgen.annotations.TestFalse;
import br.com.jtcgen.annotations.TestNotNull;
import br.com.jtcgen.annotations.TestNull;
import br.com.jtcgen.annotations.TestTrue;
import br.com.jtcgen.annotations.TestVoidEquals;
import br.com.jtcgen.annotations.JTCGen;
import br.com.jtcgen.annotations.SetUp;
import br.com.jtcgen.annotations.Test;

@JTCGen
public class ContaAplicacao extends Conta implements Tributavel {

	@SetUp({"1000", "2200", "500.0"})
	public ContaAplicacao(int numero, int agencia, double saldo) {
		super(numero, agencia, saldo);
	}

	@TestVoidEquals({ "1000.0", "getSaldo", "1499.5" })
	public void deposita(double quantia) {
		this.saldo += this.taxaMovimentacao(quantia);
	}

	@TestEquals({ "5000.0", "4999.5" })
	public double taxaMovimentacao(double quantia) {
		return quantia - 0.50;
	}

	@TestEquals({ "0.2", "100.0" })
	@Override
	public double calculaImpostos(double taxa) {

		return this.getSaldo() * taxa;

	}

	@TestTrue
	public boolean saldoPositivo() {
		if (this.saldo >= 0)
			return true;

		return false;
	}

	@TestFalse
	public boolean saldoNegativo() {
		if (this.saldo >= 0)
			return false;

		return true;
	}

	@TestNotNull
	public ContaCorrente obtemContaCorrente() {
		return new ContaCorrente(this.numero, this.getAgencia(), this.saldo);
	}

	@TestNull
	@TestEquals("null")
	public ContaPoupanca obtemContaPoupanca() {
		if (String.valueOf(this.numero).length() > 10)
			return new ContaPoupanca(Integer.parseInt(new String(this.numero + "500")), this.getAgencia(), this.saldo);

		return null;
	}

	@TestEquals("123.456.789-10")
	public String retornaCpfComMascara() {
		String cpf = "123.456.789-10";
		return cpf;
	}

	@TestEquals({ "Rafael", "Bem Vindo! Rafael, seu saldo e de R$ 500.0" })
	public String boasVindas(String mensagem) {
		return "Bem Vindo! " + mensagem + ", seu saldo e de R$ " + saldo;
	}
	
	//mock('Leilao@getLances()').returns(mockList('Lance@getValor()', [200.0, 300.0, 400.0]))
	@Test("setup([10, 12, 100.0]).parameter([{c: 'ContaPoupanca@getSaldo()', v: 200.0}]).eq(600.0)")
	public double somaValoresDasContas(ContaPoupanca cp) {
		return this.saldo + cp.getSaldo();
	}

}
