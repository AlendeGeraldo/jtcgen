'use strict';

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
	
	mockery.exec();
}

var mockery = {
	exec: function () {
		
	}
}