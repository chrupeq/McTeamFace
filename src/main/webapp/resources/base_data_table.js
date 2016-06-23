

var loadTable = function(data){
	/*
	 * Don't delete this, it reads column headings out of JSON object dynamically.
	 */
			
//	for(var i in data){
//	    var key = i;
//	    var val = data[i];
//	    for(var j in val){
//	        var sub_key = j;
//	        var sub_val = val[j];
//	        if(sub_key == "event_cause"){
//	        	for(var k in sub_val){
//			var sub_sub_key = k;
//	        	alert(sub_sub_key);
//	        	}
//	        }
//	        if(sub_key == "event_cause"){
//	        	for(var k in sub_val){
//			var sub_sub_key = k;
//	        	alert(sub_sub_key);
//	        	}
//	        }
//	        alert(sub_key);
//	    }
//	    break;
//	}
    $('#dataFromDatabase').DataTable( {
    	
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

            { data: "event_cause.cause_code" },

            { data: "event_cause.event_id" },

            { data: "failure_class.failure_class" },
            
            { data: "user_equipment.tac" },
            
            { data: "mcc_mnc.mcc"},
            
            { data: "mcc_mnc.mnc"},
            
            { data: "cell_id"},
            
            { data: "duration"},
            
            { data: "ne_version"},
            
            { data: "imsi"},
            
            { data: "hier3_id"},
            
            { data: "hier32_id"},
            
            { data: "hier321_id"},
           
        ]

    } );
};