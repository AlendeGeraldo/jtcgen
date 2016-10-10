const TextEditor = Java.type("br.com.jtcgen.helpers.TextEditor");
const ImportManager = Java.type("br.com.jtcgen.helpers.ImportManager");

const helper = {
	getScene: function () {
		return Java.type("br.com.jtcgen.scripts.Scene");
	},
	import: function (className) {
		ImportManager.addImport(eval("return Java.type('" + className + "').class"));
	}
}