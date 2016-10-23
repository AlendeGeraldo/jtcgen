package br.com.jtcgen.builder;
import org.junit.runners.Suite;

import br.com.jtcgen.builder.methods.TestExpressionEqualsTest;
import br.com.jtcgen.builder.methods.TestExpressionOtherAssertsTest;

import org.junit.runner.RunWith;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	DirectoryGeneratorTest.class, 
	EndTestGeneratorTest.class, 
	SetUpGeneratorTest.class, 
	TearDownGeneratorTest.class, 
	TestClassGeneratorTest.class, 
	TestExpressionEqualsTest.class,
	TestExpressionOtherAssertsTest.class
})

public class TestSuite {
	
}