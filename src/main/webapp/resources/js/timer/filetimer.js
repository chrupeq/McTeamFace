var timerUrl = "http://localhost:8080/GroupProject2016/rest/fileTimer";
var date = "";
var counter = 0;
var setDate = function(){
	date = new Date();
	getUploadTime();
}
var getUploadTime = function() {
		  $.ajax({
				type : 'GET',
				url : timerUrl + '/1',
				dataType : "json",
				success : function(data){
					checkUpload(data);
				}
			});
};

//function wait(time){
//	   var start = new Date().getTime();
//	   var end = start;
//	   while(end < start + time) {
//	     end = new Date().getTime();
//	  }
//	}

var checkUpload = function(data){
	
	var dateArray = data.endTime.split(' ');
	var timeArray = dateArray[3].split(':');
	
	var dateArray2 = date.toString().split(' ');
	var timeArray2 = dateArray2[4].split(':');
	
	var hour1 = parseInt(timeArray[0]);
	var hour2 = parseInt(timeArray2[0]);
	var minute1 = parseInt(timeArray[1]);
	var minute2 = parseInt(timeArray2[1]);
	var second1 = parseInt(timeArray[2]);
	var second2 = parseInt(timeArray2[2]);
	
		if(hour2 <= hour1 && minute2 <= minute1 && second2 < second1){
			$('#timecodes').html('Upload began: ' + data.startTime.substring(0, 19) + '<br>Upload finished: ' + data.endTime.substring(0, 19));
		}else{
	getUploadTime();
		}
}
