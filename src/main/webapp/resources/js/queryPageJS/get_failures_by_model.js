var rootUrl = "http://localhost:8080/GroupProject2016/rest/unique_model_failures";
var findAllUniqueModelFailures = function(tacNumber) {
	$.ajax({
		type : 'GET',
		url : rootUrl + "/" + tacNumber,
		dataType : "json",
		success : loadUniqueModelFailuresTable
	});
};

var loadUniqueModelFailuresTable = function(data){
	var querysTable = $('#querysTable').DataTable( {
		 pagingType: "full_numbers",
		 
	        data: data,
	        
	        columns: [

	            { data: "date_time",
	                render: function(data, type, row) {
	                	var date = new Date(data);
	                	var day = date.getDate();
	                	var monthIndex = date.getMonth();
	                	var year = date.getFullYear();
	                	var hours = date.getHours();
	                	var minutes = date.getMinutes();
	                	return day + '/' + monthIndex + 1 + '/' + year + ' ' + hours + ':' + minutes;
	                }
	                },

	            { data: "event_cause.cause_code" },

	            { data: "event_cause.event_id" },
	            
	            { data: "event_cause.description" },

            { data: "failure_class.failure_class" },
            
            { data: "failure_class.description" },	            
            
            {data: "user_equipment.manufacturer"},
            
            {data: "user_equipment.model"},
            
            {data: "user_equipment.access_capability"},
            
            {data: "user_equipment.operating_system"},
            
            {data: "mcc_mnc.country"},
            
            {data: "mcc_mnc.operator"},
            
	            { data: "cell_id"},
	            
	            { data: "ne_version"}
	           
	        ],
	        

	    } );
	};
	
	var loadImsiTable = function(data){
		$('#querysTable').DataTable( {
			 pagingType: "full_numbers",
			 
		        data: data,
		        
		        columns: [
		            
		            { data: "report_id" },
		            
		            { data: "date_time",
		                render: function(data, type, row) {
		                	var date = new Date(data);
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
	