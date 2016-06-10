

var loadTable = function(data){
	alert(data);
    $('#dataFromDatabase').DataTable( {
    	
        data: data,

        columns: [

            { data: "report_id" },

            { data: "date_time" },

            { data: "event_cause.cause_code" },

            { data: "event_cause.event_id" },

            { data: "failure_class.failure_class" },
            
            { data: "ue_type" },
            
            { data: "market"},
            
            { data: "operator"},
            
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