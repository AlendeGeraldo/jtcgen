package br.com.jtcgen;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

import br.com.jtcgen.generator.annotations.JTCGen;
import br.com.jtcgen.generator.annotations.Number;
import sun.reflect.annotation.AnnotationType;

class ClassAnnotationReader {

	private Class<?> clazz;
	private Map<Constructor<?>, Map<Parameter, Annotation>> constructors;
	private Map<Method, Map<Parameter, Annotation>> methods;

	public ClassAnnotationReader(Class<?> clazz) {
		this.clazz = clazz;
	}

	public void readClass() {
		reset();

		Class<?> c = this.clazz;

		if (!c.isAnnotationPresent(JTCGen.class))
			throw new RuntimeException("Permission Denied! Do not exists @JTCGen annotated.");

		Constructor<?>[] constructors = c.getConstructors();

		this.constructors = readConstructors(constructors);

		Method[] methods = c.getMethods();

		this.methods = readMethods(methods);

	}

	public Map<Constructor<?>, Map<Parameter, Annotation>> getConstructors() {
		return constructors;
	}

	public Map<Method, Map<Parameter, Annotation>> getMethods() {
		return methods;
	}

	public Map<Constructor<?>, Map<Parameter, Annotation>> readConstructors(Constructor<?>... constructors) {
		return methodAndConstructorReader(constructors);
	}

	public Map<Method, Map<Parameter, Annotation>> readMethods(Method... methods) {
		return methodAndConstructorReader(methods);
	}

	private Map<Constructor<?>, Map<Parameter, Annotation>> methodAndConstructorReader(Constructor<?>... constr) {
		Map<Constructor<?>, Map<Parameter, Annotation>> mapa = new HashMap<Constructor<?>, Map<Parameter, Annotation>>();
		for (Constructor<?> con : constr) {
			Map<Parameter, Annotation> mapaParametros = null;
			Annotation[][] annotationConstruct = con.getParameterAnnotations();
			for (Annotation[] annotation : annotationConstruct) {
				mapaParametros = new HashMap<Parameter, Annotation>();
				for (int i = 0; i < annotation.length; i++) {
					mapaParametros.put(con.getParameters()[i], annotation[i]);
				}
			}
			mapa.put(con, mapaParametros);
		}
		return mapa;
	}

	private Map<Method, Map<Parameter, Annotation>> methodAndConstructorReader(Method... methods) {
		Map<Method, Map<Parameter, Annotation>> mapa = new HashMap<Method, Map<Parameter, Annotation>>();
		for (Method method : methods) {
			Map<Parameter, Annotation> mapaParametros = null;
			Annotation[][] annotationConstruct = method.getParameterAnnotations();
			for (Annotation[] annotation : annotationConstruct) {
				mapaParametros = new HashMap<Parameter, Annotation>();
				for (int i = 0; i < annotation.length; i++) {
					mapaParametros.put(method.getParameters()[i], annotation[i]);
				}
			}
			mapa.put(method, mapaParametros);
		}
		return mapa;
	}

	private void reset() {
		this.constructors = null;
		this.methods = null;
	}

}
