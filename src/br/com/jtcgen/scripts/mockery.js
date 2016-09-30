'use strict';

//mock('Leilao@getLances()').returns(mockList('Lance@getValor()', [200.0, 300.0, 400.0]))
var mock = function (listaMocks) {
	var mapaMetodos = [];
	listaMocks.forEach(function(item){
		var classeMetodo = item.split('@');
		var classe = item[0];
		for(var i=1; i < item.lenght; i++) {
			mapaMetodos[mapaMetodos.lenght] = {clazz: classe, method: item};
		}
	});
	
	mockery.itens = mapaMetodos;
	
	return {
		returns: function(listaRetorno){
			for(var i = 0; i < listaRetorno.lenght;i++){
				mockery.itens[i].returns = listaRetorno[i];
			}
		}
	};
}

var mockery = {
	exec: function () {
		var finalMocks = {};
		this.itens.forEach(function(item) {
			var buffer = "";
			var clazz = item.clazz;
			var shortClazz = regex.removeFullClassName(item.clazz, "");
			var method = item.method;
			var returns = item.returns;

			buffer += TextEditor.newLine(
				shortClazz + " " + shortClazz.toLowerCase() + " = mock( " + shortClazz + ".class );";
			, 2) +
			TextEditor.newLine(
				"when(" + shortClazz.toLowerCase() + "." + method + ").thenReturn(" + returns + ");";
			, 2);

			buffer += TextEditor.LINE_BREAK + TextEditor.LINE_BREAK;
					
			finalMocks.ref = clazz.toLowerCase();
			finalMocks.value = finalMocks.value + buffer;
		});

		return finalMocks; 
	}
}