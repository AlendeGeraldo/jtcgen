var setup = function (arrParams) {
	if( typeof arrParams != "object" ){
		errors.put({
			location: "setup",
			message: "Tipo de parametros inválido, é necessário ser um array. Ex. [200.0, 'test'] "
		});

		return mockery;
	}
	
	arrConstr = actualClazz.getConstructors();
	
	for(var i in arrConstr) {
		if(arrConstr[i].getParameterCount() == arrParams.length){
			arrTypes = arrConstr[i].getParameterTypes();
			for(var j in arrTypes) {
				if(arrTypes[j].getName().equals("double")){
					if(regex.isInteger(arrParams[j]))
						arrParams[j] = arrParams[j].toFixed(1);
				}
			}
		}
	}
	
	
	var parameters = "";
	arrParams.forEach(function(item) {
		if(typeof item == "object" && item.size == 2){
			if(regex.isMockString(item[0])) {
				mockery.mock();
			}
		} else {
			parameters += item + ", ";
		}
	});
	
	var str = TextEditor.newLine(regex.replaces(templates.setup, 
		{
			shortClazz: actualClazz.getSimpleName(),
			shortClazzLower: actualClazz.getSimpleName().toLowerCase(),
			method: actualMethod.getName(),
			params: parameters.replace(/,\s$/g, '')
		}
	), 2);
	
	buffer += str + TextEditor.LINE_BREAK;
	
	return mockery;
};