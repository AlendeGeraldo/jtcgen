var regex = {
	removeFullClassName: function (str, replacement) {
		if(replacement == undefined) 
			replacement = "";
		
		return str.replace(/^([a-zA-Z0-9]+\.)+/g, replacement);
	},
	replaces: function (str, obj) {
		str = str.replaces(/\\{\\{shortClazz\\}\\}/g, obj.shortClazz);
		str = str.replaces(/\\{\\{shortClazzLower\\}\\}/g, obj.shortClazzLower);
		str = str.replaces(/\\{\\{method\\}\\}/g, obj.method);
		str = str.replaces(/\\{\\{returns\\}\\}/g, obj.returns);

		return str;
	},
	isMockString: function (str)  {
		return /^[a-zA-Z0-9]@[a-zA-Z0-9]\(\)$/g.test(str);
	}
};