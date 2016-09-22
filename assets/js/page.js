var size = screen.height;
var navAppears = false;

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