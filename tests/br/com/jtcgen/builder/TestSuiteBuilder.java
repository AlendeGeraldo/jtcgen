package br.com.jtcgen.builder;
import org.junit.runners.Suite;

import br.com.jtcgen.builder.DirectoryGenerator;

import org.junit.runner.RunWith;

@RunWith(Suite.class)
@Suite.SuiteClasses({DirectoryGenerator.class, EndTestGeneratorTest.class, SetUpGeneratorTest.class, TearDownGeneratorTest.class, TestClassGeneratorTest.class})
public class TestSuiteBuilder {
	
}