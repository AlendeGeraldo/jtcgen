'use strict';

var collections = {
	mockList : function (str, values) {
		var buffer = "";
		var bufferList = "";
		var clazz = str.split('@')[0];
		var method = str.split('@')[1];
		var i = 0;
		
		bufferList = "\t\t\t" + "List<"+clazz+"> list = new ArrayList<"+clazz+">();\n\n"; 
		values.forEach(function(value) {
			buffer += "\t\t\t" + clazz + " " + clazz.toLowerCase() + ((i == 0) ? "" : i) +
					  " = mock( " + clazz + ".class );\n";
		    buffer += "\t\t\t" + "when("+clazz.toLowerCase()+ ((i == 0) ? "" : i) +"."+method+").thenReturns("+value+");\n\n";
			
			bufferList += "\t\t\t" + "list.add("+clazz.toLowerCase() + ((i == 0) ? "" : i) +");\n";
			
			i++;
		});
		buffer += "\n";
		bufferList += "\n";
		return {localVar: "list", str: ( buffer + bufferList )};
	}
};

var mockList = collections.mockList;