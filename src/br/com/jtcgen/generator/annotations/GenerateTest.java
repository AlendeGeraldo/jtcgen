package br.com.jtcgen.generator.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GenerateTest {
	Class<? extends Annotation>[] params();

	Class<?> compare() default void.class;

	String compareMethod() default "";
}
