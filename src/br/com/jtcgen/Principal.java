package br.com.jtcgen;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import br.com.jtcgen.generator.annotations.JTCGen;
import example.classes.ContaCorrente;

class Principal {

	public static void main(String[] args) throws Exception {
		Class<?> classe = ContaCorrente.class;

		if (classe.isAnnotationPresent(JTCGen.class)) {

		}

	}

}
