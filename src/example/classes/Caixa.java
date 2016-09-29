package example.classes;

import br.com.jtcgen.annotations.TestEquals;

public class Caixa {

	@TestEquals({"ContaCorrente[mock=true]", "Banco JTCGEN.\n\nBem vindo Rafael!"})
	public String telaBoasVindas(Conta conta) {
		return "Banco JTCGEN.\n\nBem vindo " + conta.getNomeCliente() + "!";
	}
}
