package br.com.jtcgen.builder.methods;

import java.lang.reflect.Method;

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
				
				engine.eval("load('src/br/com/jtcgen/scripts/Import.js')");
				
				methodTest.append((String) engine.eval(value));
			} catch (ScriptException e) {
				methodTest.append("public void anErrosWasOccurred(){String string = \"error\";}");
				System.out.println("Falha evaluacao do javascript.");
				e.printStackTrace();
			}
		}
		
		return methodTest.toString();
	}

}
