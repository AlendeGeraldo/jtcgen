var exec =  function () {
	var finalMocks = {params: "", value: ""};
	mockery.itens.forEach(function(item) {
		var buffer = "";
		var clazz = item.clazz;
		var shortClazz = regex.removeFullClassName(item.clazz, "");
		var method = item.method;
		var returns = item.returns;

		buffer += TextEditor.newLine(
			regex.replaces(templates.mockDef, {
				'shortClazz': shortClazz,
				'shortClazzLower': shortClazz.toLowerCase(),
				'method': method,
				'returns': returns
			})
		, 2);

		buffer += TextEditor.LINE_BREAK;
				
		finalMocks.params += clazz.toLowerCase() + ", ";
		finalMocks.value += buffer;
	});
	
	finalMocks.params = finalMocks.params.replace(/,\s$/, "");

	mockery.stack = (finalMocks);
	
	return mockery;
};