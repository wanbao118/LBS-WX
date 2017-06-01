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
											branchNumber : {
												group: '.group',
												validators : {
													notEmpty : {
														message : 'please input branch number'
													},

													regexp : {
														regexp : '^[0-9]{4}$',
														message : 'Please input four number!'
													}
												
												 }
											},
											accountNumber : {
												group: '.group',
												validators : {
													notEmpty : {
														message : 'please input account number'
													},

													regexp : {
														regexp : '^[0-9]{5}$',
														message : 'Please input five number!'
													}
												
												 }
											},
											customerName : {
												group: '.group',
												validators : {
													notEmpty : {
														message : 'please input customer name'
													},

													regexp : {
														regexp : '^[a-zA-Z\\s]{1,20}$',
														message : 'Please input correct format of name!'
													}
													
												 }
											}, 				
											customerId : {
												group: '.group',
												validators : {
													notEmpty : {
														message : 'please input customer ID'
													},

													regexp : {
														regexp : '^[0-9]{14}$',
														message : 'Please input customerID with 14 digitals!'
													}
													
												 }
											}, 											
											dateOfBirth : {
												group: '.group',
												validators : {
													notEmpty : {
														message : 'please input date of birth'
													},

													regexp : {
														regexp : '^(((0[1-9]|[12][0-9]|3[01])(0[13578]|1[02]))|((0[1-9]|[12][0-9]|30)(0[469]|11))|((0[1-9]|[1][0-9]|2[0-8])-02))([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})$',
														message : 'Please input the correct format:DDMMYY e.g.23051999!'
													}
													
												 }
											}, 
											address : {
												group: '.group',
												validators : {
													notEmpty : {
														message : 'please input address'
													},

													regexp : {
														regexp : '^[\\w\.,\\s]{1,140}$',
														message : 'Please input the address less than 140 characters'
													}
													
												 }
											},
											contactAddress : {
												group: '.group',
												validators : {
													notEmpty : {
														message : 'please input contact address'
													},

													regexp : {
														regexp : '^[\\w\.,\\s]{1,140}$',
														message : 'Please input the contact address less than 140 characters'
													}
													
												 }
											},		
											contactNumber : {
												group: '.group',
												validators : {
													notEmpty : {
														message : 'please input contact number'
													},

													regexp : {
														regexp : '^[0-9]{15}$',
														message : 'Please input the correct format contact number with 15 digits'
													}
													
												 }
											},
											wechatId : {
												group: '.group',
												validators : {
													notEmpty : {
														message : 'please input contact number'
													},
													stringLength : {
														min : 1,
														max : 30,
														message : 'Please input password between one and fifteen!'
													},
													
												 }
											},
											employment : {
												group: '.group',
												validators : {
													notEmpty : {
														message : 'please input employment '
													},
													regexp : {
														regexp : '^[\\w\\s]{1,30}$',
														message : 'Please input the employment with less than 30 characters'
													}													
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
				$('#creationForm').find('.alert-success').html('User created successfully!').show();
			} else {
				if (response.errorCode[0] == "10021")
				{
					location.href=contextPath+"/login.html";
				}
				$('#creationForm').find('.alert-warning').html($.errorHandler.prop(response.errorCode[0])).show();
			}
		}
	});
}
