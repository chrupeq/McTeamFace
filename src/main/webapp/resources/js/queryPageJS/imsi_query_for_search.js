$(document).ready(function() {
	findAllUniqueModels();
	$(".js-example-basic-single").select2();
	});

var rootUrlSelect = "http://localhost:8080/GroupProject2016/rest/unique_model";
var findAllUniqueModels = function() {
	$.ajax({
		type : 'GET',
		url : rootUrlSelect,
		dataType : "json",
		success : loadSearchParams,
		error: $('#tableDiv').html('<h3 id="noDataMessage">no data to display for this query<h3>')
	});
};

var loadSearchParams = function(data){
	$.each(data, function(index, element) {
		$('#selectByModel').append($("<option></option>")
		                    .attr("value", element.tac)
		                    .text(element.manufacturer + ' ' + element.marketing_name));
		
	});
}

var rootUrlSelectDates = "http://localhost:8080/GroupProject2016/rest/imsi/get_imsis_between_dates";
var findAllImsisForDates = function(date1, date2) {
	 $('#queryprogress').css('width', '0%');
	 	$('#queryprogress').text('0%');
	$('#queryprogressouter').removeClass('animated fadeOutUp');
	$('#queryprogressouter').addClass('animated fadeInDown');
	$.ajax({
		type : 'GET',
		url : rootUrlSelectDates + "?date1=" + date1 + "&date2=" + date2,
		dataType : "json",
		progress: function(e) {
	        if(e.lengthComputable) {
	            var pct = (e.loaded / e.total) * 100;
	            $('#queryprogress').css('width', pct.toPrecision(3) + '%');
	        	$('#queryprogress').text(pct.toPrecision(3) + '%');
	        }
	        else {
	            console.warn('Content Length not reported!');
	        }
	    },
		success : function(data){
			imsiWithDatesQuery();
			loadImsiTable(data);
			$('#queryprogressouter').mousemove(function(){
				$('#queryprogressouter').removeClass('animated fadeInDown');
				$('#queryprogressouter').addClass('animated fadeOutUp');
			});
			
			
		},
		error:function(){
			$('#tableDiv').html('<h3 id="noDataMessage">no data to display for this query<h3>')
		}
	});
};

var imsiQuery = function(dateThree, dateFour){
	 $('#queryprogress').css('width', '0%');
 	$('#queryprogress').text('0%');
	$('#queryprogressouter').removeClass('animated fadeOutUp');
	$('#queryprogressouter').addClass('animated fadeInDown');
		console.log("dates: " + dateThree + " " + dateFour);
		$.ajax({
			type:'GET',
			url: imsiStatsURL + '?dateOne=' + dateThree + '&dateTwo=' + dateFour,
			dataType:'json',
			progress: function(e) {
		        if(e.lengthComputable) {
		            var pct = (e.loaded / e.total) * 100;
		            $('#queryprogress').css('width', pct.toPrecision(3) + '%');
		        	$('#queryprogress').text(pct.toPrecision(3) + '%');
		        }
		        else {
		            console.warn('Content Length not reported!');
		        }
		    },
			success: function(data){
				buildIMSIStatsTable(data);
				if(getTheCookieYouNeed('job_title') == 'NME'){
					failuresLineChart(data);
				}
				$('#queryprogressouter').mousemove(function(){
					$('#queryprogressouter').removeClass('animated fadeInDown');
					$('#queryprogressouter').addClass('animated fadeOutUp');
				});
			},
			error: $('#tableDiv').html('<h3 id="noDataMessage">no data to display for this query<h3>')
		});
}
