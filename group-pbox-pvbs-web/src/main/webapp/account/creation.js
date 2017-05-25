$(document)
		.ready(
				function() {
					$('#creationForm').bootstrapValidator(
									{
										message : 'This value is not valid',

										feedbackIcons : {
											valid : 'glyphicon glyphicon-ok',
											invalid : 'glyphicon glyphicon-remove',
											validating : 'glyphicon glyphicon-refresh'
										},
										fields : {
											clearingCode : {
												group: '.group',
												validators : {
													notEmpty : {
														message : 'please input clearing code!'
													},

													regexp : {
														regexp : '^[0-9]{3}$',
														message : 'Please input three number!'
													}
												/*
												 * remote: { url:
												 * paths+'/service/employee/checkErExists',
												 * message: '用戶名不存在', delay :
												 * 2000,//per 2s send a request
												 * type: 'POST' }
												 */}
											},

											password : {
												validators : {
													notEmpty : {
														message : 'Please input password!'
													},
													stringLength : {
														min : 1,
														max : 15,
														message : 'Please input password between one and fifteen!'
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
									creation(e.target);
								}

							});
				});
function creation(e) {
	var clearingCode = $("#clearingCode").val();
	var branchNumber = $("#branchNumber").val();
	var accountNumber = $("#accountNumber").val();
	var customerName = $("#customerName").val();
	var customerId = $("#customerId").val();
	var dateOfBirth = $("#dateOfBirth").val();
	var address = $("#address").val();
	var contactAddress = $("#contactAddress").val();
	var contactNumber = $("#contactNumber").val();
	var wechatId = $("#wechatId").val();
	var employment = $("#employment").val();

	var acct = {
		'clearingCode' : clearingCode,
		'branchNumber' : branchNumber,
		'accountNumber' : accountNumber,
		'customerName' : customerName,
		'customerId' : customerId,
		'dateOfBirth' : dateOfBirth,
		'address' : address,
		'contactAddress' : contactAddress,
		'contactNumber' : contactNumber,
		'wechatId' : wechatId,
		'employment' : employment,
		'operationCode' : 'A'
	};
	$.ajax({
		url : contextPath+"/service/acct/acctMaintenance",
		type : "post",
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(acct),
		success : function(response) {
			if (response.result == 00000) {
				
				$('#creationForm').find('.alert').html('User created successfully!').show();
			} else {
				$('#creationForm').find('.alert').html('User has existsed!').show();
			}
		}
	});
}
