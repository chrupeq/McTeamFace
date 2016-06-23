$(document).ready(function() {
	findAllImsis();
	$(".js-example-basic-multiple").select2();
	});

var rootUrl = "http://localhost:8080/GroupProject2016/rest/unique_imsi";
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
		$('#selectByImsi').append($("<option></option>")
		                    .attr("value",element)
		                    .text(element));
		
	});
}

