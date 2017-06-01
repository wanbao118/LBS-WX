/** After click the edit button*/
$(document)
		.ready(
				function() {
					$('#editForm').bootstrapValidator(
									{
										message : 'This value is not valid',

										feedbackIcons : {
											valid : 'glyphicon glyphicon-ok',
											invalid : 'glyphicon glyphicon-remove',
											validating : 'glyphicon glyphicon-refresh'
										},
										fields : {
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
var accountId = "";

function edit(id) {
	var list = $("#"+id).val();
	var acct = JSON.parse(list);
    $("#clearingCode").val(acct.account.clearingCode);
    $("#branchNumber").val(acct.account.branchNumber);
    $("#accountNumber").val(acct.account.accountNumber);
    $("#customerName").val(acct.customerName);
    $("#customerId").val(acct.customerId);
    $("#dateOfBirth").val(acct.dateOfBirth);
    $("#address").val(acct.address);
    $("#contactAddress").val(acct.contactAddress);
    $("#contactNumber").val(acct.contactNumber);
    $("#wechatId").val(acct.wechatId);
    $("#employment").val(acct.employment);

    accountId = acct.accountId;
    document.getElementById("maintenance_header").style.display = 'none';
    document.getElementById("maintenance_content").style.display = 'none';
    document.getElementById("edit_header").style.display = 'block';
    document.getElementById("edit_content").style.display = 'block';

}

function editSubmit(){

    var address = $("#address").val();
    var contactAddress = $("#contactAddress").val();
    var contactNumber = $("#contactNumber").val();
    var wechatId = $("#wechatId").val();

    var editInfo = {
        'accountId':accountId,
        'address' : address,
        'contactAddress' : contactAddress,
        'contactNumber' : contactNumber,
        'wechatId' : wechatId,
        'operationCode' : "C"
    }

    $.ajax({
        url : contextPath+"/service/acct/acctMaintenance",
        type : "post",
        contentType : "application/json",
        dataType : "json",
        data : JSON.stringify(editInfo),
        success : function(response) {
            if (response.result == 00000) {
            	enquiryInfo(null,1);
                $('#editForm').find('.alert-success').html('Information edited successfully!').show();
            } else {
                if (response.errorCode[0] == "10021")
				{
					location.href=contextPath+"/login.html";
				}
				$('#editForm').find('.alert-warning').html('Information failed to be edited!'+$.errorHandler.prop(response.errorCode[0])).show();
            }
        }
    });
}

function editReturn(){

    $("#clearingCode").val('');
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


    document.getElementById("maintenance_header").style.display = 'block';
    document.getElementById("maintenance_content").style.display = 'block';
    document.getElementById("edit_header").style.display = 'none';
    document.getElementById("edit_content").style.display = 'none';
}

