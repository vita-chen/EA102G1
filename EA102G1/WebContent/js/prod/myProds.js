function init() {
	var path = document.getElementById("path").innerText;
	console.log(path);

	var checks = document.getElementsByClassName("check");
	var crosses = document.getElementsByClassName("cross");
	var ups = document.getElementsByClassName("up");
	var downs = document.getElementsByClassName("down");
	var upDowns = document.getElementsByClassName("upDown");
	var myStatus = document.getElementsByClassName("myStatus");
	console.log(myStatus);
	for (let i = 0 ; i < upDowns.length; i++) {
		upDowns[i].addEventListener("click", function(e){
			var up = this.children[0];
			var down = this.children[1];
			if(this.children[0].getAttribute("display") === "inline"){
				up.setAttribute("display", "none");
				checks[i].setAttribute("display", "none");
				down.setAttribute("display", "inline");
				crosses[i].setAttribute("display", "inline");
			} else {
				down.setAttribute("display", "none");
				crosses[i].setAttribute("display", "none");
				up.setAttribute("display", "inline");
				checks[i].setAttribute("display", "inline");
			}
			var prod_no = this.closest(".myParent").children[0].textContent;
			ajaxToggleStatus(prod_no);
		});
	}

	function ajaxToggleStatus(prod_no) {
		var xhr = new XMLHttpRequest();
		xhr.onload = function() {
			if(xhr.status === 200){
				
			}
		}
		var url=path+"/prod/prod.do?action=toggleStatus&prod_no="+prod_no;
		xhr.open("get", url, true);
		xhr.send(null);
	}

	for (let i = 0; i < myStatus.length; i++) {
		if (myStatus[i].textContent === "0"){
			ups[i].setAttribute("display", "inline");
			checks[i].setAttribute("display", "none");
			downs[i].setAttribute("display", "none");
			crosses[i].setAttribute("display", "inline");
		} else {
			downs[i].setAttribute("display", "inline");
			crosses[i].setAttribute("display", "none");
			ups[i].setAttribute("display", "none");
			checks[i].setAttribute("display", "inline");
		}
	}
}
window.addEventListener("load", init);