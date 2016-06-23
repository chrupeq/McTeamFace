$(document).ready(function() {
	findAll();
	});

var rootUrl = "http://localhost:8080/GroupProject2016/rest/network_entities";
var findAll = function() {
	$.ajax({
		type : 'GET',
		url : rootUrl + "/" + "base_data",
		dataType : "json",
		success : loadTable
	});
};