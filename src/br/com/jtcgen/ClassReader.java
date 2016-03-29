package br.com.jtcgen;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import br.com.jtcgen.generator.annotations.JTCGen;
import br.com.jtcgen.generator.annotations.Number;
import sun.reflect.annotation.AnnotationType;

public class ClassReader {

	private Class<?>[] clazz;

	public ClassReader(Class<?>... clazzs) {
		this.clazz = clazzs;
	}

	public void readClass() throws Exception {
		for (Class<?> c : this.clazz) {

			if (c.isAnnotationPresent(JTCGen.class)) {
				JTCGen ann = c.getAnnotation(JTCGen.class);
				System.out.println("Consegui Ler a annotation " + JTCGen.class.getName() + ": " + ann.value());
			} else {
				System.out.println(c.getName() + " não tem annotations do tipo:" + JTCGen.class.getName());
			}

			Constructor<?>[] cons = c.getConstructors();

			System.out.println(cons.length);

			for (Constructor<?> con : cons) {
				if (con.isAnnotationPresent(Number.class)) {
					Number ann = con.getAnnotation(Number.class);
					System.out.println("Consegui Ler a annotation " + Number.class.getName() + ": attrs("
							+ ann.minLength() + ", " + ann.maxLength() + ", " + ann.precision() + ");");
				} else {
					System.out.println(con.getName() + " não tem annotations @Number");
				}
			}
		}
	}

}
