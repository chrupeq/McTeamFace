var rootUrl="http://localhost:8080/GroupProject2016/rest/users";
var jobTitle = "";
$(document).ready(function() {
alert("here");
	var loggedIn = checkCookieStatus();
	
	if(loggedIn != ""){
		$('#logoutBtn').removeClass('hiddenbycostanza');
		userAccessControl();
	}else{
		$('#welcometab').removeClass('hiddenbycostanza');
		$('#home').removeClass('hiddenbycostanza');
	}
	$('#loginButton').on("click", function(){
		getDetailsFromUser();
		
		return false;
	});
});

	var displayError = function(){
		$("#errorMessage").removeClass('hiddenbycostanza');
	}
	
	var hideError = function(){
		$("#errorMessage").addClass('hiddenbycostanza');
	}
	


var getDetailsFromUser = function() {

	$.ajax ({
		type: 'GET',
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
			$('#logoutBtn').removeClass('hiddenbycostanza');
			jobTitle = detail.job_title;
			document.cookie = 'jobTitle=' + detail.job_title + ';';
			document.cookie = 'username=' + detail.username + ';';
			document.cookie = 'name=' + detail.firstname + ';';
			document.cookie = 'id=' + detail.id + ';';
			document.cookie = 'last_login=' + detail.last_login;
			revealCustomHeader();
			$('#welcometab').addClass('hiddenbycostanza');
			$.ajax ({
				type: 'POST',
				url: rootUrl + '/logintime/' + new Date() + '/' + getTheCookieYouNeed('username'),
				dataType: "json",
				success: function(details){
						}
			});
			userAccessControl();
			getCustomQueryBar();
		}		
		
	})	
	
	if(counter == 0){
		displayError();
		return false;
	}
	
	counter=0;	
	return false;
};

var checkCookieStatus = function(){
	var accessLevel = getTheCookieYouNeed('job_title');
	return accessLevel;
}

var getTheCookieYouNeed = function(detail){
	
	var cookieArray = document.cookie.split(';');
	
	if(detail == 'job_title'){
		return cookieArray[0].substring(9);
	}
	
if(detail == 'username'){
	return cookieArray[1].substring(10);
	}

if(detail == 'name'){
	return cookieArray[2].substring(6);
}

if(detail == 'id'){
	return cookieArray[3].substring(4);
}

if(detail == 'last_login'){
	return cookieArray[4].substring(12,36);
}
}
