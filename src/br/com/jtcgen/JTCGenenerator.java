package br.com.jtcgen;

import java.util.List;

import br.com.jtcgen.annotations.JTCGen;
import br.com.jtcgen.builder.DirectoryGenerator;
import br.com.jtcgen.builder.TestGenerator;
import br.com.jtcgen.builder.TestGeneratorFactory;
import br.com.jtcgen.helpers.ImportManager;

/**
 * Façade para criação dos casos de testes
 * 
 * @author Rafael Henrique Ap. Gonçalves <rafael.goncalves19@fatec.sp.gov.br>
 * @author Estevam Herculano
 */
public class JTCGenenerator {

	/**
	 * função sobrecarregada para aceitar var-args de objetos
	 * 
	 * @param Objects...
	 *            objects
	 */
	public void generateTests(Object... objects) {
		int i = 0;
		Class<?>[] classes = new Class<?>[objects.length];
		for (Object obj : objects)
			classes[i++] = obj.getClass();

		generate(classes);
	}

	/**
	 * direciona a reflexão de todas as classes que serão gerados os casos de
	 * testes
	 * 
	 * @param classe
	 */
	public void generateTests(Class<?>... classes) {
		generate(classes);
	}

	private void generate(Class<?>... classes) {
		for (Class<?> classe : classes) {
			if (classe.isAnnotationPresent(JTCGen.class)) {
				StringBuffer buffer = new StringBuffer();

				List<TestGenerator> gens = TestGeneratorFactory.createGenerators(classe);
				for (TestGenerator gen : gens) {
					buffer.append(gen.generate());
				}

				DirectoryGenerator dir = TestGeneratorFactory.createDirectoryGenerator(classe);
				dir.createTest(buffer.toString().replaceFirst("\\{\\{OTHER_IMPORTS\\}\\}", ImportManager.getImports()));
			}
		}
	}
}
