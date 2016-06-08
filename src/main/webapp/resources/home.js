

//var rootUrl="http://localhost:8080/GroupProject2016/rest/users";

$(document).ready(function() {

});


$(document).on("click", "homeTab", function(){
	alert("Home");
	window.location = 'http://localhost:8080/GroupProject2016/home.html';
	
	
});

$(document).on("click", "dataSet", function(){
	alert("Data");
	window.location = 'http://localhost:8080/GroupProject2016/dataset.html';
	
	
});



$(document).on("click", "aboutUs", function(){
	alert("About");
	window.location = 'http://localhost:8080/GroupProject2016/aboutUs.html';
	

});


$(document).on("click", "contactUs", function(){
	alert("Contact");
	window.location = 'http://localhost:8080/GroupProject2016/contactUs.html';
	

});

