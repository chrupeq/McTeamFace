//javascript document

var rootUrl="http://localhost:8080/GroupProject2016/rest/users";
var jobTitle = "";
$(document).ready(function() {
	displayError();
	$("#queryTabClick").hide();
	$("#contact").hide();
	$("#about").hide();
	$("#importDatasetTab").hide();
	$("#formTab").hide();
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
			jobTitle = detail.job_title;
			document.cookie = "jobTitle=" + detail.job_title + "";
			window.location = 'http://localhost:8080/GroupProject2016/home.html';
			
		//	userAccessControl(detail.job_title);
			console.log("here");
			
		}		
		
	})	
	
	if(counter == 0){
		$("#errorMessage").css("display", "inline");
	}
	
	counter=0;	
	return false;
};

var updateGlobal = function(jobDetail){
	console.log("variable: " + jobDetail);
	jobTitle = jobDetail;
};


