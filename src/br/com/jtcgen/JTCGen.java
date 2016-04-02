package br.com.jtcgen;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import example.classes.ContaCorrente;

class JTCGen {

	public static void main(String[] args) throws Exception {
		ContaCorrente contaCorrente = new ContaCorrente(1000, 2000, 5000.0);

		AnnotationReader cl = new AnnotationReader(contaCorrente.getClass());

		cl.readClass();
	}

}
