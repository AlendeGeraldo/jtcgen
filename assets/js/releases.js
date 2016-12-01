jQuery(document).ready(function() {
	jQuery.ajax({
		url: "https://api.github.com/repos/rgoncalves94/jtcgen/releases/latest",
		dataType: "json",
		type: "GET"
	}).done(function(git) {
		var btnText = "Download " + git.tag_name;
		var linkRelease = "/jtcgen/jars/jtcgen-" + git.tag_name + ".jar";
		var anchorDownload = jQuery("#download-latest a");
		anchorDownload.attr("href", linkRelease);
		anchorDownload.html(btnText);
	});
});