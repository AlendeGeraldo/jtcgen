package br.com.jtcgen.builder.methods;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.mockito.Mockito;

import br.com.jtcgen.annotations.Test;
import br.com.jtcgen.annotations.TestEquals;
import br.com.jtcgen.exceptions.InvalidParamDeclarationException;
import br.com.jtcgen.helpers.ImportManager;
import br.com.jtcgen.helpers.TextEditor;

public class TestExpression extends TestMethodTemplate{
	
	public TestExpression(Method method, Class<?> clazz) {
		super(method, clazz);
	}

	@Override
	protected String getContent() {
		
		ImportManager.addImportStatic(Mockito.class);
		
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
		try {
			engine.eval("load('src/br/com/jtcgen/scripts/import.js')");
		} catch (ScriptException e) {
			System.out.println("Erro na evaluacao javascript");
			e.printStackTrace();
		}
		
		Test ann = this.method.getAnnotation(Test.class);
		
		for(String value : ann.value()){
			try {
				System.out.println(engine.eval(value));
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return "";
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
