package br.com.jtcgen;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

import br.com.jtcgen.generator.annotations.Name;

class ConstructorAnnotationReader<T> extends AnnotationReader {

	public ConstructorAnnotationReader(Class<T> clazz) {
		super(clazz);
	}

	public <T> String getImplementedConstructor() {

		Constructor<?>[] constrs = this.clazz.getConstructors();

		StringBuffer callConstrStr = new StringBuffer();
		for (Constructor<?> construtor : constrs) {
			int totalDeParametros = construtor.getParameterCount();

			for (Parameter param : construtor.getParameters()) {

				if (param.isAnnotationPresent((Class<? extends Annotation>) Number.class)) {
					// Class<T> a = param.getDeclaredAnnotation(Number.class);
				} else if (param.isAnnotationPresent((Class<? extends Annotation>) Name.class)) {

				}
			}

		}

		return callConstrStr.toString();
	}

}
