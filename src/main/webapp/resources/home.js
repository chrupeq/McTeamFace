

var rootUrl="http://localhost:8080/GroupProject2016/rest/users";

$(document).ready(function() {
	displayErrors();
	
	
});

var displayErrors = function() {
	$("#passwordErrorMessage").css('display', 'none');
	$("#userExistsEerrorMessage").css('display', 'none');
	return false;
}


$(document).on("click", "#formButton", function(){
	var password = $("#passwordFormInput").val();
	var reenterPassword = $("#reenterPasswordFormInput").val();
	
	
	if(password == reenterPassword){
		getDatabaseDetails();
	}else {
		$("#passwordErrorMessage").css("display", "inline");
	}
	
	
	return false;
});


var addUser = function(){
	console.log("addUser");
	
	$.ajax({
		type:"POST",
		contentType: 'application/json',
		url:rootUrl,
		
		data: formToJSON(),
		success: function(data, textStatus, jqXHR) {
			alert("USER FUCKTY created sucessfully");
			
			location.reload();
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert("addUser error: " + textStatus);
		}
	});
};


var getDatabaseDetails = function() {
	console.log("getting details");
	
	$.ajax ({
		type: 'POST',
		url: rootUrl,
		dataType: "json",
		success: function(details){
			checkUsernameExists(details);
				}
	});
	return false;
}

function checkUsernameExists(details) {
	console.log("Checking username");
	var username = $("#usernameFormInput").val();
	var counter = 0;
	
	$.each(details, function(i, detail){
		
		if(username==detail.username) {
			$("#userExistsEerrorMessage").css("display", "inline");
			
			counter++;
		
			
			
			return false;
		}		
		
	})	
	
	if(counter == 0){
		addUser();
	}
	
	counter=0;	
	return false;
};


var formToJSON =function() {
	
	

	return JSON.stringify({
		
		"firstname": $('#firstName').val(),
		"lastname": $('#lastName').val(),		
		"username": $('#usernameFormInput').val(),
		"password": $('#passwordFormInput').val(),
		"job_title": $('#jobTitleSelectId').val()
		
		
				
	});
		
};





