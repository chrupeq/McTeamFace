//javascript document

var rootUrl="http://localhost:8080/GroupProject2016/rest/users";

$(document).ready(function() {
	displayError();
});

	var displayError = function(){
		$("#errorMessage").css('display', 'none');
		return false;
	}
	

$(document).on("click", "button", function(){
	getDetailsFromUser();
	
	return false;
});
	

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

	$.ajax ({
		type: 'POST',
		url: rootUrl,
		dataType: "json",
		success: function(details){
			loginAuthentication(details);
				}
	});
	return false;
}


function loginAuthentication(details) {
	var username = $('#username').val();
	var password = $('#password').val();
	var counter = 0;
	
	$.each(details, function(i, detail){
		
		if(username==detail.username && password==detail.password) {
			counter++;
			
			verifyAdmin();
			window.location = 'http://localhost:8080/GroupProject2016/home.html';
			
			return false;
		}		
		
	})	
	
	if(counter == 0){
		$("#errorMessage").css("display", "inline");
	}
	
	counter=0;	
	return false;
};






