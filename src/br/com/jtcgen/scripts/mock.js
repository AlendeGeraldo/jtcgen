'use strict';

mock('examples.classes.Leilao@getLances() = mkList(\\'mock(\\'examples.classes.Lance@getValor() = 300.0\\')\\')')
mock('Leilao@getLances()').
var mocks = {
	sequence: [],
	mock : function (str) {
		var parts = str.split("=");
		var call = parts[0];
		var valueMock = parts[1];
		print(call);
		print(valueMock);

		return this;
	},
	addInSequence: function(obj) {
		sequence.add(obj);
	},
	getInSequence: function(position) {
		return (sequence[position] != undefined) ? return sequence[position] : null;
	}
};

var mock = mocks.mock;