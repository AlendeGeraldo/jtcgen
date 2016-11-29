package br.com.jtcgen;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.jtcgen.annotations.JTCGen;
import br.com.jtcgen.builder.DirectoryGenerator;
import br.com.jtcgen.builder.TestGenerator;
import br.com.jtcgen.builder.TestGeneratorFactory;
import br.com.jtcgen.exceptions.JTCGenException;
import br.com.jtcgen.helpers.ImportManager;
import br.com.jtcgen.helpers.ListClasses;

/**
 * Facade para cricao dos casos de testes
 * 
 * @author Rafael Henrique Ap. Goncalves <rafael.goncalves19@fatec.sp.gov.br>
 * @author Estevam Herculano
 */
public class JTCGenenerator implements TestCaseGenerable {
	
	public static final boolean ENABLE_BACKUP = true;
	public static final boolean DISABLE_BACKUP = false;

	/**
	 * funcao sobrecarregada para aceitar var-args de objetos
	 * 
	 * @param Objects...
	 *            objects
	 */
	
	public void generateTests(Object... objects) {
		generateTests(ENABLE_BACKUP, objects);
		
	}
	public void generateTests(boolean makeABackup, Object... objects) {
		int i = 0;
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (Object obj : objects)
			classes.add(obj.getClass());

		generate(makeABackup, classes);
	}

	/**
	 * direciona a reflexao de todas as classes que serao gerados os casos de
	 * testes
	 * 
	 * @param classe
	 */
	
	public void generateTests(String sourceDir, Class<?>... classes) {
		generateTests(ENABLE_BACKUP, classes);
	}
	
	/**
	 * direciona a reflexao de todas as classes que serao gerados os casos de
	 * testes
	 * 
	 * @param classe
	 */
	public void generateTests(boolean makeABackup, Class<?>... classes) {
		List<Class<?>> list = new ArrayList<Class<?>>();
		for(Class<?> clazz : classes)
			list.add(clazz);
		generate(makeABackup, list);
	}
	
	public void generateTests() {
		generateTests(ENABLE_BACKUP);
	}

	public void generateTests(boolean makeABackup) {
		
		ListClasses ls = new ListClasses();
		
		List<String> classes = ls.getClasses();
		
		List<Class<?>> arrClasses = new ArrayList<Class<?>>();
		for(String className : classes) {
			try {
				arrClasses.add(Class.forName(className));
			} catch (ClassNotFoundException e) {
				System.out.println("Falha ao carregar classe: " + className);
				e.printStackTrace();
			}
		}

		ImportManager.addMapedReflections(arrClasses);
		
		generate(makeABackup, arrClasses);
	}

	private void generate(boolean makeABak, List<Class<?>> classes) {
		System.out.println("Executando ...");
		try {
			for (Class<?> classe : classes) {
				if (classe.isAnnotationPresent(JTCGen.class)) {
					StringBuffer buffer = new StringBuffer();
	
					List<TestGenerator> gens = TestGeneratorFactory.createGenerators(classe);
					for (TestGenerator gen : gens) {
						buffer.append(gen.generate());
					}
	
					DirectoryGenerator dir = TestGeneratorFactory.createDirectoryGenerator(classe);
					dir.createTest(
							buffer.toString().replaceFirst("\\{\\{OTHER_IMPORTS\\}\\}", ImportManager.getImports()),
							makeABak
					);
				}
			}
			System.out.println("Classes de testes geradas, verifique o source folder de testes.");
		} catch(JTCGenException e) {
			String simpleName = e.getClass().getSimpleName();
			StringBuffer error = new StringBuffer();
			error.append("Foram encontratos erros na geração dos testes, ");
			error.append("verifique o(s) seguinte(s) problema(s): \n\n");
			error.append("[" + simpleName + "] \"" + e.getMessage() + "\"");
			System.out.println(error);
			System.exit(0);
		}
	}
}
