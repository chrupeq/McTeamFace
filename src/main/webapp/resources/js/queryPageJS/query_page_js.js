$(document).ready(function() {
	$("#selectByModel").select2({dropdownCssClass : 'bigdrop'}); 
	$('#imsiWithDates').on("click", function(){
		$('#dateQueryModal').removeClass('animated bounceOut');
		$('#dateQueryModal').addClass('animated bounceIn');
	});
	
	$('#imsiStats').on("click", function(){
		$('#imsiStatsModal').removeClass('animated bounceOut');
		$('#imsiStatsModal').addClass('animated bounceIn');
		$('#imsiStatsModal').modal('show');
	});
	
	$('#allImsiFailuresBetweenDates').on('click', function(){
		$('#simgleImsiModal').modal('show');
	})
	
	$('#allFailuresForModel').on("click", function(){
		$('#modelFailuresModal').removeClass('animated bounceOut');
		$('#modelFailuresModal').addClass('animated bounceIn');
	});
	$('#modalClose').on("click", function(){
		$('#modelFailuresModal').addClass('animated bounceOut');
	});
	$('#modalClose2').on("click", function(){
		$('#modelFailuresModal').addClass('animated bounceOut');
	});
	
	$('#dateSearch').on('click', function(){
		var firstDate = $("#datetimepicker1").find("input").val();
		var secondDate = $("#datetimepicker2").find("input").val();
		$('#queryHeader').addClass('animated fadeOutUp');
		$('#queryHeader2').removeClass('animated fadeOutUp');
		$('#queryHeader2').addClass('animated fadeInDown');
		findAllImsisForDates(firstDate, secondDate);
		$('#querysTable').addClass('animated fadeOutUp');
		changeContainerCSS('imsiDates');
	});
	
	$('#imsiStatsModalSearch').on('click', function(){
		var thirdDate = $("#datetimepicker3").find("input").val();
		var fourthDate = $("#datetimepicker4").find("input").val();
		var date = new Date();
		console.log(date.getTime());
		imsiQuery(thirdDate, fourthDate);
		changeContainerCSS('imsiStats');
	});
	
	$('#selectByModel').change(function(){
		changeContainerCSS('modelsQuery');
        var selectedTac = $('#selectByModel option:selected').val();
        $('#tableDiv').removeClass('animated fadeOutUp');
    	$('#tableDiv').addClass('animated fadeInDown');
        modelQuery();
        findAllUniqueModelFailures(selectedTac);
        
})
});

var changeContainerCSS = function(whichTable){
	var newDiv = "";
	if(whichTable == 'modelsQuery'){
 newDiv = '<h2>Query Selector<h2><br><h4 style="color: blue;">You are viewing all falures for ' + $("#selectByModel option:selected").text() + '</h4>';
	}
	if(whichTable == 'imsiDates'){
		$('#tableDiv').removeClass('animated fadeOutUp');
		$('#tableDiv').addClass('animated fadeInDown');
		 newDiv = '<h2>Query Selector<h2><br><h4 style="color: blue;">You are viewing all failures for dates between ' + $("#datetimepicker1").find("input").val() + ' and ' + $("#datetimepicker2").find("input").val() + '</h4>';
			clearDatePickers();
	}
var closeButton = '<button id="backToHomeButton" type="button" class="btn btn-secondary">Back To Home</button>';

showTable();
$('#backToHomeButton').on('click', function(){
	hideTable();
})

if(whichTable == 'imsiDates'){
	
imsiWithDatesQuery();
}

if(whichTable == 'imsiStats'){
	$('#queryHeader').addClass('animated fadeOutUp');
	$('#queryHeader2').addClass('animated fadeInDown');
	clearDatePickers();
}
}

var clearDatePickers = function(){
	  $('#datetimepicker1').find('input').val("");
	  $('#datetimepicker2').find('input').val("");
	  $('#datetimepicker3').find('input').val("");
	  $('#datetimepicker4').find('input').val("");
	};