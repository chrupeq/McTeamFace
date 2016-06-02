//javascript document


window.onload = prepareLogin;

function prepareLogin() {
	alert("Load success")
	document.getElementById("button").onclick = signin;
}


var signin = function() {
	var username = document.getElementById("username").value;
	var password = document.getElementById("password").value;
	
	if(username == "Manager2016" && password == "password1") {
		alert("Login SUCCESS");
	}

}
