package br.com.jtcgen;

import example.classes.ContaCorrente;

public class JTCGen {

	public static void main(String[] args) throws Exception {
		ContaCorrente contaCorrente = new ContaCorrente(1000, 2000, 5000.0);

		ClassReader cl = new ClassReader(contaCorrente.getClass());

		cl.readClass();
	}

}
