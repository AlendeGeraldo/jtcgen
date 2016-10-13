var regex = {
	removeFullClassName: function (str, replacement) {
		if(replacement == undefined) 
			replacement = "";
		
		return str.replace(/^([a-zA-Z0-9]+\.)+/g, replacement);
	},
	replaces: function (str, obj) {
		str = str.replace(/\{\{shortClazz\}\}/g, obj.shortClazz);
		str = str.replace(/\{\{shortClazzLower\}\}/g, obj.shortClazzLower);
		str = str.replace(/\{\{method\\}\}/g, obj.method);
		str = str.replace(/\{\{returns\}\}/g, obj.returns);
		str = str.replace(/\{\{params\}\}/g, obj.params);

		return str;
	},
	isMockString: function (str)  {
		return /^[a-zA-Z0-9]@[a-zA-Z0-9]\(\)$/g.test(str);
	}
};