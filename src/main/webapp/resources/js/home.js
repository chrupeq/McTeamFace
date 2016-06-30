var rootUrl2 = "http://localhost:8080/GroupProject2016/rest/users";

$(document).on("click", 'button.clickToAdd', function() {

	$("#myModalEdit").modal('show');
	findById(this.id);
});

var loadDataTable = function(data) {
	var table = $('#carTable')
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
									data : "username"
								},

								{
									data : "password"
								},

								{
									data : ""
								},

								{
									data : "id",
									render : function(data) {
										return '<button type="button" id="'
												+ data
												+ '" class="btn btn-primary btn-sm moreInfoClicks">More Information</button>';
									}
								},

								{
									data : "id",
									render : function(data) {
										return '<button type="button" id="'
												+ data
												+ '" class = "btn btn-default btn-sm buttonTrigger">Edit</button>';
									}

								},

						],

					});

};

$(document).ready(function() {
	displayErrors();

	$(document).on("click", "#formButton", function() {
		var password = $("#passwordFormInput").val();
		var reenterPassword = $("#reenterPasswordFormInput").val();

		if (password == reenterPassword) {
			getDatabaseDetails();
		} else {
			$("#passwordErrorMessage").css("display", "inline");
		}

		return false;
	});

});

/*Login and Logout functions*/
var displayErrors = function() {
	$("#passwordErrorMessage").css('display', 'none');
	$("#userExistsEerrorMessage").css('display', 'none');
	return false;
}

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

var formToJSON = function() {

	return JSON.stringify({

		"firstname" : $('#firstName').val(),
		"lastname" : $('#lastName').val(),
		"username" : $('#usernameFormInput').val(),
		"password" : $('#passwordFormInput').val(),
		"job_title" : $('#jobTitleSelectId').val()

	});

};
