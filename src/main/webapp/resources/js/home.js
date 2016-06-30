var rootUrl2 = "http://localhost:8080/GroupProject2016/rest/users";
var jobTitle2 = "";
$(document).ready(function() {
	userAccessControl();
	displayErrors();
	jobTitle2 = jobTitle;
	findAll();

});


var userAccessControl = function(){
	var accessLevel = document.cookie.substring(9);
	if(accessLevel == "SA"){
		$("#importDatasetTab").show();
		$("#formTab").show();
		
	}else if(accessLevel == "NME"){
		$("#importDatasetTab").show();
		$("#queryTabClick").show();
		$("#contact").show();
		$("#about").show();
		
	}else if(accessLevel == "SE"){
		
		$("#queryTabClick").show();
		$("#contact").show();
		$("#about").show();
		
	}else if(accessLevel == "CSR"){
		
		$("#queryTabClick").show();
		$("#contact").show();
		$("#about").show();
	}
};

var showTabs = function(){
	$("#importDatasetTab").show();
	$("#formTab").show();
}


$(document).on("click", 'button.clickToAdd', function() {
	$("#myModalAdd").modal('show');
});

$(document).on("click", 'button.editButton', function() {
	var userId = this.id;
	findById(userId);
	$("#userExistsErrorMessageEdit").css('display', 'none');
	$("#myModalEdit").modal('show');
	
	$(document).on("click", "#formButtonEdit", function() {
		$("#userExistsErrorMessageEdit").hide();
			getDatabaseDetailsEdit(userId);

		return false;
	});
	
});

$(document).on("click", 'button.deleteButton', function() {
	deleteUser(this.id);
});

$(document).on("click", 'formButtonClose', function() {
	$("#myModalAdd").modal('hide');
});

$(document).on("click", 'formButtonCloseEdit', function() {
	$("#myModalEdit").modal('hide');
});


//var findAll = function() {
//	$.ajax({
//		type : 'GET',
//		url : rootUrl2,
//		dataType : 'json',
//		success : loadDataTable
//	});
//};

var findById = function(id){
	console.log('findById: ' + id);
	$.ajax({
		type: 'GET',
		url: rootUrl2 + '/' + id,
		dataType: "json",
		success: renderEditDetails
		});
};

var addUser = function() {
	console.log("addUser");

	$.ajax({
		type : "POST",
		contentType : 'application/json',
		url : rootUrl2,

		data : formToJSON(),
		success : function(data, textStatus, jqXHR) {

			location.reload();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("addUser error: " + textStatus);
		}
	});
};


var editUser = function(id) {
	console.log("editUser");

	$.ajax({
		type : "PUT",
		contentType : 'application/json',
		url : rootUrl2 + '/' + id,

		data : formToJSONEdit(),
		success : function(data, textStatus, jqXHR) {

			findAll();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("editUser error: " + textStatus);
		}
	});
};
	
var deleteUser = function(id) {
	console.log("deleteUser");
	
	$.ajax({
		type: "DELETE",
		url: rootUrl2 + "/" + id,
		success: function(data, textStatus, jqXHR){
			findAll();

		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert("deleteUser Error");
		}
			
	});
};

var renderEditDetails = function(data){
	$('#firstNameEdit').val(data.firstname);
	$('#lastNameEdit').val(data.lastname);
	$('#usernameFormInputEdit').val(data.username);
	$('#passwordFormInputEdit').val(data.password);
	$('#jobTitleSelectEdit').val(data.job_title);
}

var loadDataTable = function(data) {
	var table = $('#userInfoTable').DataTable(
	
					{
						destroy: true,
						data : data,

						columns : [

								{
									data : "id"
								},

								{
									data : "firstname"
								},
								
								{
									data : "lastname"
								},

								{
									data : "username"
								},

								{
									data : "password"
								},

								{
									data : "job_title"
								},

								{
									data : "id",
									render : function(data) {
										return '<button type="button" id="'
												+ data
												+ '" class="btn btn-primary btn-sm editButton">Edit</button>';
									}
								},

								{
									data : "id",
									render : function(data) {
										return '<button type="button" id="'
												+ data
												+ '" class = "btn btn-danger btn-sm deleteButton">Delete</button>';
									}

								},

						],

					});

};





$(document).on("click", "#formButton", function() {
	$("#userExistsEerrorMessage").hide();
	$("#passwordErrorMessage").hide();
	var password = $("#passwordFormInput").val();
	var reenterPassword = $("#reenterPasswordFormInput").val();

	if (password == reenterPassword) {
		getDatabaseDetails();
	} else {
		$("#passwordErrorMessage").css("display", "inline");
	}

	return false;
});


/*Login and Logout functions*/
var displayErrors = function() {
	$("#passwordErrorMessage").css('display', 'none');
	$("#userExistsEerrorMessage").css('display', 'none');
	return false;
}

var getDatabaseDetailsEdit = function(userId) {
	console.log("getting details");

	$.ajax({
		type : 'POST',
		url : rootUrl2,
		dataType : "json",
		success : function(details) {
			checkUsernameExistsEdit(details, userId);
		}
	});
	return false;
};



function checkUsernameExistsEdit(details, userId) {
	console.log("Checking username");
	var username = $("#usernameFormInputEdit").val();
	var counter = 0;

	
	$.each(details, function(i, detail) {

		console.log(userId);

		if (userId != detail.id) {

			if (username == detail.username) {
				$("#userExistsErrorMessageEdit").css("display", "inline");

				counter++;

				return false;
			}
		}
	})

	if (counter == 0) {

		editUser(userId);
	}

	counter = 0;
	return false;
};



var getDatabaseDetails = function() {
	console.log("getting details");

	$.ajax({
		type : 'POST',
		url : rootUrl2,
		dataType : "json",
		success : function(details) {
			checkUsernameExists(details);
		}
	});
	return false;
}

function checkUsernameExists(details) {
	console.log("Checking username");
	var username = $("#usernameFormInput").val();
	var counter = 0;

	$.each(details, function(i, detail) {

		if (username == detail.username) {
			$("#userExistsEerrorMessage").css("display", "inline");

			counter++;

			return false;
		}
	})

	if (counter == 0) {
		addUser();
	}

	counter = 0;
	return false;
};

$(document).on("click", "#logoutBtn", function() {
	document.cookie = "jobTitle=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
	window.location = 'http://localhost:8080/GroupProject2016/welcome.html';

	$('#username').val("");
	$('#password').val("");
});

/*form functions*/


var formToJSON = function() {

	return JSON.stringify({

		"firstname" : $('#firstName').val(),
		"lastname" : $('#lastName').val(),
		"username" : $('#usernameFormInput').val(),
		"password" : $('#passwordFormInput').val(),
		"job_title" : $('#jobTitleSelect').val()

	});

};

var formToJSONEdit = function() {
	console.log("editStringify");
	
	return JSON.stringify({

		"firstname" : $('#firstNameEdit').val(),
		"lastname" : $('#lastNameEdit').val(),
		"username" : $('#usernameFormInputEdit').val(),
		"password" : $('#passwordFormInputEdit').val(),
		"job_title" : $('#jobTitleSelectEdit').val()

	});

};
