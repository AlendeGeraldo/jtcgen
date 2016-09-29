package br.com.jtcgen.builder;
import org.junit.runners.Suite;

import org.junit.runner.RunWith;

@RunWith(Suite.class)
@Suite.SuiteClasses({DirectoryGeneratorTest.class, EndTestGeneratorTest.class, SetUpGeneratorTest.class, TearDownGeneratorTest.class, TestClassGeneratorTest.class})
public class TestSuiteBuilder {
	
}