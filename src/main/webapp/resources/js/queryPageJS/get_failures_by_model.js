var rootUrlUMF = "http://localhost:8080/GroupProject2016/rest/unique_model_failures";
var querysTable = "";
$(document).ready(function(){
})

var findAllUniqueModelFailures = function(tacNumber) {
	$.ajax({
		type : 'GET',
		url : rootUrlUMF + "/" + tacNumber,
		dataType : "json",
		success : loadUniqueModelFailuresTable
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
		$('#querysTable').addClass('animated fadeInDown');
		querysTable = $('#querysTable').DataTable( {
			 pagingType: "full_numbers",
			 
		        data: data,
		        
		        columns: [
		            
		            { data: "date_time",
		                render: function(data, type, row) {
		                	var date = new Date(data);
		                	alert(date.toString());
		                	var day = date.getDate();
		                	var monthIndex = date.getMonth();
		                	var year = date.getFullYear();
		                	var hours = date.getHours();
		                	var minutes = date.getMinutes();
		                	return day + '/' + monthIndex + 1 + '/' + year + ' ' + hours + ':' + minutes;
		                }
		                },

		            { data: "imsi" }
		           
		        ],
		        

		    } );
		
		};