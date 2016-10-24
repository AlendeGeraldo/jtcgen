package example.classes;

abstract class Conta {

	protected String nomeCliente;
	private String cpf;
	private int agencia;
	protected int numero;
	protected double saldo;

	public Conta(int numero, int agencia, double saldo) {
		this.numero = numero;
		this.agencia = agencia;
		this.saldo = saldo;
	}

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
