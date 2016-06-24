$(document).ready(function() {
	findAllUniqueModels();
	$(".js-example-basic-multiple").select2();
	});

var rootUrlSelect = "http://localhost:8080/GroupProject2016/rest/unique_model";
var findAllUniqueModels = function() {
	$.ajax({
		type : 'GET',
		url : rootUrlSelect,
		dataType : "json",
		success : loadSearchParams
	});
};

var loadSearchParams = function(data){
	$.each(data, function(index, element) {
		$('#selectByModel').append($("<option></option>")
		                    .attr("value",element[2])
		                    .text(element[0] + ' ' + element[1]));
		
	});
}

