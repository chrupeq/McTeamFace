//javascript document

var rootUrl="http://localhost:8080/GroupProject2016/rest/users";



$(document).ready(function() {
	alert("Hello");

	
	
	$.ajax ({
		type: 'GET',
		url: rootUrl,
		dataType: "json",
		success: loginAuthentication,
		error: function() {
			alert('error loading details');
		}
	})
});

function loginAuthentication(details) {
	var username = $('#username').value;
	var password = $('#password').value;
	
	
	$.each(details, function(i, detail){
		alert(detail.username + " and " + detail.password);
		if(username==detail.username) {
			if(password==detail.password){
				alert("Login success");
			}
		
			
			
			
		}else {
			alert("FAIL");
		}
		
		
		
	})	
}









