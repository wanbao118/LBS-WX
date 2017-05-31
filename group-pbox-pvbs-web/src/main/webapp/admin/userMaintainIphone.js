$(document).ready(function() {
	$('#empForm').bootstrapValidator({
		message : 'This value is not valid',

		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			userId : {
				group : '.group',
				validators : {
					notEmpty : {
						message : 'please input user id'
					},
					regexp : {
						regexp : /^[0-9]{4}$/,
						message : 'the length should be 4.'
					},
				}
			}
		}

	}).on('success.form.bv', function(e) {
		// Prevent submit form
		e.preventDefault();
		var $form = $(e.target);
		validator = $form.data('bootstrapValidator');
		if (validator) {
			enquiry('1');
		}
	});
});

function enquiry(currentPage) {
	$('#empForm').find('.alert-success').hide();
	$('#empForm').find('.alert-warning').hide();
	var userId = $("#userId").val();

	var json = {
		'operationCode' : 'FBU',
		'userId' : userId,
		'pageRecorders' : pageRecorders,
		'currentPage' : currentPage
	};
	$
			.ajax({
				url : contextPath + "/service/user/userMaintain",
				type : "post",
				contentType : "application/json",
				dataType : "json",
				data : JSON.stringify(json),
				success : function(response) {
					$("#data").empty();
					$("#pageInfo").empty();

					if (response.result == "00000") {
						if (response.listData.length == 0) {
							$('#empForm').find('.alert-warning').html(
									"No Record Found").show();
						}
						else {
							var item = response.listData[0];
							var userPositionName;

							switch(item.userPosition){
								case("M"):
									userPositionName = "Manager";
								case("S"):
									userPositionName = "Supervisor";
								case("T"):
									userPositionName = "Teller";
							}
							
							var rec = "<div class=\"alert alert-info alert-dismissible alert-info-new\" role=\"alert\">";
							rec = rec
									+ "<button id=\"close\" type=\"button\" class=\"close\" aria-label=\"Close\"><span onclick=\"mousdown('"+item.id+"')\" aria-hidden=\"true\">&times;</span></button>";
							rec = rec
									+ "<i class=\"fa fa-info-circle\"></i>";

							rec = rec + "<div onclick=editTest('"+item.id+"') id = 'userInfor'>";

							rec = rec + " UserName : "
									+ item.userName;
							rec = rec
									+"<br /><br />";
							rec = rec
									+ " User Position : "
									+ userPositionName;

							rec = rec + "</div>"
							rec = rec + "</div>";

							$("#data").append("<input type='hidden' id='"+item.id+"' value='"+JSON.stringify(item)+ "'/>");
							$("#data").append(rec);
						}
						handlePageInfo(response.params);
					} else {
						$('#empForm').find('.alert-warning').html(
								$.errorHandler.prop(response.errorCode[0]))
								.show();
					}
				},
				error : function() {
					alert("net error");
				}
			});
}

function handlePageInfo(params) {
	var currentPage = new Number(params.currentPage);
	var totalPage = new Number(params.totalPages);

	if (currentPage > 1) {
		$("#pageInfo").append(
				"<li><a href=\"#\"  onclick=\"enquiry('" + (currentPage - 1)
						+ "')\">Previous Page</a></li>");
	} else {
		$("#pageInfo")
				.append(
						"<li><a href=\"#\"  onclick=\"enquiry('1')\">Previous Page</a></li>");
	}
	if (totalPage > currentPage) {
		$("#pageInfo").append(
				"<li><a href=\"#\"  onclick=\"enquiry('" + (currentPage + 1)
						+ "')\">Next Page</a></li>");
	} else {
		$("#pageInfo").append(
				"<li><a href=\"#\"  onclick=\"enquiry('" + (totalPage)
						+ "')\">Next Page</a></li>");
	}
}

function mousdown(id){	
	var list = $("#" + id).val();
	var user = JSON.parse(list);
	$("#userName").val(user.userName);
	$("#userPosition").val(user.userPosition);
	$("#transactionLimit").val(user.transactionLimit);
	$("#exchangeLimit").val(user.exchangeRateLimit);
	$("#termDepositLimit").val(user.termDepositeLimit);
	
	var userName = $("#userName").val();
	var userPosition = $("#userPosition").val();
	var transactionLimit = $("#transactionLimit").val();
	var exchangeLimit = $("#exchangeLimit").val();
	var tdLimit = $("#termDepositLimit").val();
	
	var userId = user.userId;

	var editInfo = {
		'userId' : userId,
		'userName' : userName,
		'userPosition' : userPosition,
		'transactionLimit' : transactionLimit,
		'exchangeRateLimit' : exchangeLimit,
		'termDepositeLimit' : tdLimit,
		'operationCode' : "DE"
	}
	
	$.ajax({
		url : contextPath+"/service/user/userMaintain",
		type : "post",
		contentType: "application/json",
		dataType : "json",
		data : JSON.stringify(editInfo),
		success : function(response) {
			if(response.result == 00000 ){
				$('#userInfor').empty();
				$('#userInfor').append("Close succeed!");
			}
			else {
//				alert("Close fail! The balance is more than 0.Please check the balance.")
				$('#userInfor').append("Close fail!");
			}
		},
		error: function() {
			$('#userInfor').append("Close fail!");
		}
	
	});
	
}

function editTest(id) {
	var list = $("#" + id).val();
	var user = JSON.parse(list);

	$("#userName").val(user.userName);
	$("#userPosition").val(user.userPosition);
	$("#transactionLimit").val(user.transactionLimit);
	$("#exchangeLimit").val(user.exchangeRateLimit);
	$("#termDepositLimit").val(user.termDepositeLimit);

	userId = user.userId;
	document.getElementById("maintenance_header").style.display = 'none';
	document.getElementById("maintenance_content").style.display = 'none';
	document.getElementById("edit_header").style.display = 'block';
	document.getElementById("edit_content").style.display = 'block';

}




