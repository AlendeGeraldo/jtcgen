package example.classes;

import br.com.jtcgen.annotations.GenerateTestEquals;
import br.com.jtcgen.annotations.Param;
import br.com.jtcgen.annotations.Expected;

public class Caixa {

	@GenerateTestEquals(param="ContaCorrente[mock=true]", expected="Banco JTCGEN.\n\nBem vindo Rafael!")
	public String telaBoasVindas(Conta conta) {
		return "Banco JTCGEN.\n\nBem vindo " + conta.getNomeCliente() + "!";
	}
}
