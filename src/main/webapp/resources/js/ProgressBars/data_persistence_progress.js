var timerUrl2 = "http://localhost:8080/GroupProject2016/rest/upload/progressvariable";

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
	console.log(data);
	$('#fileuploadprogress').css('width', data + '%');
	$('#fileuploadprogress').text(data + '%');
	if(data != 100){
		getUploadProgress();
	}

}