var setupBuffer = "";

var setup = function (arrParams) {
	if(arrParams == undefined)
		arrParams = [];
	if( helper.isDiffType(arrParams, "[object Array]")) {
		throw exception.invalidParam("[InvalidParamException] Tipo de parametro inválido na classe: "+actualClazz.getSimpleName()+" no método '.setup()' . É necessário ser um array. Ex: [200.0, 'abc'];");
	}
	 
	arrConstr = actualClazz.getConstructors();
	for(var i in arrConstr) {
		if(arrConstr[i].getParameterCount() == arrParams.length){
			
			arrTypes = arrConstr[i].getParameterTypes();
			for(var j in arrTypes) {
				
				if (!helper.isDiffType(arrParams[j], '[object Array]')) {
					throw exception.invalidParam("[InvalidParamException] Tipo de parametro inválido na classe: "+actualClazz.getSimpleName()+" no método '.setup()' . Não são suportados parametros do tipo Array;");
				}
				
				if(arrTypes[j].getSimpleName().equals("double")){
					if(regex.isInteger(arrParams[j]))
						arrParams[j] = arrParams[j].toFixed(1);
					
				} else if(arrTypes[j].getSimpleName().equals("float")){
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
		if(!helper.isDiffType(item, '[object Object]')) {
			if(	helper.isDiffType(item.c, '[object String]') || 
				item.v == undefined
			) {
				throw exception.invalidParam("[InvalidParamException] Tipo de parametro inválido na classe: "+actualClazz.getSimpleName()+" no método '.setup()' . Para criar mocks é necessário respeitar o JSON padrão. Ex.: {c:'Clazz@someMethod()', v:['value-example']};");
			}
			
			if(!helper.isDiffType(item.v, '[object Object]')) {
				throw exception.invalidParam("[InvalidParamException] Tipo de parametro inválido na classe: "+actualClazz.getSimpleName()+" no método '.setup()' . Não é possivel definir objetos no atributo 'v'. Utilize apenas tipos primitivos;");
			}
			
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