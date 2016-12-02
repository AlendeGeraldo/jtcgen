package br.com.jtcgen.builder.methods;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.mockito.Mockito;

import br.com.jtcgen.annotations.Test;
import br.com.jtcgen.annotations.TestEquals;
import br.com.jtcgen.annotations.Tests;
import br.com.jtcgen.exceptions.InvalidParamDeclarationException;
import br.com.jtcgen.exceptions.JTCGenException;
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
		
		Test[] tests = method.getAnnotationsByType(Test.class);
		
		StringBuffer methodTest = new StringBuffer();
		
		int count = 1;
		for(Test test: tests) {
			String value = test.value();
			try {
				Bindings bind = engine.createBindings();
				bind.put("actualClazz", this.clazz);
				bind.put("actualMethod", this.method);
				
				engine.setBindings(bind, ScriptContext.GLOBAL_SCOPE);
				
				List<URL> resources = new ArrayList<URL>(); 
				resources.add(this.getClass().getResource("/br/com/jtcgen/scripts/templates.js"));
				resources.add(this.getClass().getResource("/br/com/jtcgen/scripts/regex.js"));
				resources.add(this.getClass().getResource("/br/com/jtcgen/scripts/helpers.js"));
				resources.add(this.getClass().getResource("/br/com/jtcgen/scripts/setup.js"));
				resources.add(this.getClass().getResource("/br/com/jtcgen/scripts/parameter-definition.js"));
				resources.add(this.getClass().getResource("/br/com/jtcgen/scripts/returns.js"));
				resources.add(this.getClass().getResource("/br/com/jtcgen/scripts/assert-equals.js"));
				resources.add(this.getClass().getResource("/br/com/jtcgen/scripts/assert-equals-void.js"));
				resources.add(this.getClass().getResource("/br/com/jtcgen/scripts/assert-null.js"));
				resources.add(this.getClass().getResource("/br/com/jtcgen/scripts/assert-not-null.js"));
				resources.add(this.getClass().getResource("/br/com/jtcgen/scripts/assert-false.js"));
				resources.add(this.getClass().getResource("/br/com/jtcgen/scripts/assert-true.js"));
				resources.add(this.getClass().getResource("/br/com/jtcgen/scripts/test-stack-execution.js"));
				resources.add(this.getClass().getResource("/br/com/jtcgen/scripts/collections.js"));
				resources.add(this.getClass().getResource("/br/com/jtcgen/scripts/mockery.js"));
				
				StringBuffer script = new StringBuffer();
				try {
					for(URL resource : resources){
						Path path = null;
						FileSystem fs = null;
						if(resource.toString().split("!").length > 1){
							final Map<String, String> env = new HashMap<>();
							final String[] array = resource.toString().split("!");
							fs = FileSystems.newFileSystem(URI.create(array[0]), env);
							path = fs.getPath(array[1]);
						}else {
							path = Paths.get(resource.toURI());
						}
						
						if(path != null){
							List<String> readAllLines = Files.readAllLines(path);
							for(String s : readAllLines)
								script.append(s);
							engine.eval(script.toString());
						}
						if(fs != null)
							fs.close();
						
						script = new StringBuffer();
					}
					
				} catch (URISyntaxException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				methodTest.append((String) engine.eval(value));
				
				if(count < tests.length){
					methodTest.append("\n");
				}
			} catch (ScriptException e) {
				
				String message = e.getLocalizedMessage();
				if(message.matches("^.+\\[InvalidParamException\\].+")) {
					String newMens = message.replaceFirst("^.+\\[InvalidParamException\\]", "").split(";")[0];
					
					throw new InvalidParamDeclarationException(newMens);
				}
				
				System.out.println(e.getMessage() + " " + e.getLineNumber() + " " +  e.getColumnNumber() + " " + e.getFileName());
				e.printStackTrace();
			}
			count++;
		}
		
		NashornBag.clearBag();
		
		return methodTest.toString();
	}

}
