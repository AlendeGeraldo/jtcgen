package example;

import br.com.jtcgen.JTCGenenerator;
import br.com.jtcgen.TestCaseGenerable;

public class Manifest {
	public static void main(String[] args) {
		TestCaseGenerable jtcGenenerator = new JTCGenenerator();

		jtcGenenerator.generateTests(JTCGenenerator.DISABLE_BACKUP);	
	}
}
