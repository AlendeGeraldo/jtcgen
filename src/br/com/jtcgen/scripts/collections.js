const collections = {
	list : function (mockMethods, values) {
		collections.initImports();
		var buffer = "\n";
		var bufferList = "";
		var fullClazz = str.split('@')[0];
		var clazz = str.split('@')[0].replace(/^([a-zA-Z0-9]+\.)+/g, "");
		var method = str.split('@')[1];
		var i = 0;
		
		eval('Java.type("br.com.jtcgen.helpers.ImportManager").addImport(Java.type("'+ fullClazz +'").class);');
		
		bufferList = "\t\t" + "List<"+clazz+"> list = new ArrayList<"+clazz+">();\n\n"; 
		
		values.forEach(function(value) {
			if(double == "double") {
				value = Number(value);
				if(/^[0-9]+$/.test(value))
					value = value.toFixed(1)
			}
			
			buffer += "\t\t" + clazz + " " + clazz.toLowerCase() + ((i == 0) ? "" : i) +
					  " = mock( " + clazz + ".class );\n";
		    buffer += "\t\t" + "when("+clazz.toLowerCase()+ ((i == 0) ? "" : i) +"."+method+").thenReturn("+value+");\n\n";
			
			bufferList += "\t\t" + "list.add("+clazz.toLowerCase() + ((i == 0) ? "" : i) +");\n";
			
			i++;
		});
		
		buffer += "\n";
		bufferList += "\n";
		return {localVar: "list", str: ( buffer + bufferList )};
	},
	initImports : function() {
		ImportManager.addImport(eval("return Java.type('java.util.List').class"));
		ImportManager.addImport(eval("return Java.type('java.util.ArrayList').class"));
	}
};

var mockList = collections.list;