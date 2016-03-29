package br.com.jtcgen;

import example.classes.ContaCorrente;

public class JTCGen {

	public static void main(String[] args) {
		ClassReader cl = new ClassReader(ContaCorrente.class);

		cl.readClass();
	}

}
