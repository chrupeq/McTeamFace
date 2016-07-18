$(document).ready(function(){
	$('#queryprogressouter').mousemove(function(){
		$('#queryprogressouter').removeClass('animated fadeInDown');
		$('#queryprogressouter').addClass('animated fadeOutUp');
	});
})

var getCustomQueryBar = function(){
	var customQueryBar = "";
	customQueryBar = '<div id="sidebar-wrapper">'
	     + '<ul class="sidebar-nav">'
	      +   '<li class="sidebar-brand">'
	       +      '<a href="#"`style="color: blue">'
	        +         getTheCookieYouNeed('name') + '\'s Queries'
	         +   '</a>'
	         + '</li>';

	if(getTheCookieYouNeed('job_title') == ""){
		$('#queryList').html(customQueryBar);
	}
     
	else if(getTheCookieYouNeed('job_title') == 'NME'){
		customQueryBar += '<a href="#" id="imsiWithDates" class="list-group-item">List of '
			+ 'IMSI failures for given dates</a> <a href="#" id="allFailuresForModel"'
			+ 'class="list-group-item">List of failure details for given Model</a>'
			+ '<a href="#" id="imsiStats" class="list-group-item">View IMSI'
		+ ' Statistics</a>'
		+ '<a href="#" id="allImsiFailuresBetweenDates"'
		+ 'class="list-group-item">Failure count for a given IMSI between two dates</a>'
		+ '<a href="#" id="imsiEventSelectIdCauseCode"'
		+ 'class="list-group-item">Unique Event ID and Cause Codes for a given IMSI</a>'
		+ '<a href="#" id="modelsWithDatesLink"'
			+ 'class="list-group-item">Failure count for given equipment model between two dates</a>'
			+ '<a href="#" id="topTenQuery"'
			+ 'class="list-group-item">Top 10 Market/Operator/Cell ID failures for given dates</a>'
			+ '<a href="#" id="imsiTopTen" class="list-group-item">Top ten IMSI failure counts for given dates</a>'
			+  '<a href="#" id="failureClassImsis" class="list-group-item">IMSI\'s for a given Failure Class</a>'
			+ '</ul>'
			+ '</div>';
		$('#queryList').html(customQueryBar);
		
	}else if(getTheCookieYouNeed('job_title') == 'CSR'){
		
		customQueryBar += '<a href="#" id="imsiEventSelectIdCauseCode"'
			+ 'class="list-group-item">Unique Event ID and Cause Codes for a given IMSI</a>'
			+ '<a href="#" id="allImsiFailuresBetweenDates"'
			+ 'class="list-group-item">Failure count for a given IMSI between two dates</a>'
			+ '</ul>'
			+ '</div>';
		$('#queryList').html(customQueryBar);
			
	}else if(getTheCookieYouNeed('job_title') == 'SE'){
		
		customQueryBar += '<a href="#" id="imsiWithDates" class="list-group-item">List of '
			+ 'IMSI failures for given dates</a>'
			+ '<a href="#" id="imsiEventSelectIdCauseCode"'
			+ 'class="list-group-item">Unique Event ID and Cause Codes for a given IMSI</a>'
			+ '<a href="#" id="allImsiFailuresBetweenDates"'
			+ 'class="list-group-item">Failure count for a given IMSI between two dates</a>'
			+ '<a href="#" id="modelsWithDatesLink"'
			+ 'class="list-group-item">Failure count for given equipment model between two dates</a>'
			+  '<a href="#" id="failureClassImsis" class="list-group-item">IMSI\'s for a given Failure Class</a>'
			+ '</ul>'
			+ '</div>';	
		$('#queryList').html(customQueryBar);
	}else if(getTheCookieYouNeed('job_title') == 'SA'){
		customQueryBar += '<a data-toggle="tab" href="#form" id="formTab">User Information</a>'
			+ '<a data-toggle="tab" href="#importDataset" id="importDatasetTab">Import Datasets</a>'
			+ '</ul>'
			+ '</div>';	
		alert(customQueryBar);
		$('#navList').html(customQueryBar);
		alert($('#navList').html());
	}

	
	$("#selectByModel").select2({dropdownCssClass : 'bigdrop'}); 
	$('#imsiWithDates').on("click", function(){
		$('#dateQueryModal').removeClass('animated bounceOut');
		$('#dateQueryModal').addClass('animated bounceIn');
	});
	
	$('#imsiStats').on("click", function(){
		$('.modalerrordiv').remove();
		$('#imsiStatsModal').removeClass('animated bounceOut');
		$('#imsiStatsModal').addClass('animated bounceIn');
		$('#imsiStatsModal').modal('show');
	});
	
	$('#allImsiFailuresBetweenDates').on('click', function(){
		$('.modalerrordiv').remove();
		$("#imsiEventIdCauseCode").select2();
		$('#singleImsiModal').modal('show');
	})
	
	$('#imsiWithDates').on("click", function(){
		$('.modalerrordiv').remove();
		$('#datetimepicker1').find('input').val("");
		$('#datetimepicker2').find('input').val("");
		 $('#datetimepicker1').datetimepicker({
             viewMode: 'years',
			 format: 'DD/MM/YYYY HH:mm'
         });
		 $('#datetimepicker2').datetimepicker({
			 viewMode: 'years',
			 format: 'DD/MM/YYYY HH:mm'
        });
		loadDateModal();
	});
	
	$('#imsiEventSelectIdCauseCode').on('click', function(){
		$('.modalerrordiv').remove();
		$.getJSON("http://localhost:8080/GroupProject2016/rest/imsi/get_unique", function(result) {
		    $.each(result, function(index, item) {
		    	$('#imsiEventIdCauseCode').append($("<option></option>")
	                    .attr("value",item)
	                    .text(item));
		    });
		})
			
		$('#imsisEventIdAndCauseCode').modal('show');
	
	})
	
	$('#allFailuresForModel').on('click', function(){
		$('.modalerrordiv').remove();
		$('#modelFailuresModal').modal('show');
		$('#modalTitle2').html('Models Query');
		$('#selectByModel').on('change', function() {
			$('#queryHeader').addClass('animated fadeOutUp');
			$('#queryHeader2').addClass('animated fadeInDown');
			  var value = $(this).val();
			  $('#modelFailuresModal').modal('hide');
			});
	})
	
	$('#dateSearch').on('click', function(){
		$('.modalerrordiv').remove();
		var firstDate = $("#datetimepicker1").find("input").val();
		var secondDate = $("#datetimepicker2").find("input").val();
		if(checkDates(firstDate, secondDate) == 'false'){
			
		}else{
			$('.modalerrordiv').remove();
		$('#dateQueryModal').modal('hide');
		$('#searchParams').html('You are searching for all IMSI failures between ' + firstDate + ' and ' + secondDate);
		$('#queryHeader').addClass('animated fadeOutUp');
		$('#queryHeader2').removeClass('animated fadeOutUp');
		$('#queryHeader2').addClass('animated fadeInDown');
		
		findAllImsisForDates(firstDate, secondDate);
		$('#querysTable').addClass('animated fadeOutUp');
		changeContainerCSS('imsiDates');
		}
	});
	
	$('#modelsWithDatesLink').on('click', function(){
		$('.modalerrordiv').remove();
		$('#modelsWithDatesModal').modal('show');
	})
	$('#topTenQuery').on('click', function(){
	$('#topTenModal').modal('show');
	})
	
	$('#topTenSearch').on('click', function(){
		$('.modalerrordiv').remove();	
		var firstDate = $("#datetimepicker9").find("input").val();
		var secondDate = $("#datetimepicker10").find("input").val();
		$('#searchParams').html('You are searching for the top 10 Market/Operator/Cell ID failures between  ' + firstDate + ' and ' + secondDate);
		$('#queryHeader').addClass('animated fadeOutUp');
		$('#queryHeader2').removeClass('animated fadeOutUp');
		$('#queryHeader2').addClass('animated fadeInDown');
		if(checkDates(firstDate, secondDate) == 'false'){
			
		}else{
			$('#topTenModal').modal('hide');
			hideTable();
		findTopTenFailures(firstDate, secondDate);
		$('#querysTable').addClass('animated fadeOutUp');
		changeContainerCSS('imsiDates');
		}
	});
	
	$('#imsiTopTen').on('click', function(){
		$('#topTenImsiModal').modal('show');
	})
	
		$('#failureClassImsis').on('click', function(){
		$('#getByFailureClassModal').modal('show');
	})
	
}