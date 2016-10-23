package br.com.jtcgen.testdatabuilder;

import br.com.jtcgen.annotations.TestEquals;
import br.com.jtcgen.annotations.TestFalse;
import br.com.jtcgen.annotations.TestTrue;
import br.com.jtcgen.annotations.TestVoidEquals;
import br.com.jtcgen.helpers.ImportManager;
import br.com.jtcgen.annotations.JTCGen;
import br.com.jtcgen.annotations.SetUp;
import br.com.jtcgen.annotations.Test;

interface Tributavel {

	public double calculaImpostos(double taxa);

}

abstract class Conta {
	protected String nomeCliente;
	private String cpf;
	private int agencia;
	protected int numero;
	protected double saldo;
	
	@SetUp({"1000", "2200", "500.0"})
	public Conta(int numero, int agencia, double saldo) {
		this.numero = numero;
		this.agencia = agencia;
		this.saldo = saldo;
	}
	
	public abstract double calculaImpostos(double taxa);

	public int getAgencia() {
		return agencia;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCpf() {
		return cpf;
	}

	public int getNumero() {
		return numero;
	}

	public double getSaldo() {
		return this.saldo;
	}

	public void deposita(double quantia) {
		this.saldo += quantia;
	}

	public void saca(double quantia) {
		if (quantia > this.saldo)
			throw new RuntimeException("Valor indisponível para saque.");
		this.saldo -= quantia;
	}

	@Override
	public String toString() {

		return "Conta: " + this.numero + ", Saldo: R$ " + this.saldo;
	}
}

@JTCGen
class ContaCorrente extends Conta implements Tributavel {

	public ContaCorrente(int numero, int agencia, double saldo) {
		super(numero, agencia, saldo);
	}

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
	
	public double getSaldo() {
		return this.saldo;
	}
}


@JTCGen class ContaAplicacao extends Conta {

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
	
	@Test("setup([10, 12, 0.0]).isNotNull()")
	public ContaCorrente obtemContaCorrente() {
		return new ContaCorrente(this.numero, this.getAgencia(), this.saldo);
	}

	@Test("setup([5, 5, 0.0]).isNull()")
	public ContaPoupanca obtemContaPoupanca() {
		if (String.valueOf(this.numero).length() > 10)
			return new ContaPoupanca(Integer.parseInt(new String(this.numero + "500")), this.getAgencia(), this.saldo);

		return null;
	}
	
	//mock('Leilao@getLances()').returns(mockList('Lance@getValor()', [200.0, 300.0, 400.0]))
	@Test("setup([10, 12, 100.0]).parameter([{c: 'ContaPoupanca@getSaldo()', v: 200.0}]).eq(300.0)")
	public double somaValoresDasContas(ContaPoupanca cp) {
		return this.saldo + cp.getSaldo();
	}
	
	@Test("setup([10, 12, 100.0])"
	+ ".parameter([{c: 'ContaPoupanca@getSaldo()', v: 200.0}, { c: 'ContaCorrente@getSaldo()', v: 300.0}])"
	+ ".eq(600.0)")
	public double somaValoresDasContasPoupancaECorrente(ContaPoupanca cp, ContaCorrente cc) {
		return this.saldo + cp.getSaldo() + cc.getSaldo();
	}
	
	@Test("setup([10, 12, 100.0])"
			+ ".parameter([{c: 'ContaPoupanca@getSaldo()', v: 200.0}, 1.1])"
			+ ".eq(330.0)")
	public double calculaJurosAcimaSobContas(ContaPoupanca cp, double juros) {
		return (this.saldo + cp.getSaldo()) * juros;
	}
	
	@Test("setup([10, 12, 0.0])"
			+ ".parameter([{c: 'ContaPoupanca@getSaldo()', v: 200.0}])"
			+ ".isTrue()")
	@Test("setup([10, 12, -2.2])"
			+ ".parameter([{c: 'ContaPoupanca@getSaldo()', v: 0.0}])"
			+ ".isFalse()")
	public boolean saldoEhPositivo(ContaPoupanca cp) {
		return (this.saldo + cp.getSaldo()) >= 0;
	}
	
	public double getSaldo() {
		return saldo;
	};

	@TestEquals("123.456.789-10")
	public String retornaCpfComMascara() {
		String cpf = "123.456.789-10";
		return cpf;
	}

	@TestEquals({ "Rafael", "Bem Vindo! Rafael, seu saldo e de R$ 500.0" })
	public String boasVindas(String mensagem) {
		return "Bem Vindo! " + mensagem + ", seu saldo e de R$ " + saldo;
	}
	
	
}

@JTCGen class ContaPoupanca extends Conta implements Tributavel {

	public ContaPoupanca(int numero, int agencia, double saldo) {
		super(numero, agencia, saldo);
	}

	public void deposita(double quantia) {
		this.saldo += this.taxaMovimentacao(quantia);
	}

	private double taxaMovimentacao(double quantia) {
		return quantia - 0.10;
	}
	
	public String mostraSaldoComMensagemPersonalizada(String mensagem){
		return "Bom dia! "+mensagem+" Saldo: "+ saldo;
	}
	
	public double getSaldo() {
		return this.saldo;
	}

	@Override
	public double calculaImpostos(double taxa) {
		return this.saldo -= this.saldo * taxa;
	}
}

public class ClazzTestDataBuilder {
	
	public static Class<?> criaClasseExemplo() {
		return ContaAplicacao.class;
	}
	
	public static void getLocalClasses() {
		Class<?>[] arrClasses = {ContaCorrente.class, ContaPoupanca.class, ContaAplicacao.class};
		
		ImportManager.addMapedReflections(arrClasses);
	}
	
}
