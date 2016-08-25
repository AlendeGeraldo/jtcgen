package br.com.jtcgen.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GenerateTestNotSame {

	String param() default "";

	String expected() default "{{NULL}}";

	String method() default "{{NULL}}";

}
