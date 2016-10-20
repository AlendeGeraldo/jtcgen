var exec =  function () {
	var namesInUse = NashornBag.get();
	
	var finalMocks = {params: "", value: ""};
	if(mockery.itens != undefined) {
		mockery.itens.forEach(function(item) {
			if(item.clazz != "__SCALAR_TYPE__" && item.clazz != "__SCALAR_TYPE__") {
				var buffer = "";
				var clazz = item.clazz;
				var shortClazz = regex.removeFullClassName(item.clazz, "");
				var shortClazzLower = shortClazz.toLowerCase();
				var method = item.method;
				var returns = item.returns;
				
				namesInUse.forEach(function (name){
					if(shortClazzLower == name) {
						shortClazzLower = name + "1";
					}
				});
		
				buffer += TextEditor.newLine(
					regex.replaces(templates.mockDef, {
						'shortClazz': shortClazz,
						'shortClazzLower': shortClazzLower,
						'method': method,
						'returns': returns
					}) 
				, 2);
				
				NashornBag.add(shortClazzLower);
		
				buffer += TextEditor.LINE_BREAK;
						
				finalMocks.params += shortClazzLower + ", ";
				finalMocks.value += buffer;
			} else {
				finalMocks.params += item.returns + ", ";
			}
		});
	} else {
		
	}
	
	finalMocks.params = finalMocks.params.replace(/,\s$/, "");

	mockery.stack = (finalMocks);
	
	return mockery;
};