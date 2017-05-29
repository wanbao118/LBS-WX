/** After click the edit button*/

var accountId = "";

function edit() {
	alert($("#edit").val());
	var list = $("#edit").val();
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

                $('#editForm').find('.alert').html('Information edited successfully!').show();
            } else {
                $('#editForm').find('.alert').html('Information failed to be edited!').show();
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

