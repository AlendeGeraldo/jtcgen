//mock('Leilao@getLances()').returns(mockList('Lance@getValor()', [200.0, 300.0, 400.0]))
var buffer;
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
					if(/^[0-9]+$/.test(arrParams[j]))
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

	var str = regex.replaces(templates.setup, 
		{
			shortClazz: actualClazz.getSimpleName(),
			shortClazzLower: actualClazz.getSimpleName().toLowerCase(),
			params: parameters.replace(/,\s$/g, '')
		}
	);
	
	return mockery;
};

var param;
var mock = param = function (listaMocks) {
	var methodMap = [];
	listaMocks.forEach(function(item){
		var clazzMethods = item.split('@');
		var clazzName = clazzMethods[0];
		for(var i=1; i < clazzMethods.lenght; i++) {
			methodMap[methodMap.lenght] = {clazz: clazzName, method: clazzMethods[i]};
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
	var finalMocks = {};
	this.itens.forEach(function(item) {
		var buffer = "";
		var clazz = item.clazz;
		var shortClazz = regex.removeFullClassName(item.clazz, "");
		var method = item.method;
		var returns = item.returns;

		buffer += TextEditor.newLine(
			regex.replaces(templates.mockDef, {
				shortClazzLower: shortClazz.toLowerCase(),
				method: method,
				returns: returns
			})
		, 2);

		buffer += TextEditor.LINE_BREAK + TextEditor.LINE_BREAK;
				
		finalMocks.ref = clazz.toLowerCase();
		finalMocks.value = finalMocks.value + buffer;
	});

	return finalMocks; 
};

var mockery = {
	"mock" : mock,
	"parameter" : mock,
	"setup": setup,
	"returns": returns,
	"exec": exec
};