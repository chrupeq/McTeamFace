$(document).ready(function() {
	$("#selectByModel").select2({dropdownCssClass : 'bigdrop'}); 
	$('#imsiWithDates').on("click", function(){
		$('#dateQueryModal').removeClass('animated bounceOut');
		$('#dateQueryModal').addClass('animated bounceIn');
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
		changeContainerCSS('imsiDates');
	});
	
	
	
	$('#selectByModel').change(function(){
		$('#container').addClass('animated fadeOutUp');
		changeContainerCSS('modelsQuery');
        var selectedTac = $('#selectByModel option:selected').val();
        findAllUniqueModelFailures(selectedTac);
        
})
});

var changeContainerCSS = function(whichTable){
	
	$('#container').remove();
	
	
var newDiv = 'Query Selector';
var closeButton = '<button id="backToHomeButton" type="button" class="btn btn-secondary">Back To Home</button>';

$('#queryHeader2').html(newDiv);
$('#queryHeader2').hide();
$('#queryHeader2').addClass('animated fadeInDown');
$('#queryHeader2').show();
$('#buttonDiv').html(closeButton);
$('#buttonDiv').hide();
$('#buttonDiv').addClass('animated fadeInDown');
$('#buttonDiv').show();

$('#backToHomeButton').on('click', function(){	
	refreshPage();
});

if(whichTable == 'imsiDates'){
imsiWithDatesQuery();
}
if(whichTable == 'modelsQuery'){
	modelQuery();
}
	
}

var refreshPage = function(){
	$('#querysTable').addClass('animated fadeOutUp');
	$('#queryHeader2').addClass('animated fadeOutUp');
	$('#backToHomeButton').addClass('animated fadeOutUp');
	
	location.reload();
	
	
	
}
