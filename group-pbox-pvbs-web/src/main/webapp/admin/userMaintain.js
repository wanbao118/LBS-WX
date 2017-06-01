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
		'pageRecorders':pageRecorders,
		'currentPage':currentPage
	};
	$.ajax({
		url : contextPath + "/service/user/userMaintain",
		type : "post",
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(json),
		success : function(response) {
			if (response.result == "00000") {
				var tr = $("#cloneTr");
				$(".data").hide();
				$("#pageInfo").empty();
				$("#cloneTr").show();

				$.each(response.listData, function(index, item) {
					var clonedTr = tr.clone();
					var _index = index;

					clonedTr.children("td").each(
							function(inner_index) {
								switch (inner_index) {
								case (0):
									$(this).html(item.userName);
									break;
								case (1):
									$(this).html(item.userPosition);
									break;
								case (2):
									$(this).html(item.transactionLimit);
									break;
								case (3):
									$(this).html(item.termDepositeLimit);
									break;
								case (4):
									$(this).html(item.exchangeRateLimit);
									break;
								case (5):
									$(this).html("<button type='button' class='btn btn-info' onclick=editTest('"+item.id+"')><i class='glyphicon glyphicon-edit icon-white'></i>Edit</button>"
											+ "<button type='button' class='btn btn-danger' onclick=deleteUser('"+item.id+"')><i class='glyphicon glyphicon-trash icon-white'></i>Delete</button>");
									break;
								}
							});
					clonedTr.append("<input type='hidden' id='"+item.id+"' value='"+JSON.stringify(item)+"'/>");
					clonedTr.insertAfter(tr);
				});
				$("#cloneTr").hide();
				$("#userList").show();
				handlePageInfo(response.params);
			} else {
				$(".data").hide();
				$('#empForm').find('.alert-warning')
				.html($.errorHandler.prop(response.errorCode[0])).show();
			}
		},
		error : function()
	    {
	        alert("net error");
	    }
	});
}

function deleteUser(id) {
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
				$("#cloneTr").hide();
				$('#empForm').find('.alert-success').html("Delete User Successfully !").show();
				$('#empForm').find('.alert-warning').hide();
			}
			else {
				$('#empForm').find('.alert-warning').html($.errorHandler.prop(response.errorCode[0])).show();
			}
		},
		error: function() {
			$('#empForm').find('.alert-warning').html("Net Error").show();
		}
	
	});
}


function handlePageInfo(params){
	var currentPage = new Number(params.currentPage);
	var totalPage = new Number(params.totalPages);

	$("#pageInfo").append("<li><a href=\"#\"  onclick=\"enquiry('1')\">First Page</a></li>");
	if(currentPage>1){
		$("#pageInfo").append("<li><a href=\"#\"  onclick=\"enquiry('"+(currentPage-1)+"')\">Previous Page</a></li>" );
	}else{
		$("#pageInfo").append("<li><a href=\"#\"  onclick=\"enquiry('1')\">Previous Page</a></li>");
	}
	if(totalPage>currentPage){
		$("#pageInfo").append("<li><a href=\"#\"  onclick=\"enquiry('"+(currentPage+1)+"')\">Next Page</a></li>");
	}else{
		$("#pageInfo").append("<li><a href=\"#\"  onclick=\"enquiry('"+(totalPage)+"')\">Next Page</a></li>");
	}
	$("#pageInfo").append("<li><a href=\"#\"  onclick=\"enquiry('"+totalPage+"')\">Last Page</a></li>");
}