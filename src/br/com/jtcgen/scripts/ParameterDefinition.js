var param;
var mock = param = function (listaMocks) {
	var methodMap = [];
	listaMocks.forEach(function(item) {
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
	});

	mockery.itens = methodMap;
	
	return mockery;
};