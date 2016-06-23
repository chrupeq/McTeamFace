$(document).ready(function() {
	findAllImsis();
	});

var rootUrl = "http://localhost:8080/GroupProject2016/rest/network_entities";
var findAllImsis = function() {
	$.ajax({
		type : 'GET',
		url : rootUrl + "/" + "base_data",
		dataType : "json",
		success : loadSearchParams
	});
};

var htmlOptionsForSearch = "";
var loadSearchParams = function(data){
	$.each(data, function(index, element) {
		$('#searchByImsiBox').append('<option data-subtext="Rep California">' + element.imsi + '</option>');
	});
/*	$('.selectpicker').selectpicker('refresh'); */
}

