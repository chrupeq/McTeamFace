var rootUrlUsers="http://localhost:8080/GroupProject2016/rest/users";

$(document).ready(function() {
	displayError();
	$("#button").on("click", function(){
		getDetailsFromUser();
		return false;
	$('#loginButton').on("click", function(){
		getDetailsFromUser();
		return false;
	});
	$('#queryTabClick').on('click', function(){
		checkLevel();
	});
});

	var displayError = function(){
		$("#errorMessage"), 'display', 'none');
		return false;
	}
	
var verifyAdmin = function() {
	console.log("verify admin");
	
	var loggedInUsername = $('#username').val();
	
	if(loggedInUsername == "SystemAdmin1") {
		console.log("Admin logged in");
	} else {
		console.log("Username not admin");
		
	}
	
	return false;
}


var getDetailsFromUser = function() {
	var username = $('#username').val();
	var password = $('#password').val();
	$.ajax ({
		type: 'POST',
		url: rootUrlUsers,
		data: JSON.stringify({ username: JSON.stringify(username), password: JSON.stringify(password) }),
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
		success: function(data, textStatus, xhr){

			alert(data);
			document.cookie = 'sessionId="'+ data +'"';
			alert(document.cookie);
			alert(data);


			var d = new Date();
			d.setTime(d.getTime() + (1*24*60*60*1000));
			
			document.cookie = "username="+username+";"
					document.cookie = "expires="+ d.toUTCString() +";";
			document.cookie =  "sessionId="+xhr.getResponseHeader('sessionId')+";";

			if(data == 200){
				window.location.href = "http://localhost:8080/GroupProject2016/home.html";
				alert(getCookie("sessionId"));
			}else{
				loginAuthentication();
			}
				}
	});
	return false;
}


function loginAuthentication() {

			$("#errorMessage").css("display", "inline");
			return false;
	};

	function getCookie(name) {

		  var start = document.cookie.indexOf(name + "=");

		  var len = start + name.length + 1;

		  if ((!start) && (name != document.cookie.substring(0, name.length))) {

		    return null;

		  }

		  if (start == -1) return null;

		  var end = document.cookie.indexOf(';', len);

		  if (end == -1) end = document.cookie.length;

		  return unescape(document.cookie.substring(len, end));

		}

var checkLevel = function(){
	if(getCookie("username") == "testusername"){
		alert("correct username!");
	}
	
	if(getCookie("username") == "BOOZERNAME"){
		alert("ha, no.");
	}
}