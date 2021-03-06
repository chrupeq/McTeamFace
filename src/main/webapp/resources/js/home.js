var rootUrl2 = "http://localhost:8080/GroupProject2016/rest/users";
var jobTitle2 = "";

var userAccessControl = function() {
	$('#home').addClass('hiddenbycostanza');
	var accessLevel = getTheCookieYouNeed('job_title');
	if (accessLevel == "SA") {
		injectNavBar('SA');
		$('#formTab').click();
		$('#queryBody').addClass('hiddenbycostanza');
		$('#wilkommen').addClass('hiddenbycostanza');
		$("#importtab").removeClass('hiddenbycostanza');
		$("#formtab").removeClass('hiddenbycostanza');
		$('#form').removeClass('hiddenbycostanza');
		$('#importDataset').removeClass('hiddenbycostanza');
		getCustomQueryBar();
		$('#sidebar-wrapper').height('35%');

	} else if (accessLevel == "NME") {
		injectNavBar('NME');
		
				$('#home').addClass('hiddenbycostanza');
		$("#querytab").removeClass('hiddenbycostanza');
		$('#queryBody').removeClass('hiddenbycostanza');
		$("#contacttab").removeClass('hiddenbycostanza');
		$("#abouttab").removeClass('hiddenbycostanza');
		$('#home').removeClass('active');
		$('#queryBody').addClass('active');
		$('#welcometab').addClass('hiddenbycostanza');
		$('#wilkommen').addClass('hiddenbycostanza');
		$('#queryList').removeClass('hiddenbycostanza');
		$("#querytab").removeClass('hiddenbycostanza');
		$('#querytab').addClass('active');
		$('#queryTabClick').click();
		getCustomQueryBar();
		$('#sidebar-wrapper').height('100%');
		

	} else if (accessLevel == "SE") {
		injectNavBar('SE');
		$('#home').addClass('hiddenbycostanza');
		$("#querytab").removeClass('hiddenbycostanza');
		$("#queryBody").removeClass('hiddenbycostanza');
		$("#contacttab").removeClass('hiddenbycostanza');
		$("#abouttab").removeClass('hiddenbycostanza');
		$('#queryTabClick').click();
		getCustomQueryBar();
		$('#sidebar-wrapper').height('57%');
		
	} else if (accessLevel == "CSR") {
		injectNavBar('CSR');
		$('#tab').css('padding-top: 125px; margin-left: 250px;');
		$('#queryList').css('margin-top: -10.5%;');
		$('#queryTabClick').click();
		$('#home').addClass('hiddenbycostanza');
		$("#querytab").removeClass('hiddenbycostanza');
		$("#queryBody").removeClass('hiddenbycostanza');
		$("#contacttab").removeClass('hiddenbycostanza');
		$("#abouttab").removeClass('hiddenbycostanza');
		getCustomQueryBar();
		$('#sidebar-wrapper').height('40%');
	}
};

var showTabs = function() {
	$("#importtab").removeClass('hiddenbycostanza');
	$("#formtab").removeClass('hiddenbycostanza');
}

$(document).on("click", 'button.clickToAdd', function() {
	$('#myModalAdd').addClass('animated bounceIn');
	$("#myModalAdd").modal('show');
});

$(document).on("click", 'button.editButton', function() {
	var userId = this.id;
	findById(userId);
	$("#userExistsErrorMessageEdit").css('display', 'none');
	if(userId == 1){
		$("#usernameFormInputEdit").prop("disabled", "false");
		$("#jobTitleSelectEdit").prop("disabled", "false");
	}else{
		$("#usernameFormInputEdit").removeProp("disabled");
		$("#jobTitleSelectEdit").removeProp("disabled");
	}
//	$('#firstNameEdit').val(userId.firstname);
//	$('#lastNameEdit').val(userId.lastname);
//	$('#usernameFormInputEdit').val(user);
	$("#myModalEdit").modal('show');

	$(document).on("click", "#formButtonEdit", function() {
		$("#userExistsErrorMessageEdit").hide();
		getDatabaseDetailsEdit(userId);
		$('#myModalEdit').modal('hide');

		return false;
	});

});

$(document).on("click", 'button.deleteButton', function() {
	if(confirm("Are you sure you want to delete this user?")){
		if(this.id == getTheCookieYouNeed('id')){
			if(getTheCookieYouNeed('job_title') == 'SA'){
				alert("You can't delete system admin accounts, silly.");
			}else{
			deleteUser(this.id);
			$('#logoutBtn').click();
			}
		}else{
			deleteUser(this.id);
		}
	}else{
		
	}
});

$(document).on("click", 'formButtonClose', function() {
	$("#myModalAdd").modal('hide');
});

$(document).on("click", 'formButtonCloseEdit', function() {
	$("#myModalEdit").modal('hide');
});

var findAll = function() {
	$.ajax({
		type : 'GET',
		url : rootUrl2,
		dataType : 'json',
		success : loadDataTable
	});
};

var findById = function(id) {
	console.log('findById: ' + id);
	$.ajax({
		type : 'GET',
		url : rootUrl2 + '/' + id,
		dataType : "json",
		success : renderEditDetails,
		error: console.log("error")
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
		type : "DELETE",
		url : rootUrl2 + "/" + id,
		success : function(data, textStatus, jqXHR) {
			findAll();

		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("deleteUser Error");
		}

	});
};

var renderEditDetails = function(data) {
	alert("I is here.");
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
						destroy : true,
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
												+ '" class="btn btn-primary btn-sm editButton databutton">Edit</button>';
									}
								},

								{
									data : "id",
									render : function(data) {
										return '<button type="button" id="'
												+ data
												+ '" class = "btn btn-danger btn-sm deleteButton databutton">Delete</button>';
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
	injectNavBar('logged out');
//	$('#tab').css('padding-top: 0px; margin-left: 0px;');
//	$('#queryList').css('margin-top: -2.4%;');
	$('#customHeader').empty();
	document.cookie = 'jobTitle=; expires=Thu, 01 Jan 1970 00:00:00 UTC';
	document.cookie = 'username=; expires=Thu, 01 Jan 1970 00:00:00 UTC';
	document.cookie = 'name=; expires=Thu, 01 Jan 1970 00:00:00 UTC';
	document.cookie = 'id=; expires=Thu, 01 Jan 1970 00:00:00 UTC';
	document.cookie = 'last_login=; expires=Thu, 01 Jan 1970 00:00:00 UTC';
	$('#welcometab').removeClass('hiddenbycostanza');
	$('#home').removeClass('hiddenbycostanza');
	$("#importtab").addClass('hiddenbycostanza');
	$("#querytab").addClass('hiddenbycostanza');
	$("#contacttab").addClass('hiddenbycostanza');
	$("#abouttab").addClass('hiddenbycostanza');
	$("#formtab").addClass('hiddenbycostanza');
	$('#queryBody').addClass('hiddenbycostanza');
	$('#form').addClass('hiddenbycostanza');
	$('#importDataset').addClass('hiddenbycostanza');
	$('#customHeader').addClass('hiddenbycostanza');
	$('#username').val("");
	$('#password').val("");
	$('#logoutBtn').addClass('hiddenbycostanza');
	$('#wilkommen').click();
	hideError();
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

var revealCustomHeader = function() {
	var name = getTheCookieYouNeed('name');
	var theDate = new Date(); 
	var timeOfDay = "";
	  
	if ( theDate.getHours() < 12 )  
	{ 
	    timeOfDay = "Good morning, ";
	} 
	else
	if ( theDate.getHours() >= 12 && theDate.getHours() <= 17 ) 
	{ 
	    timeOfDay = "Good Afternoon, "; 
	} 
	else
	if ( theDate.getHours() > 17 && theDate.getHours() <= 24 ) 
	{ 
	    timeOfDay = "Good Evening, "; 
	} 
	else
	{ 
	    document.write("I'm not sure what time it is!"); 
	} 
	  
	if (name != "") {
		if(getTheCookieYouNeed('last_login') != 'null' && getTheCookieYouNeed('last_login') != '' && getTheCookieYouNeed('last_login') != ' '){
			$('#customHeader').html('<h4>' + timeOfDay + name +'!</h4><h4>Your last login was ' + getTheCookieYouNeed('last_login') + '</h4>');
		}else{
		$('#customHeader').html('<h2>' + timeOfDay + name +'!</h2>');
		}
		$('#customHeader').removeClass('hiddenbycostanza');
	}
}

$(document).ready(function() {
	displayErrors();
	jobTitle2 = jobTitle;
	findAll();
	revealCustomHeader();
	hideTable();
});