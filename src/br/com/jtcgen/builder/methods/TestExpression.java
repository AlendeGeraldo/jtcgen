package br.com.jtcgen.builder.methods;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.mockito.Mockito;

import br.com.jtcgen.annotations.Test;
import br.com.jtcgen.annotations.Tests;
import br.com.jtcgen.helpers.ImportManager;
import br.com.jtcgen.helpers.NashornBag;

public class TestExpression extends TestMethodTemplate{
	
	public TestExpression(Method method, Class<?> clazz) {
		super(method, clazz);
	}

	@Override
	protected String getContent() {
		
		ImportManager.addImportStatic(Mockito.class);
		
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
		
		Test ann = this.method.getAnnotation(Test.class);

		List<String> annotationValues = new ArrayList<String>();
		
		if(ann == null){
			Tests anns = this.method.getAnnotation(Tests.class);
			
			if(anns != null) {
				Test[] value = anns.value();
				
				for(Test t: value){
					for(String s: t.value())
						annotationValues.add(s);
				}
			}
		} else {
			for(String s: ann.value())
				annotationValues.add(s);
		}
		
		StringBuffer methodTest = new StringBuffer();
		
		int count = 1;
		for(String value : annotationValues) {
			try {
				Bindings bind = engine.createBindings();
				bind.put("actualClazz", this.clazz);
				bind.put("actualMethod", this.method);
				
				engine.setBindings(bind, ScriptContext.GLOBAL_SCOPE);
				
				engine.eval("load('src/br/com/jtcgen/scripts/import.js')");
				
				methodTest.append((String) engine.eval(value));
				
				if(count < annotationValues.size()){
					methodTest.append("\n");
				}
			} catch (ScriptException e) {
				methodTest.append("public void anErrosWasOccurred(){String string = \"error\";}");
				System.out.println("Falha evaluacao do javascript.");
				e.printStackTrace();
			}
			count++;
		}
		
		NashornBag.clear();
		
		return methodTest.toString();
	}

}
