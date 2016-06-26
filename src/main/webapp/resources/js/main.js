var rootUrlUsers="http://localhost:8080/GroupProject2016/rest/users";

$(document).ready(function() {
	displayError();
	$("#button").on("click", function(){
		getDetailsFromUser();
		return false;
	});
});

	var displayError = function(){
		$("#errorMessage").css('display', 'none');
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

			if(data == 200){
				window.location.href = "http://localhost:8080/GroupProject2016/home.html";
				
			}else{
				loginAuthentication();
			}
				}
	});
	return false;
}


function loginAuthentication() {

//			var date = new Date();
//			var expiryDate = date.getTime() + diff*60000;
//			
//			verifyAdmin();
//			document.cookie = "username="+ username +"; expires="+ expiryDate.toUTCString();
//			window.location = 'http://localhost:8080/GroupProject2016/home.html';
			$("#errorMessage").css("display", "inline");
			return false;
	};

	






