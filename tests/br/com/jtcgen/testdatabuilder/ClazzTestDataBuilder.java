package br.com.jtcgen.testdatabuilder;

import br.com.jtcgen.annotations.TestEquals;
import br.com.jtcgen.annotations.TestFalse;
import br.com.jtcgen.annotations.TestNotNull;
import br.com.jtcgen.annotations.TestNull;
import br.com.jtcgen.annotations.TestTrue;
import br.com.jtcgen.annotations.TestVoidEquals;
import br.com.jtcgen.annotations.JTCGen;
import br.com.jtcgen.annotations.SetUp;

public class ClazzTestDataBuilder {
	
	public static Class<?> criaClasseExemplo() {
		
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

			@TestNotNull
			public ContaAplicacao obtemContaCorrente() {
				return new ContaAplicacao(this.numero, this.getAgencia(), this.saldo);
			}

			@TestNull
			@TestEquals("null")
			public ContaAplicacao obtemContaPoupanca() {
				if (String.valueOf(this.numero).length() > 10)
					return new ContaAplicacao(Integer.parseInt(new String(this.numero + "500")), this.getAgencia(), this.saldo);

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

		}
		
		return ContaAplicacao.class;

	}
	
}
