package br.com.jtcgen.builder.methods;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import br.com.jtcgen.annotations.Expected;
import br.com.jtcgen.annotations.TestEquals;
import br.com.jtcgen.annotations.Param;
import br.com.jtcgen.exceptions.InvalidParamDeclarationException;
import br.com.jtcgen.helpers.TextEditor;

public class TestAssertEquals extends TestMethodTemplate {

	public TestAssertEquals(Method method, Class<?> clazz) {
		super(method, clazz);
	}

	@Override
	public String getContent() {

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
		
		Map<String, String> scene = buildScene();// its a substitute of params;
		
		String paramAdicionais = getParamAdicional();

		String resultExpected = parseExpectedValue(expected, method);
		
		if(scene != null) {
			String paramExpected = scene.get("var");
			
			assinaturaMetodo.append(scene.get("str"));
			
			String[] newParams = new String[params.length+1];
			for(int i = 0; i < params.length; i++) {
				newParams[i] = params[i]; 
			}
			newParams[params.length] = paramExpected;
			params = newParams;
		}
		
		assinaturaMetodo.append(createMethodCall(pts, params));

		String content = TextEditor.newLine("assertEquals(" + resultExpected + ", resultado" + paramAdicionais + ");",
				2);
		assinaturaMetodo.append(content);

		return assinaturaMetodo.toString();
	}
}