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
			window.location = 'http://localhost:8080/GroupProject2016/homeTabPages/home.html';
			
			return false;
		}		
		
	})	
	
	if(counter == 0){
		$("#errorMessage").css("display", "inline");
	}
	
	counter=0;	
	return false;
};






