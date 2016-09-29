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

	protected abstract String getContent();

	protected final String getEndMethod() {
		return TextEditor.newLine("}" + TextEditor.newLine("", 1), 1);
	}

	protected String createMethodCall(Parameter[] pts, String[] params) {
		int count = 0;
		StringBuilder buffer = new StringBuilder();
		
		IInternalBehaviors[] iibs = TestInternalBehaviors.make();
		for(IInternalBehaviors iib : iibs) {
			buffer.append(TextEditor.newLine(iib.behave(method, testScene), 2));
		}

		if (!(method.getReturnType() == void.class)) {
			if (method.getReturnType().isPrimitive())
				buffer.append(method.getReturnType() + " resultado = ");
			else {
				buffer.append(method.getReturnType().getSimpleName() + " resultado = ");
				ImportManager.addImport(method.getReturnType());
			}
		}

		buffer.append("this.instance." + method.getName() + "(");
		for (Parameter p : pts) {
			Class<?> type = p.getType();

			String param;
			if (type.equals(String.class)) {
				param = '"' + params[count] + '"';
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
		if (method.getReturnType() == double.class)
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
		if (params.length != method.getParameterCount() && testScene == null)
			return false;

		return true;
	}

	protected boolean hasMethodCompareOrExpected(String metCompare, String expected) {
		return (metCompare.equals("{{NULL}}") && expected.equals("{{NULL}}"));
	}

	protected String parseExpectedValue(String value, Method method) {
		String str;
		if (method.getReturnType() == String.class) {
			str = "\"" + value + "\"";
		} else {
			str = value;
		}
		return str;
	}

	public void setScene(Annotation antecessora) {
		this.testScene = antecessora; 
		
	}
	
	protected Map<String,String> buildScene() {
		return testScene == null ? null : buildStrScene(testScene);
	}
	
	protected Map<String,String> buildStrScene(Annotation testScene) {
		
		ImportManager.addImportStatic(Mockito.class);
		
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
		try {
			engine.eval("load('src/br/com/jtcgen/scripts/import.js')");
		} catch (ScriptException e) {
			System.out.println("Erro na evaluação javascript");
			e.printStackTrace();
		}
		
		Test ann = this.method.getAnnotation(Test.class);
		
		Map<String, String> m = new HashMap<String, String>();
		for(String value : ann.value()){
			try {
				engine.eval(value);
				
				m.put("str", (String) engine.eval("mocks.finalize();"));
				m.put("var", (String) engine.eval("mocks.getVariavel();"));
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return m;
	}
	
}