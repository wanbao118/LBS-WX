$(document)
		.ready(
				//function1在提交前对输入accountNumber以及amount进行判断
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
											amount : {
												group: '.group',
												validators : {
													notEmpty : {
														message : 'please input Withdraw Amount!'
													},

													regexp : {
														regexp : '^(0|[0-9][0-9]{0,2})$',
														message : 'Please input 1-3 numbers!'
													}
												/*
												 * remote: { url:
												 * paths+'/service/employee/checkErExists',
												 * message: '用戶名不存在', delay :
												 * 2000,//per 2s send a request
												 * type: 'POST' }
												 */}
											},

											accountNumber: {
												validators : {
													notEmpty : {
														message : 'Please input Account Number!'
													},
													regexp : {
														regexp : '^[0-9]{12}$',
														message : 'Please input 12 numbers!'
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


//function2用ajax提交输入的数据
function creation(e) {
	
	var accountNumber=$("#accountNumber").val();
	var amount=$("#amount").val();
	var currency=$("#currency").val();
	alert($.errorHandler.prop('00000'));
	$('#show').html($.errorHandler.prop('00000'));
	var acct={
			'accountNumber':accountNumber,
			'amount':amount,
			'currency':currency,
			'operationCode' : 'W'
	};
	
	$.ajax({
		url : contextPath+"/service/accountbalance/transaction",
		type : "post",
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(acct),
		success : function(response) {
			if (response.result == 00000) {
				
				//$('#creationForm').find('.alert').html('Withdraw successfully!').show();
			} else {
				//$('#creationForm').find('.alert').html('Withdraw fail!Please check your balance!').show();
			}
		}
	});
}
