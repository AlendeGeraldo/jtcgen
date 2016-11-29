var exec =  function () {
	var namesInUse = NashornBag.get();
	
	var finalMocks = {params: "", value: ""};
	if(mockery.itens != undefined) {
		mockery.itens.forEach(function(item) {
			if(item.sameCall == undefined)
				item.sameCall = false;
			if(item.clazz != "__SCALAR_TYPE__" && item.clazz != "__SCALAR_TYPE__") {
				var buffer = "";
				var clazz = item.clazz;
				var shortClazz = regex.removeFullClassName(item.clazz, "");
				var shortClazzLower = shortClazz.toLowerCase();
				var method = item.method;
				var returns = item.returns;
				
				if(!item.sameCall){
					namesInUse.forEach(function (name){
						if(shortClazzLower == name) {
							shortClazzLower = name + "1";
						}
					});
				}

				var template = templates.mockDef;
				if(item.sameCall){
					template = templates.mockDefSameCall;
				}
		
				buffer += TextEditor.newLine(
					regex.replaces(template, {
						'shortClazz': shortClazz,
						'shortClazzLower': shortClazzLower,
						'method': method,
						'returns': returns
					}) 
				, 2);
				
				NashornBag.add(shortClazzLower);
		
				buffer += TextEditor.LINE_BREAK;
				if(!item.sameCall)
					finalMocks.params += shortClazzLower + ", ";
				else
					finalMocks.params += "";
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

var makeObjMockCall = function(item) {
	var methodMap = [];
	if(typeof item == "object"){
		var clazzMethods = item.c.split('@');
		var clazzName = clazzMethods[0];
		for(var i=1; i < clazzMethods.length; i++) {
			if(helper.isDiffType(item.v, '[object Array]')) {
				item.v = [item.v];
			}
			var metodos = [];
			if(ImportManager.reflections().get(clazzName) != null)
				metodos = ImportManager.reflections().get(clazzName).getDeclaredMethods();
			
			
			for(var j=0; j < metodos.length; j++) {
				var metodo = metodos[j];
				
				if(metodo.getName().equals(clazzMethods[i].replace(/\(|\)/g, ""))) {
					if(metodo.getReturnType().getSimpleName().equals("double")) {
						item.v[i-1] = (regex.isInteger(item.v[i-1])) ? item.v[i-1].toFixed(1) : item.v[i-1];
					} else if(metodo.getReturnType().getSimpleName().equals("String")) {
						item.v[i-1] = '"' + item.v[i-1] + '"';
					} else if(metodo.getReturnType().getSimpleName().equals("char")) {
						item.v[i-1] = '\'' + item.v[i-1] + '\'';
					}
				}
			} 
			
			methodMap.push({
				clazz: clazzName, 
				method: clazzMethods[i], 
				returns: item.v[i-1]
			});
		}
	}
	return methodMap;
};

var makeStrCall = function(item, sameCall) {
	if(item == undefined){
		print("Ocorreu um erro em makeStrCall");
		return;
	}
	var namesInUse = NashornBag.get();
	
	var buffer = "";
	var clazz = item.clazz;
	var shortClazz = regex.removeFullClassName(item.clazz, "");
	var shortClazzLower = shortClazz.toLowerCase();
	var method = item.method;
	var returns = item.returns;
	
	if(!sameCall){
		namesInUse.forEach(function (name){
			if(shortClazzLower == name) {
				shortClazzLower = name + "1";
			}
		});
	}

	var template = templates.mockDef;
	if(sameCall){
		template = templates.mockDefSameCall;
	}
	
	buffer += TextEditor.newLine(
		regex.replaces(template, {
			'shortClazz': shortClazz,
			'shortClazzLower': shortClazzLower,
			'method': method,
			'returns': returns
		}) 
	, 2);
	
	NashornBag.add(shortClazzLower);

	buffer += TextEditor.LINE_BREAK;
			
	return {variable: shortClazzLower, strCall : buffer};
}