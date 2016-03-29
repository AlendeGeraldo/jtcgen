package br.com.jtcgen;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;

import br.com.jtcgen.generator.annotations.Number;

public class ClassReader {

	private Class<?>[] clazz;

	public ClassReader(Class<?>... clazzs) {
		this.clazz = clazzs;
	}

	public void readClass() {
		for (Class<?> c : this.clazz) {

			Constructor<?>[] cs = c.getConstructors();

			for (Constructor<?> construc : cs) {
				AnnotatedType[] ann = construc.getAnnotatedParameterTypes();

				System.out.println(ann[0].getAnnotation(Number.class));
			}
		}
	}

}
