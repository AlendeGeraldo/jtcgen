package br.com.jtcgen.generator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
public @interface Name {
	boolean specialCharsAcceptance() default false;

	int minLength() default 1;

	int maxLength() default 100;
}
