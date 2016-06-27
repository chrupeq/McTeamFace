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

var rootUrlSelectDates = "http://localhost:8080/GroupProject2016/rest/imsi/get_imsis_between_dates";
var findAllImsisForDates = function(date1, date2) {
	$.ajax({
		type : 'GET',
		url : rootUrlSelectDates + "?date1=" + date1 + "&date2=" + date2,
		dataType : "json",
		success : loadImsiTable
	});
};
