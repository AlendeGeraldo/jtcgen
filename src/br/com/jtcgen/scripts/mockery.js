//mock('Leilao@getLances()').returns(mockList('Lance@getValor()', [200.0, 300.0, 400.0]))
const tmpMock = '{{shortClazz}} {{shortClazzLower}} = mock({{shortClazz}}.class);' +
				'\n\t\twhen({{shortClazzLower}}.{{method}}).thenReturn({{returns}});';

const mock = function (listaMocks) {
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
}

const mockery = {
	exec: function () {
		var finalMocks = {};
		this.itens.forEach(function(item) {
			var buffer = "";
			var clazz = item.clazz;
			var shortClazz = regex.removeFullClassName(item.clazz, "");
			var method = item.method;
			var returns = item.returns;

			buffer += TextEditor.newLine(
				regex.replaces(tmpMock, {
					shortClazz: shortClazz,
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
	},
	returns: function(listaRetorno) {
		for(var i = 0; i < listaRetorno.lenght;i++){
			mockery.itens[i].returns = listaRetorno[i];
		}
	}
}