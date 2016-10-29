package br.com.jtcgen;

public interface TestCaseGenerable {

	public void generateTests(boolean makeABackup, Class<?>... classes);
	
	public void generateTests(boolean makeABackup);
}
