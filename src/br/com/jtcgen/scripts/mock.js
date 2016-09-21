'use strict';

//mock('Leilao@getLances()').returns(mockList('Lance@getValor()', [200.0, 300.0, 400.0]))
var mocks = {
	sequence: {clazz: null, method: null, returns: null},
	finalized : {},
	mock : function (str) {
			mocks.sequence.clazz = str.split('@')[0];
			mocks.sequence.method = str.split('@')[1];
			mocks.sequence.returns = null;
		
		return mocks;
	},
	returns: function (str) {
		mocks.sequence.returns = str;
		return mocks;
	},
	finalize: function () {
		var buffer = "";
		buffer += "\t\t\t" + mocks.sequence.clazz + " " + mocks.sequence.clazz.toLowerCase() +
		  " = mock( " + mocks.sequence.clazz + ".class );\n";
		buffer += "\t\t\t" + "when("+mocks.sequence.clazz.toLowerCase()+"."+mocks.sequence.method+").thenReturns("+mocks.sequence.returns.localVar+");\n\n";
		
		mocks.finalized = {variavel: mocks.sequence.clazz.toLowerCase(), str : mocks.sequence.returns.str + buffer};
		
		return mocks.finalized.str; 
	},
	getVariavel: function (){
		return mocks.finalized.variavel;
	}
};

var mock = mocks.mock;