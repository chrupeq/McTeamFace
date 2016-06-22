

var loadTable = function(data){
	
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