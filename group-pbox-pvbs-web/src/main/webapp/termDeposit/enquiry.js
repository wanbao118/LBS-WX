/**
 * Enquiry acct master info
 */
var params;
var current;
var listData=[];
(function(){
	$('#enquiryForm').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			transAccountNum : {
				group: '.group',
				validators : {
					notEmpty : {
						message : 'please input account number!'
					},

					regexp : {
						regexp : '^([0-9]{12})$',
						message : 'Please input 12 number!'
					}
				
				}
			},
			depositNumber : {
				group: '.group',
				validators : {
					notEmpty : {
						message : 'please input TD Number!'
					}
				}
			}
		}
	}).on('success.form.bv', function(e) {
		e.preventDefault();
		var $form = $(e.target);
		validator = $form.data('bootstrapValidator');
		if (validator) {
			enquiryInfo(e.target,'1');
		}

	});
})();
function enquiryInfo(e,currentPage){
	current = currentPage;
	var transAccountNum = $("#transAccountNum").val();
	var realAcctNum = $("#realAcctNum").val();
	var json = {'transAccountNum' : transAccountNum,'depositNumber' : depositNumber,'operationCode' : 'TQ','params':{'pageRecorders':pageRecorders,'currentPage':currentPage}};
	$.ajax({
		url : contextPath+"/service/termDeposit/termDepositDepatcher",
		type : "post",
		contentType: "application/json",
		dataType : "json",
		data : JSON.stringify(json),
		success : function(response) {
			if (response.result==00000) {
				$("#pageInfo").empty();
				listData = response.listData;
				var list = response.listData;
				var len = list.length,html="";
				for(var i = 0;i<len;i++){
					params = list[i];
					html+="<tr>"
						+"<input type='hidden' id='"+list[i].id+"' value='"+JSON.stringify(list[i])+"'/>"
						+"<td>"+list[i].accountNum+"</td>"
						+"<td>"+list[i].depositNumber+"</td>"
						+"<td>"+list[i].termPeriod+"</td>"
						+"<td>"+list[i].maturityDate+"</td>"
						+"<td>"+list[i].depositAmount+"</td>"
						+"<td>"+list[i].maturityAmount+"</td>"
						+"<td>"+list[i].maturityStatus+"</td>"
						+"<td><button type='button' class='btn btn-info' id='edit' onclick=\"edit('"+list[i].id+"')\"><i class='glyphicon glyphicon-edit icon-white'></i>Show</button>"
				}
				$(".infoCon").html(html);
				$(".acctNotExist").hide();
				$("#acctInfoList").show();
				$(".acctInfo").fadeIn();
			}
			else {
				$(".acctNotExist").fadeIn();
				$(".acctInfo").hide();
				if (response.errorCode[0] == "10021")
				{
					location.href=contextPath+"/login.html";
				}
				$('#enquiryForm').find('.alert-warning').html('Record Not Exist!'+$.errorHandler.prop(response.errorCode[0])).show();
			}
			handlePageInfo(response.params);
		}
	});
}
function edit(id){
	var data = $("#"+id).val();
	var jsonData = JSON.parse(data);
    $("#accountNum").val(jsonData.accountNum);
    $("#depositNumber").val(jsonData.depositNumber);
    $("#depositAmount").val(jsonData.depositAmount);
    $("#termPeriod").val(jsonData.termPeriod);
    $("#termInterestRate").val(jsonData.termInterestRate);
    $("#maturityDate").val(jsonData.maturityDate);
    $("#maturityStatus").val(jsonData.maturityStatus);
    $("#createTime").val(jsonData.createTime);
    $("#edit_content").show();
	$("#edit_header").show();
	$("#maintenance_header").hide();
	$("#maintenance_content").hide();
}
function handlePageInfo(params){
	var currentPage = new Number(params.currentPage);
	var totalPage = new Number(params.totalPages);
	$("#pageInfo").append("<li><a href=\"#\"  onclick=\"enquiryInfo(,'1')\">First Page</a></li>");
	if(currentPage>1){
		$("#pageInfo").append("<li><a href=\"#\"  onclick=\"enquiryInfo(,'"+(currentPage-1)+"')\">Previous Page</a></li>" );
	}else{
		$("#pageInfo").append("<li><a href=\"#\"  onclick=\"enquiryInfo(,'1')\">Previous Page</a></li>");
	}
	if(totalPage>currentPage){
		$("#pageInfo").append("<li><a href=\"#\"  onclick=\"enquiryInfo(,'"+(currentPage+1)+"')\">Next Page</a></li>");
	}else{
		$("#pageInfo").append("<li><a href=\"#\"  onclick=\"enquiryInfo(,'"+(totalPage)+"')\">Next Page</a></li>");
	}
	$("#pageInfo").append("<li><a href=\"#\"  onclick=\"enquiryInfo(,'"+totalPage+"')\">Last Page</a></li>");
}
function editReturn(){
	$("#edit_content").hide();
	$("#edit_header").hide();
	$("#maintenance_header").show();
	$("#maintenance_content").show();
}