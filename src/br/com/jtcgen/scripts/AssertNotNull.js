var isNotNull = function() {
	var assert = "assertNotNull";
	var instance = actualClazz.getSimpleName().toLowerCase();
	var returnType = actualMethod.getReturnType().getSimpleName();
	
	mockery.exec();
	
	var stack = mockery.stack;
	var str = buffer + stack.value;
	
	var paramAdd = "";
	
	if(returnType == "double") {
		paramAdd = ", 0.0001";
		
		if(regex.isInteger(expected)) expected = expected.toFixed(1);
	}
	
	str += TextEditor.newLine(
			regex.replaces(templates.assertOneParam, {
			shortClazzLower: instance,
			'returns': returnType,
			'assert': assert,
			'method': actualMethod.getName(),
			'params': stack.params,
			'paramAdd': paramAdd
		})
	, 2);
	
	return str;
}