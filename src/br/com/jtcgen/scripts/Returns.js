var returns = function(listaRetorno) {
	for(var i = 0; i < listaRetorno.lenght;i++){
		mockery.itens[i].returns = listaRetorno[i];
	}

	return mockery;
};