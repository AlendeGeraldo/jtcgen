var setupBuffer = "";

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
				if(arrTypes[j].getSimpleName().equals("double")){
					
					if(regex.isInteger(arrParams[j]))
						arrParams[j] = arrParams[j].toFixed(1);
					
				} else if(arrTypes[j].getSimpleName().equals("String")) {
					
					arrParams[j] = '"' + arrParams[j] + '"';
					
				} else if(arrTypes[j].getSimpleName().equals("char")) {
					
					arrParams[j] = '\'' + arrParams[j] + '\'';
					
				}
			}
		}
	}
	
	
	var parameters = "";
	arrParams.forEach(function(item) {
		if(typeof item == "object"){
			if(regex.isMockString(item.c)) {
				var mockObjs = makeObjMockCall(item);
				
				var results = [];
				if(mockObjs.length > 0){
					var sameCall = false;
					mockObjs.forEach(function(mockObj) {
						results.push(makeStrCall(mockObj, sameCall));
						sameCall = true;
					});
				}
				
				if(results.length > 0){
					results.forEach(function(result, i){
						if(i==0){
							parameters += result.variable + ", ";
						}
						setupBuffer += result.strCall;
					});
				}
				
			}
		} else {
			parameters += item + ", ";
		}
	});
	
	var noRepeatVarCount = (NashornBag.getCountExpected() == 0) ? "" : NashornBag.getCountExpected();
	var str = TextEditor.newLine(regex.replaces(templates.setup, 
		{
			shortClazz: actualClazz.getSimpleName(),
			shortClazzLower: actualClazz.getSimpleName().toLowerCase() + noRepeatVarCount,
			method: actualMethod.getName(),
			params: parameters.replace(/,\s$/g, '')
		}
	), 2);
	
	buffer += str + TextEditor.LINE_BREAK;
	
	return mockery;
};