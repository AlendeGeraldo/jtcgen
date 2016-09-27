var regex = {
	removeFullClassName: function (str, replacement) {
		if(replacement == undefined) 
			replacement = "";
		
		return str.replace(/^([a-zA-Z0-9]+\.)+/g, replacement);
	} 
}