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
	
	@Test("setup([10, 12, 100.0]).parameter([{c: 'ContaPoupanca@getSaldo()', v: 200.0}]).eq(300.0)")
	public double somaValoresDasContas(ContaPoupanca cp) {
		return this.saldo + cp.getSaldo();
	}
	
	@Test("setup([10, 12, 100.0]).parameter([{c: 'ContaPoupanca@getSaldo()', v: 200.0}, 1.1]).eq(330.0)")
	public double calculaJurosAcimaSobContas(ContaPoupanca cp, double juros) {
		return (this.saldo + cp.getSaldo()) * juros;
	}
	
	@Test("setup([10, 12, 0.0]).parameter([{c: 'ContaPoupanca@getSaldo()', v: 200.0}]).isTrue()")
	@Test("setup([10, 12, -2.2]).parameter([{c: 'ContaPoupanca@getSaldo()', v: 0.0}]).isFalse()")
	public boolean saldoEhPositivo(ContaPoupanca cp) {
		return (this.saldo + cp.getSaldo()) >= 0;
	}
	
	public double getSaldo() {
		return saldo;
	};
	
	/**
	 * Métodos de testes
	 * */
	
	@TestEquals({"a;5.5", "a"})
	@TestEquals({"p;1093.0", "p"})
	public char testeChar(char param1, double param2) {
		return param1;
	}
	
	@TestEquals({"10.5;1.0", "10.5"})
	public float testeFloat(float param1, float param2) {
		return param1;
	}
	
	@TestEquals({"5.1;2.0", "10.2"})
	public double testeDouble(double param1, float param2) {
		return param1 * param2;
	}
	
	@TestEquals({"5.1;2.0", "10.2"})
	public long testeLong(int param1, int param2) {
		return param1 * param2;
	}
	
	@TestEquals({"5.1;2.0", "10.2"})
	public int testeInt(float param1, double param2) {
		return (int) (param1 * param2);
	}
	
	@TestEquals({"v5.1;a", "v5.1a"})
	public String testeString(String param1, char param2) {
		char c[] = {param2};
		return param1 + new String(c);
	}

}
