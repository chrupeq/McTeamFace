modelsUrl = 'http://localhost:8080/GroupProject2016/rest/unique_model_failures/date_time_query';
var model = "";
var manufacturer = "";
$(document).ready(function(){
	$.getJSON("http://localhost:8080/GroupProject2016/rest/unique_model", function(result) {
	    $.each(result, function(index, item) {
	    	
	    	$('#modelsWithDatesSelect').append($("<option></option>")
                    .attr("value",item.tac)
                    .text(item.manufacturer + ' ' + item.marketing_name));
	    });
	});
	
	$('#modelsWithDatesLink').on('click', function(){
		$('#modelsWithDatesModal').modal('show');
	})
	
	$('#modelDatesSearch').on('click', function(){
		var seventhDate = $("#datetimepicker7").find("input").val();
		var eighthDate = $("#datetimepicker8").find("input").val();
		var selectedTac = $('#modelsWithDatesSelect option:selected').val();
	 model = $('#modelsWithDatesSelect option:selected').text();
		$('#modelsWithDatesModal').removeClass('animated bounceIn');
		$('#modelsWithDatesModal').addClass('animated bounceOut');
		
		$('#modelsWithDatesModal').removeClass('animated bounceOut');
		$('#modelsWithDatesModal').modal('hide');
		$('#searchParams').html('You are searching for all failures for ' + model + ' between ' + seventhDate + ' and ' + eighthDate );
		loadModelsWithDatesTable(seventhDate, eighthDate, selectedTac);
		})
	
//	$('#imsiEventSearch').on('click', function(){
//		loadEventImsiTable(IMSI2);
//	});
	
	
});	

var rootUrlSelect = "http://localhost:8080/GroupProject2016/rest/unique_model";
var findAllUniqueModels = function() {
	$.ajax({
		type : 'GET',
		url : rootUrlSelect,
		dataType : "json",
		success : loadModelsDatesModal,
		error: $('#tableDiv').html('<h3 id="noDataMessage">no data to display for this query<h3>')
	});
};

var loadModelsWithDatesTable = function(seventhDate, eighthDate, selectedTac){
	$('#queryprogress').css('width', '0%');
 	$('#queryprogress').text('0%');
	$('#queryprogressouter').removeClass('animated fadeOutUp');
	$('#queryprogressouter').addClass('animated fadeInDown');
	$.ajax({
		type:'GET',
		url: modelsUrl + "/?tacNumber=" + selectedTac + "&dateOne=" + seventhDate + "&dateTwo=" + eighthDate,
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
			loadModelsWithDatesTable1(data);
			$('#queryprogressouter').mousemove(function(){
				$('#queryprogressouter').removeClass('animated fadeInDown');
				$('#queryprogressouter').addClass('animated fadeOutUp');
			});
		},
		error: $('#tableDiv').html('<h3 id="noDataMessage">no data to display for this query<h3>')
	});
}

var loadModelsWithDatesTable1 = function(data){
	
	var table = '<table class="display table table-striped table-hover table-condensed animated fadeInDown" id="querysTable">'
	+ '<thead>'
	+ '<tr>'
	+ '<th align="left" class="col-sm-2">Number of Failures</th>'
	+ '<th align="left" class="col-sm-2">Manufacturer</th>'
	+ '<th align="left" class="col-sm-2">Marketing Name</th>'
	+ '</tr>'
	+ '</thead>'
	+ '</table>';
	
	$('#tableDiv').html(table);
	 querysTable = $('#querysTable').DataTable( {
		 pagingType: "full_numbers",
		 
	        data: data,
	        
	        columns: [

	            { data: "hier3_id" },
	            
	            { data: "user_equipment.manufacturer" },
	            
	            { data: "user_equipment.marketing_name" }
	      
	        ],
	        
	        
	    } );
	
	 showTable();
	
	$('#backToHomeButton').on('click', function(){
		hideTable();
	});
	};