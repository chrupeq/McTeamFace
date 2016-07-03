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
		$('#container').addClass('animated fadeOutUp');
		var firstDate = $("#datetimepicker1").find("input").val();
		var secondDate = $("#datetimepicker2").find("input").val();
		findAllImsisForDates(firstDate, secondDate);
		changeContainerCSS('imsiDates');
	});
	
	$('#imsiStatsModalSearch').on('click', function(){
		$('#container').addClass('animated fadeOutUp');
		var thirdDate = $("#datetimepicker3").find("input").val();
		var fourthDate = $("#datetimepicker4").find("input").val();
		imsiQuery(thirdDate, fourthDate);
		changeContainerCSS('imsiStats');
	});
	
//	$('#imsiStats').on('click', function(){
//		changeContainerCSS('imsiStats');
//	})
	
	
	$('#selectByModel').change(function(){
		$('#container').addClass('animated fadeOutUp');
		changeContainerCSS('modelsQuery');
        var selectedTac = $('#selectByModel option:selected').val();
        findAllUniqueModelFailures(selectedTac);
        
})
});

var changeContainerCSS = function(whichTable){
	var newDiv = "";
	if(whichTable == 'modelsQuery'){
 newDiv = '<h2>Query Selector<h2><br><h4 style="color: blue;">You are viewing all falures for ' + $("#selectByModel option:selected").text() + '</h4>';
	}
	if(whichTable == 'imsiDates'){
		 newDiv = '<h2>Query Selector<h2><br><h4 style="color: blue;">You are viewing all failures for dates between ' + $("#datetimepicker1").find("input").val() + ' and ' + $("#datetimepicker2").find("input").val() + '</h4>';
	}
var closeButton = '<button id="backToHomeButton" type="button" class="btn btn-secondary">Back To Home</button>';
$('#tableDiv').removeClass('animated fadeOutUp');
$('#queryHeader2').removeClass('animated fadeOutUp');
$('#buttonDiv').removeClass('animated fadeOutUp');
$('#queryHeader2').html(newDiv);
$('#queryHeader2').hide();
$('#queryHeader2').addClass('animated fadeInDown');
$('#queryHeader2').show();
$('#buttonDiv').html(closeButton);
$('#buttonDiv').hide();
$('#buttonDiv').addClass('animated fadeInDown');
$('#buttonDiv').show();

$('#backToHomeButton').on('click', function(){
	$('#tableDiv').removeClass('animated fadeInDown');
	$('#tableDiv').addClass('animated fadeOutUp');
	$('#queryHeader2').addClass('animated fadeOutUp');
	$('#buttonDiv').addClass('animated fadeOutUp');
	$('#container').removeClass('animated fadeOutUp');
	$('#querysTable').destroy();
	replaceContainer();
});

if(whichTable == 'imsiDates'){
imsiWithDatesQuery();
}
if(whichTable == 'modelsQuery'){
	modelQuery();
}

	
}