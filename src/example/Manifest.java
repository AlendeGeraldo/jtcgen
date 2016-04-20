package example;

import br.com.jtcgen.JTCGenenerator;
import example.classes.ContaAplicacao;

public class Manifest {
	public static void main(String[] args) {
		JTCGenenerator jtcGenenerator = new JTCGenenerator();

		jtcGenenerator.generateTests(new ContaAplicacao(1, 1, 1));
	}
}
