var TextEditor = Java.type("br.com.jtcgen.helpers.TextEditor");
var ImportManager = Java.type("br.com.jtcgen.helpers.ImportManager");
var NashornBag = Java.type("br.com.jtcgen.helpers.NashornBag");

var helper = {
	getScene: function () {
		return Java.type("br.com.jtcgen.scripts.Scene");
	},
	import: function (className) {
		ImportManager.addImport(eval("return Java.type('" + className + "').class"));
	},
	parseDouble: function(value) {
		return (regex.isInteger(value)) ? value.toFixed(1) : value;
	},
	isDiffType: function(value, typeExpected) {
		return Object.prototype.toString.call(value) != typeExpected;
	} 
};

var exception = {
	invalidParam: function (message) {
		var exception = Java.type("br.com.jtcgen.exceptions.InvalidParamDeclarationException");
		
		return new exception(message);
	}	
};