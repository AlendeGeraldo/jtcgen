package br.com.jtcgen;

public interface TestCaseGenerable {

	public void generateTests(String sourceDir, boolean makeABackup, Class<?>... classes);
	
	public void generateTests(String sourceDir, boolean makeABackup);
}
