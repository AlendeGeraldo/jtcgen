var TextEditor = Java.type("br.com.jtcgen.helpers.TextEditor");
var ImportManager = Java.type("br.com.jtcgen.helpers.ImportManager");

var helper = {
	getScene: function () {
		return Java.type("br.com.jtcgen.scripts.Scene");
	},
	import: function (className) {
		ImportManager.addImport(eval("return Java.type('" + className + "').class"));
	}
}