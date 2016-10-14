package br.com.jtcgen.builder.methods;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.mockito.Mockito;

import br.com.jtcgen.annotations.Test;
import br.com.jtcgen.helpers.ImportManager;

public class TestExpression extends TestMethodTemplate{
	
	public TestExpression(Method method, Class<?> clazz) {
		super(method, clazz);
	}

	@Override
	protected String getContent() {
		
		ImportManager.addImportStatic(Mockito.class);
		
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
		
		Test ann = this.method.getAnnotation(Test.class);
		
		StringBuffer methodTest = new StringBuffer();
		for(String value : ann.value()){
			try {
				Bindings bind = engine.createBindings();
				bind.put("actualClazz", this.clazz);
				bind.put("actualMethod", this.method);
				
				engine.setBindings(bind, ScriptContext.GLOBAL_SCOPE);
				
				engine.eval("load('src/br/com/jtcgen/scripts/import.js')");
				
				methodTest.append((String) engine.eval(value));
			} catch (ScriptException e) {
				methodTest.append("public void anErrosWasOccurred(){String string = \"error\";}");
				System.out.println("Falha evaluacao do javascript.");
				e.printStackTrace();
			}
		}
		
		
		return methodTest.toString();
		/*
		TestEquals test = (TestEquals) method.getAnnotation(TestEquals.class);
			
		String parametro = "", expected = "";
		if(test.value().length == 2){
			parametro = test.value()[0];
			expected = test.value()[1];
		} else 
			expected = test.value()[0];
		

		String[] params = getParams(parametro);

		Parameter[] pts = method.getParameters();

		if (!isValidParams(params))
			throw new InvalidParamDeclarationException("Valor total de parametros incorretos: " 
					+ method.getName());

		StringBuilder assinaturaMetodo = new StringBuilder();
		
		String paramAdicionais = getParamAdicional();

		String resultExpected = parseExpectedValue(expected, method);
		
		assinaturaMetodo.append(createMethodCall(pts, params));

		String content = TextEditor.newLine("assertEquals(" + resultExpected + ", resultado" + paramAdicionais + ");",
				2);
		assinaturaMetodo.append(content);

		return assinaturaMetodo.toString();*/
	}

}
