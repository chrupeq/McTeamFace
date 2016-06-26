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
		var firstDate = $('#dateSelection1').val();
		var secondDate = $('#dateSelection2').val();
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
	alert("here");
var newDiv = 'Query Selector';
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
	replaceContainer();
});

if(whichTable == 'imsiDates'){
imsiWithDatesQuery();
}
if(whichTable == 'modelsQuery'){
	modelQuery();
}
	
}