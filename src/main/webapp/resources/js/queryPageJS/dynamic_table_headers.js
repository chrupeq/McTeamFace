$(document).ready(function() {
	$('#selectByModel').on('change', function() {
		  var value = $(this).val();
		  $('#container').addClass('animated fadeOutUp');
		  $('#modelFailuresModal').modal('hide');
		});
});

var table = "";
var imsiWithDatesQuery = function(){
	
	table = '<table class="table table-striped table-hover table-condensed animated fadeInDown"'
		+ 'id="querysTable">'
		+ '<thead>'
						+'<tr>'
						+	'<th align="left">Report ID.</th>'
						+	'<th align="left">Date/Time</th>'
						+	'<th align="left" class="col-sm-2">IMSI</th>'
					+	'</tr>'
				+	'</thead>'
				+'</table>';
	$('#tableDiv').html(table);
	$('#tableDiv').hide();
	$('#tableDiv').addClass('animated fadeInDown');
	$('#tableDiv').show();
}

var modelQuery = function(){
	renderModelQueryTable();
}
	TableView = Backbone.View.extend({
		render:function(){
			var template = _.template($('#model_data_table').html(), {});
			$(this.el).html(template);
			return this;
		}
	});


var renderModelQueryTable = function(){
	var tableView = new TableView();
	$('#tableDiv').html(tableView.render().el);
	$('#tableDiv').html(tableView.initialize().el);
}

var renderMainContainer = function(){
	var mainView = new MainView();
	$('#homeDiv').html(mainView.render().el);
	$('#homeDiv').html(mainView.initialize().el);
}

var replaceContainer = function(){
	renderMainContainer();
	$('#container').removeClass('animated fadeOutUp');
}
	MainView = Backbone.View.extend({
		initialize: function () {
			$(".js-example-basic-multiple").select2();
			
			$('#selectByModel').on('change', function() {
				  var value = $(this).val();
				  $('#container').addClass('animated fadeOutUp');
				  $('#modelFailuresModal').modal('hide');
				});
		},
		
		events: {
			"click #imsiWithDates":function(){
				$('#dateQueryModal').removeClass('animated bounceOut');
				$('#dateQueryModal').addClass('animated bounceIn');
				$('#dateQueryModal').modal('show');
				 $('#datetimepicker1').datetimepicker({
		             viewMode: 'years',
					 format: 'DD/MM/YYYY HH:mm'
		         });
				 $('#datetimepicker2').datetimepicker({
					 viewMode: 'years',
					 format: 'DD/MM/YYYY HH:mm'
		        });
			},
			
	"click #allFailuresForModel":function(){
		$('#modelFailuresModal').removeClass('animated bounceOut');
		$('#modelFailuresModal').addClass('animated bounceIn');
		$('#modelFailuresModal').modal('show');
		$('#selectByModel').empty();
		findAllUniqueModels();
		
	},
			"click #modalClose":function(){
				$('#modelFailuresModal').addClass('animated bounceOut');
			},
	"click #modalClose2":function(){
		$('#modelFailuresModal').addClass('animated bounceOut');
	},
			"click #dateSearch":function(){
				$('#container').addClass('animated fadeOutUp');
				changeContainerCSS('imsiDates');
			}
		},
		
		render:function(){
			var template = _.template($('#main_page_div').html(), {});
			$(this.el).html(template);
			this.delegateEvents();
			return this;
		}
	});