var size = screen.height;
var navAppears = false;

$.material.init();

document.addEventListener("scroll", (event) => {
	if(document.body.scrollTop > 50 && !navAppears) {
		navAppears = true;
		$(".navbar").removeClass("fade animated slideOutUp slideInDown");
		$(".navbar").addClass("animated slideInDown");
	} else if (document.body.scrollTop <= 50 && navAppears) {
		navAppears = false;
		$(".navbar").removeClass("fade animated slideOutUp slideInDown");
		$(".navbar").addClass("animated slideOutUp")
	}
});

function scrollToElement(element){
	$('html, body').animate({
        scrollTop: element.offset().top
    }, 500);
}

$("ul.nav li").on("click", function (event) {
	$("ul.nav li").each(function() {
		$(this).removeClass("active");
	});

	$(this).addClass("active")
})

hljs.initHighlightingOnLoad();