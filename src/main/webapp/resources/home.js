

var rootUrl="http://localhost:8080/GroupProject2016/rest/users";

$(document).ready(function() {
	displayError();
});

var displayError = function() {
	$("#errorMessage").css('display', 'none');
	return false;
}




$(document).on("click", "#formButton", function(){
	var password = $("#passwordFormInput").val();
	var reenterPassword = $("#reenterPasswordFormInput").val();
	
	if(password === reenterPassword){
		addUser();
	}else {
		$("#errorMessage").css("display", "inline");
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
			$("#Id").val(data.id);
			location.reload();
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert("addWine error: " + textStatus);
		}
	});
};


var formToJSON =function() {
	
	var id = $('#Id').val();
	alert(id);
	return JSON.stringify({
		"id": $('#Id').val(),
		"firstname": $('#firstName').val(),
		"lastname": $('#lastName').val(),		
		"username": $('#usernameFormInput').val(),
		"password": $('#passwordFormInput').val(),
		"job_title": $('#jobTitleSelectId').val()
		
		
				
	});
		
};





