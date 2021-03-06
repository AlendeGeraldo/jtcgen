package br.com.jtcgen.builder.methods;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.mockito.Mockito;

import br.com.jtcgen.annotations.Test;
import br.com.jtcgen.exceptions.InvalidParamDeclarationException;
import br.com.jtcgen.helpers.ImportManager;
import br.com.jtcgen.helpers.TextEditor;

public abstract class TestMethodTemplate {

	protected StringBuffer methodBuffer = new StringBuffer();
	protected Class<?> clazz;
	protected Method method;
	protected Annotation testScene;

	public TestMethodTemplate(Method method, Class<?> clazz) {
		this.method = method;
		this.clazz = clazz;
		this.testScene = null;
	}

	public String createMethod() {
		return createMethod("");
	}

	public String createMethod(String sufixName) {

		methodBuffer.append(getSignatureMethod(sufixName));

		methodBuffer.append(getContent());

		methodBuffer.append(getEndMethod());

		return methodBuffer.toString();
	}

	public String extractSufix() {
		return this.getClass().getSimpleName().replaceFirst("^(TestAssert)", "");
	}

	protected final String getSignatureMethod(String sufixName) {
		String sufix = (!sufixName.equals("")) ? sufixName.substring(0, 1).toUpperCase() + sufixName.substring(1) : "";
		return TextEditor.newLine("@Test", 1)
				+ TextEditor.newLine("public void " + method.getName() + sufix + "() {", 1);
	}
	
	protected final String getSignatureMethod(String name, String sufixName) {
		String sufix = (!sufixName.equals("")) ? sufixName.substring(0, 1).toUpperCase() + sufixName.substring(1) : "";
		return TextEditor.newLine("@Test", 1)
				+ TextEditor.newLine("public void " + name + sufix + "() {", 1);
	}

	protected abstract String getContent();

	protected final String getEndMethod() {
		return TextEditor.newLine("}" + TextEditor.newLine("", 1), 1);
	}
	
	protected String createMethodCall(Parameter[] pts, String[] params) {
		return createMethodCall(pts, params, "");
	}
	
	protected String createMethodCall(Parameter[] pts, String[] params, String countAnn) {
		int count = 0;
		StringBuilder buffer = new StringBuilder();
		
		IInternalBehaviors[] iibs = TestInternalBehaviors.make();
		for(IInternalBehaviors iib : iibs) {
			buffer.append(TextEditor.newLine(iib.behave(method, testScene), 2));
		}

		if (!(method.getReturnType() == void.class)) {
			if (method.getReturnType().isPrimitive())
				buffer.append(method.getReturnType() + " resultado"+countAnn+" = ");
			else {
				buffer.append(method.getReturnType().getSimpleName() + " resultado"+countAnn+" = ");
				ImportManager.addImport(method.getReturnType());
			}
		}

		buffer.append("this.instance." + method.getName() + "(");
		for (Parameter p : pts) {
			Class<?> type = p.getType();

			String param;
			if (type == String.class) {
				param = '"' + params[count] + '"';
			} else if (type== char.class) {
				param = '\'' + params[count] + '\'';
			} else if (type == double.class || type == float.class) {
				param = params[count];
				
				if(!params[count].matches("^[0-9]+\\.[0-9]+$")) {
					if(params[count].matches("^[0-9]+$")) {
						param = params[count] + ".0";
					} else {
						throw new InvalidParamDeclarationException(
								"Tipo de parâmetro inválido na annotation sobre o metodo '" + method.getName() + "' da classe " + clazz.getSimpleName() + ". Valor inválido: " + params[count]
						);
					}
				}
				
				if(type == float.class) {
					param = new String(param + "f");
				}
				
			} else if (type == int.class || type == long.class) {
				param = params[count];
				if(!params[count].matches("^[0-9]+$")) {
					if(params[count].matches("^[0-9]+\\.[0-9]+$")) {
						param = params[count].split("\\.")[0];
					} else {
						throw new InvalidParamDeclarationException(
								"Tipo de parâmetro de retorno inválido na annotation sobre o metodo '" + method.getName() + "' da classe " + clazz.getSimpleName() + ". Valor de retorno inválido: " + params[count]
						);
					}
					
				}
			} else {
				param = params[count];
			}

			buffer.append(((params.length - 1) == count) ? param : param + ",");
			count++;
		}

		buffer.append(");");

		return TextEditor.newLine(buffer.toString(), 2);
	}

	protected String getParamAdicional() {
		StringBuffer retorno = new StringBuffer();
		if (method.getReturnType() == double.class || method.getReturnType() == float.class)
			retorno.append(", 0.00001");

		return retorno.toString();
	}

	protected String[] getParams(String parametro) {
		String[] params = {};
		if (!parametro.equals(""))
			params = parametro.split(";");

		return params;
	}

	protected boolean isValidParams(String[] params) {
		if (params.length != method.getParameterCount())
			return false;

		return true;
	}

	protected boolean hasMethodCompareOrExpected(String metCompare, String expected) {
		return (metCompare.equals("{{NULL}}") && expected.equals("{{NULL}}"));
	}

	protected String parseExpectedValue(String value, Method method) {
		String str;
		Class<?> type = method.getReturnType();
		if (type == String.class) {
			str = "\"" + value + "\"";
		} else if (type == char.class) {
			str = '\'' + value + '\'';
		} else if (type == double.class || type == float.class) {
			str = value;
			if(!value.matches("^[0-9]+\\.[0-9]+$")) {
				if(value.matches("^[0-9]+$")) {
					str = value + ".0";
				} else {
					throw new InvalidParamDeclarationException(
						"Tipo de parâmetro de retorno inválido na annotation sobre o metodo '" + method.getName() + "' da classe " + clazz.getSimpleName() + ". Valor de retorno inválido: " + value
					);
				}
			}
			
			if(type == float.class) {
				str = new String(str + "f");
			}
		} else if (type == int.class || type == long.class) {
			str = value;
			if(!value.matches("^[0-9]+$")) {
				if(value.matches("^[0-9]+\\.[0-9]+$")) {
					str = value.split("\\.")[0];
				} else {
					throw new InvalidParamDeclarationException(
							"Tipo de parâmetro de retorno inválido na annotation sobre o metodo '" + method.getName() + "' da classe " + clazz.getSimpleName() + ". Valor de retorno inválido: " + value
					);
				}
				
			}
		} else {
			str = value;
		}
		
		return str;
	}

}