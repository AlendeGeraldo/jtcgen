'use strict';

var mocks = {
	mock : function (str) {
		var parts = str.split("=");
		var call = parts[0];
		var valueMock = parts[1];
		print(call);
		print(valueMock);

		return this;
	}
};

var mock = mocks.mock;