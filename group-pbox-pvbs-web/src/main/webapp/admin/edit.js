/** After click the edit button*/

var userId="";

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

function editSubmit() {
	var userName = $("#userName").val();
	var userPosition = $("#userPosition").val();
	var transactionLimit = $("#transactionLimit").val();
	var exchangeLimit = $("#exchangeLimit").val();
	var tdLimit = $("#termDepositLimit").val();

	var editInfo = {
		'userId' : userId,
		'userName' : userName,
		'userPosition' : userPosition,
		'transactionLimit' : transactionLimit,
		'exchangeRateLimit' : exchangeLimit,
		'termDepositeLimit' : tdLimit,
		'operationCode' : "U"
	}

	$.ajax({
		url : contextPath + "/service/user/userMaintain",
		type : "post",
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(editInfo),
		success : function(response) {
			if (response.result == 00000) {
				enquiry(1);
				$('#editForm').find('.alert').html(
						'Information edited successfully!').show();
			} else {
				$('#editForm').find('.alert').html(
						'Information failed to be edited!').show();
			}
		}
	});
}

function editReturn() {

/*	$("#clearingCode").val('');
	$("#branchNumber").val('');
	$("#accountNumber").val('');
	$("#customerName").val('');
	$("#customerId").val('');
	$("#dateOfBirth").val('');
	$("#address").val('');
	$("#contactAddress").val('');
	$("#contactNumber").val('');
	$("#wechatId").val('');
	$("#employment").val('');
*/
	document.getElementById("maintenance_header").style.display = 'block';
	document.getElementById("maintenance_content").style.display = 'block';
	document.getElementById("edit_header").style.display = 'none';
	document.getElementById("edit_content").style.display = 'none';
}
