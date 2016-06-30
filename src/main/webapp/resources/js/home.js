var rootUrl2 = "http://localhost:8080/GroupProject2016/rest/users";

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
	
});

var findAll = function() {
	$.ajax({
		type : 'GET',
		url : rootUrl2,
		dataType : 'json',
		success : loadDataTable
	});
};

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


var editUser = function() {
	console.log("editUser");

	$.ajax({
		type : "PUT",
		contentType : 'application/json',
		url : rootUrl2 + '/' + id,

		data : formToJSONEdit(),
		success : function(data, textStatus, jqXHR) {

			loadDataTable;
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("editUser error: " + textStatus);
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
	var table = $('#userInfoTable')
			.DataTable(
					{

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

$(document).ready(function() {
	displayErrors();
	findAll();

});

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
		alert("here");
		editUser();
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
	window.location = 'http://localhost:8080/GroupProject2016/welcome.html';

	$('#username').val("");
	$('#password').val("");
});

/*form functions*/



var editUser = function() {
	console.log("editUser");
	
	$.ajax({
		type: "PUT",
		contentType: "application/json",
		url: rootUrl + id,
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR) {
			alert("Car updated successfully");
			location.reload();
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert("UpdateCar error: " + textStatus);
		}
	
	});
}
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
