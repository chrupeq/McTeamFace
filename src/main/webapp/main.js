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

var rootUrl="http://localhost:8080/GroupProject2016/rest/wines";
var findAll=function(){
	$.ajax({
		   type:'GET',
		   url: rootUrl,
		   dataType:"json",
		   success:renderList
		   });
};
