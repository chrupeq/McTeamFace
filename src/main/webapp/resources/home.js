

var rootUrl="http://localhost:8080/GroupProject2016/rest/users";

$(document).on("click", "#formButton", function(){
	console.log("addUser");
	addUser();
});

window.onload = function () {
	alert("some buzz says Ruaidhri!!");
}

var addUser = function(){
	console.log("addUser");
	
	$.ajax({
		type:"POST",
		contentType: 'application/json',
		url:rootUrl,
		dataType: "json",
		data: formToJSON(),
		success: function(data) {
			
			alert("user created sucessfully");
			
			
			
		}
	});
};


var formToJSON =function() {
	return JSON.stringify({
		"firstname": $('#firstName').val(),
		"lastname": $('#surname').val(),
		"username": $('#usernameFormInput').val(),
		"password": $('#passwordFormInput').val(),
		"job_title": $('#jobTitleSelectId').val(),
		
				
	});
		
};





