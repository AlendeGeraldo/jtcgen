var param;
var mock = param = function (listaMocks) {
	var methodMap = [];
	var countParams = 0;
	
	if(listaMocks == undefined) 
		listaMocks = [];
	
	if(helper.isDiffType(listaMocks, '[object Array]')) {
		throw exception.invalidParam("[InvalidParamException] Tipo de parametro inválido na classe: "+actualClazz.getSimpleName()+" no método '.parameter()' . É necessário ser um array. Ex: [200.0, 'abc'];");
	}
	
	if(listaMocks.length > 0){
		listaMocks.forEach(function(item) {
			if(!helper.isDiffType(item, '[object Object]') && item != null) {
				
				if(	helper.isDiffType(item.c, '[object String]') || 
					item.v == undefined
				) {
					throw exception.invalidParam("[InvalidParamException] Tipo de parametro inválido na classe: "+actualClazz.getSimpleName()+" no método '.parameter()' . Para criar mocks é necessário respeitar o JSON padrão. Ex.: {c:'Clazz@someMethod()', v:['value-example']};");
				}
				
				if(!helper.isDiffType(item.v, '[object Object]')) {
					throw exception.invalidParam("[InvalidParamException] Tipo de parametro inválido na classe: "+actualClazz.getSimpleName()+" no método '.parameter()' . Não é possivel definir objetos no atributo 'v'. Utilize apenas tipos primitivos;");
				}
				
				var clazzMethods = item.c.split('@');
				var clazzName = clazzMethods[0];
				var sameCall = false;
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
						returns: item.v[i-1],
						sameCall: sameCall
					});
					sameCall = true;
				}
			} else {
				if(!helper.isDiffType(item, '[object Object]')) {
					throw exception.invalidParam("[InvalidParamException] Tipo de parametro inválido na classe: "+actualClazz.getSimpleName()+" no método '.parameter()'. Não são suportados parametros do tipo Objeto;");
				} else if (!helper.isDiffType(item, '[object Array]')) {
					throw exception.invalidParam("[InvalidParamException] Tipo de parametro inválido na classe: "+actualClazz.getSimpleName()+" no método '.parameter()'. Não são suportados parametros do tipo Array;");
				}

				
				var clazzParam = actualMethod.getParameterTypes()[countParams];
				
				var paramReturn = clazzParam.getSimpleName();
				
				switch(paramReturn) {
					case "String":
						if(item != null)
							item = '"' + item + '"';
						break;
					case "double": 
						item = helper.parseDouble(item);
						break;
					case "float": 
						item = helper.parseDouble(item);
						break;
					case "char": 
						item = '\'' + item + '\'';
						break;
					default: 
						item = item;
				}
				
				methodMap.push({
					clazz: "__SCALAR_TYPE__", 
					method: "__SCALAR_TYPE__", 
					returns: item
				});
			}
			countParams++;
		});
	}

	mockery.itens = methodMap;
	
	return mockery;
};