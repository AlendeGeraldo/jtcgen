package br.com.jtcgen.builder;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Test;

import br.com.jtcgen.testdatabuilder.ClazzTestDataBuilder;

public class DirectoryGeneratorTest {

	@Test
	public void deveCriarDiretorioEArquivoDaClasseDeTeste() throws FileNotFoundException {
		
		DirectoryGenerator directoryGenerator = new DirectoryGenerator(ClazzTestDataBuilder.criaClasseExemplo());
		
		directoryGenerator.createTest("Created!");
			
		File teste = new File("tests/br/com/jtcgen/testdatabuilder/ContaTest.java");
		Scanner scan = new Scanner(teste);
		
		assertTrue(teste.exists());
		assertEquals("Created!", scan.nextLine());
		
		scan.close();
	}
	
	@After
	public void tearDown() {
		new File("tests/br/com/jtcgen/testdatabuilder/ContaTest.java").delete();
	}
}
