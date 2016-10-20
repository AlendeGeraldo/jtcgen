var param;
var mock = param = function (listaMocks) {
	var methodMap = [];
	var countParams = 0;
	listaMocks.forEach(function(item) {
		if(typeof item  == "object") {
			var clazzMethods = item.c.split('@');
			var clazzName = clazzMethods[0];
			for(var i=1; i < clazzMethods.length; i++) {
				var metodos = ImportManager.reflections().get(clazzName).getDeclaredMethods();
				
				for(var j=0; j < metodos.length; j++) {
					var metodo = metodos[j];
					
					if(metodo.getName().equals(clazzMethods[i].replace(/\(|\)/g, ""))) {
						if(metodo.getReturnType().getSimpleName().equals("double")) {
							item.v = (regex.isInteger(item.v)) ? item.v.toFixed(1) : item.v;
						}
					}
				} 
				
				methodMap.push({
					clazz: clazzName, 
					method: clazzMethods[i], 
					returns: item.v
				});
			}
		} else {
			var clazzParam = actualMethod.getParameterTypes()[countParams];
			
			var paramReturn = clazzParam.getSimpleName();
			
			switch(paramReturn) {
			case "string":
				item = '"' + item + '"';
				break;
			case "double": 
				item = helper.parseDouble(item);
			}
			
			methodMap.push({
				clazz: "__SCALAR_TYPE__", 
				method: "__SCALAR_TYPE__", 
				returns: item
			});
		}
		i++;
	});

	mockery.itens = methodMap;
	
	return mockery;
};