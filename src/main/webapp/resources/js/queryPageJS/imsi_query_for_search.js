$(document).ready(function() {
	findAllImsis();
	$(".js-example-basic-multiple").select2();
	});

var rootUrl = "http://localhost:8080/GroupProject2016/rest/unique_model";
var findAllImsis = function() {
	$.ajax({
		type : 'GET',
		url : rootUrl,
		dataType : "json",
		success : loadSearchParams
	});
};

var htmlOptionsForSearch = "";
var loadSearchParams = function(data){
	
	$.each(data, function(index, element) {
		$('#selectByModel').append($("<option></option>")
		                    .attr("value",element[0] + ' ' + element[1])
		                    .text(element[0] + ' ' + element[1]));
		
	});
}

