//mock('Leilao@getLances()').returns(mockList('Lance@getValor()', [200.0, 300.0, 400.0]))
var buffer = "";
var errors = [];

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

var returns = function(listaRetorno) {
	for(var i = 0; i < listaRetorno.lenght;i++){
		mockery.itens[i].returns = listaRetorno[i];
	}

	return mockery;
};

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

var eq = function(expected) {
	var assert = "assertEquals";
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
			regex.replaces(templates.assert, {
			shortClazzLower: instance,
			'returns': returnType,
			'assert': assert,
			'expected': expected,
			'method': actualMethod.getName(),
			'params': stack.params,
			'paramAdd': paramAdd
		})
	, 2);
	
	return str;
}

var mockery = {
	stack: [],
	"mock" : mock,
	"parameter" : mock,
	"setup": setup,
	"returns": returns,
	"exec": exec,
	"eq": eq
};