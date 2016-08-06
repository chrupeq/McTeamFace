var timerUrl2 = "http://localhost:8080/GroupProject2016/rest/upload/progressvariable";

$(document).ready(function(){
	checkUpload();
})
var checkUpload = function(){
	$('#fileuploadprogress').css('width', '0%');
	$('#fileuploadprogress').text('0%');
	getUploadProgress();
}
var getUploadProgress = function() {
		  $.ajax({
				type : 'GET',
				url : timerUrl2,
				dataType : "json",
				success : function(data){
					checkUpload2(data);
				}
			});
};

var checkUpload2 = function(data){
	
	$('#fileuploadprogress').css('width', data + '%');
	$('#fileuploadprogress').text(data + '%');
	 setTimeout(function(){
		 getUploadProgress();
	    }, 2000);
		


}