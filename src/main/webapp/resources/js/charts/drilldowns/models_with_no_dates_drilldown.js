var drilldown1Url = 'http://localhost:8080/GroupProject2016/rest/drilldown/drill_down?';

var getModelInfoForDrilldown = function(eventId, causeCode, tacNumber){
	$('#queryprogress').css('width', '0%');
 	$('#queryprogress').text('0%');
	$('#queryprogressouter').removeClass('animated fadeOutUp');
	$('#queryprogressouter').addClass('animated fadeInDown');
	$.ajax({
		type:'GET',
		url: drilldown1Url + 'eventId=' + eventId + '&causeCode=' + causeCode + '&tacNumber=' + tacNumber,
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
			//loadImsiEventIdCauseCodeTable(data);
			alert("hi");
			$('#queryprogressouter').mousemove(function(){
				$('#queryprogressouter').removeClass('animated fadeInDown');
				$('#queryprogressouter').addClass('animated fadeOutUp');
			});
		},
		error: $('#tableDiv').html('<h3 id="noDataMessage">no data to display for this query<h3>')
	});
}