var eqVoid = function(expected, methodToCall) {
	
	var assert = "assertEquals";
	var instance = actualClazz.getSimpleName().toLowerCase();
	var returnType = actualMethod.getReturnType().getSimpleName();
	
	mockery.exec();
	
	var stack = mockery.stack;
	var str = setupBuffer + buffer + stack.value;
	
	var paramAdd = "";
	
	/*pegando tipo de retorno da função informada*/
	var metodos = ImportManager.reflections().get(actualClazz.getSimpleName()).getDeclaredMethods();
	var methodToCallReturnType;
	for(var j=0; j < metodos.length; j++) {
		var metodo = metodos[j];
		
		if(metodo.getName().equals(methodToCall.replace(/\([a-zA-Z0-9,\s\.\<\>]{0,}\)/g, ""))) {
			methodToCallReturnType = metodo.getReturnType();
		}
	}
	
	if(returnType == "double") {
		paramAdd = ", 0.0001";
		
		if(regex.isInteger(expected)) expected = expected.toFixed(1);
	}
	
	if(!/[a-zA-Z0-9]+\([a-zA-Z0-9,\s\.\<\>]+\)/g.test(methodToCall)) {
		methodToCall = (!/\)$/g.test(methodToCall)) ? methodToCall + "()" : methodToCall;
	}
	
	str += TextEditor.newLine(
			regex.replaces(templates.assert, {
			shortClazzLower: instance,
			'returns': methodToCallReturnType,
			'methodTarget': methodToCall,
			'assert': assert,
			'expected': expected,
			'method': actualMethod.getName(),
			'params': stack.params,
			'paramAdd': paramAdd
		})
	, 2);
	
	return str;
};