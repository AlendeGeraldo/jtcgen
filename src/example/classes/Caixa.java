package example.classes;

import br.com.jtcgen.annotations.TestEquals;
import br.com.jtcgen.annotations.Param;
import br.com.jtcgen.annotations.Expected;

public class Caixa {

	@TestEquals(param="ContaCorrente[mock=true]", expected="Banco JTCGEN.\n\nBem vindo Rafael!")
	public String telaBoasVindas(Conta conta) {
		return "Banco JTCGEN.\n\nBem vindo " + conta.getNomeCliente() + "!";
	}
}
