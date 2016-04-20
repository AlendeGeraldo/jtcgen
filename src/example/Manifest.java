package example;

import br.com.jtcgen.JTCGenenerator;
import example.classes.ContaAplicacao;
import example.classes.ContaCorrente;

public class Manifest {
	public static void main(String[] args) {
		JTCGenenerator jtcGenenerator = new JTCGenenerator();

		Class<?>[] c = { ContaAplicacao.class, ContaCorrente.class };

		jtcGenenerator.generateTests(c);
	}
}
