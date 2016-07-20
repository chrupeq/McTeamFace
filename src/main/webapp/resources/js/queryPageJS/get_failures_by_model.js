var rootUrlUMF = "http://localhost:8080/GroupProject2016/rest/unique_model_failures";
var querysTable = "";

var findAllUniqueModelFailures = function(tacNumber) {
	$('#queryprogress').css('width', '0%');
 	$('#queryprogress').text('0%');
	$('#queryprogressouter').removeClass('animated fadeOutUp');
	$('#queryprogressouter').addClass('animated fadeInDown');
	$.ajax({
		type : 'GET',
		url : rootUrlUMF + "/" + tacNumber,
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
			loadUniqueModelFailuresTable(data);
			if(getTheCookieYouNeed('job_title') == 'NME'){
			modelsBarChart(data);
			}
			$('#queryprogressouter').mousemove(function(){
				$('#queryprogressouter').removeClass('animated fadeInDown');
				$('#queryprogressouter').addClass('animated fadeOutUp');
			});
		}
	});
};

var loadUniqueModelFailuresTable = function(data){
	 querysTable = $('#querysTable').DataTable( {
		 pagingType: "full_numbers",
		 
	        data: data,
	        
	        columns: [

	            { data: "hier3_id" },
	            
	            { data: "user_equipment.manufacturer" },
	            
	            { data: "user_equipment.marketing_name" },
	            
	            { data: "event_cause.event_id" },
	            
	            { data: "event_cause.cause_code" },
    
	        ],
	        
	        
	    } );
	
	 showTable();
		$('#backToHomeButton').on('click', function(){
			$("#selectByModel").select2('val', 'All');
			hideTable();
		})
	};
	
	var loadImsiTable = function(data){
		if(data.length == 0){
			$('#tableDiv').html('<h3 id="noDataMessage">no data to display for this query<h3>')
		}else{
		$('#querysTable').addClass('animated fadeInDown');
		querysTable = $('#querysTable').DataTable( {
			 pagingType: "full_numbers",
			 
		        data: data,
		        
		        columns: [
		            
		            { data: "date_time"},

		            { data: "imsi" }
		           
		        ],
		        

		    } );
		}
		};