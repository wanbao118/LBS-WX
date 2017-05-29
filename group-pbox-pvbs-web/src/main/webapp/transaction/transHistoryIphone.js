$(document).ready(function() {
	getSupportCode();
	getPrimaryCode();
	$('#empForm').bootstrapValidator(
			{
				message : 'This value is not valid',

				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					accountNumber : {
						group: '.group',
						validators : {
							notEmpty : {
								message : 'Please Account Num!'
							},
							regexp : {
								regexp : '^[0-9]{12}$',
								message : 'Please input 12 numbers!'
							}
						}
					}
				}
			});
	$("#sub_bt").on("click",function(){
		enquiry('1');
	});
});

Date.prototype.toLocaleString = function() {
	var year = this.getFullYear();
	var month = this.getMonth() + 1;
    var date = this.getDate();
    var hour = this.getHours();
    var minute = this.getMinutes();
    var second = this.getSeconds();
    if (hour < 10){
    	hour = "0" + hour;
    }
    if (minute < 10){
    	minute = "0" + minute;
    }
    if (second < 10){
    	second = "0" + second;
    }
	return  year+ "/" + month + "/" + date + "/ " + hour + ":" + minute + ":" + second;
};

function enquiry(currentPage) {
	$('.box-content').find('.alert-warning').hide();
	var accountNumber = $("#accountNumber").val();
	var currency = $("#currency").val();
	var transferType = $("#transferType").val();
	var acct = {
		'accountNumber':accountNumber,
		'currency' : currency,
		'operationCode':'Q',
		'params':{'Type':transferType,'pageRecorders':pageRecorders,'currentPage':currentPage}
	};
	
	$.ajax({
		url : contextPath+"/service/accountbalance/transHis",
		type : "post",
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(acct),
		success : function(response) {
			$("#data").empty();
			 $("#pageInfo").empty();
			if (response.result == 00000) {
				if(response.listData.length==0){
					$('.box-content').find('.alert-warning').html('No Record.').show();
				}
				$.each(response.listData, function(index,item){
					var rec = "<div class=\"alert alert-info alert-dismissible alert-info-new\" role=\"alert\">";
					rec = rec+"<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>";
					rec = rec+"<i class=\"fa fa-info-circle\"></i>";
					switch(item.transferType){
	               	 	case("D"):
	               	 		rec=rec+item.sourceAccountNum;
	               	 		rec=rec+"  Deposit ";
	               	 	 break;
	                    case("W"):
	                    	rec=rec+item.sourceAccountNum;
	                    	rec=rec+"  Withdraw ";
	               	 	 break;
	                    case("T"):
	                    	rec=rec+item.sourceAccountNum;
	                    	rec=rec+"  Transfer to";
	                    	rec=rec+" "+item.targetAccountNum;
	               	 	 break;
               	 	}
						rec=rec+" "+item.transferAmount+"("+item.currency+")";
						rec=rec+" at "+new Date(item.createTime).toLocaleString();
						rec=rec+"</div>";
					$("#data").append(rec); 
                });  
				handlePageInfo(response.params);
			}else{
				$('.box-content').find('.alert-warning').html('Search Transfer History Error !  '+$.errorHandler.prop(response.errorCode[0])).show();
			}
		},
	error : function()
    {
        alert("net error");
    }
	});
}
function handlePageInfo(params){
	var currentPage = new Number(params.currentPage);
	var totalPage = new Number(params.totalPages);
	//$("#pageInfo").append("<li><a href=\"#\"  onclick=\"enquiry('1')\">First Page</a></li>");
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
	//$("#pageInfo").append("<li><a href=\"#\"  onclick=\"enquiry('"+totalPage+"')\">Last Page</a></li>");
}
function getPrimaryCode(){
	var acct = {
			"item":"Primary_Ccy_Code"		
	}
	$.ajax({
		url : contextPath+"/service/sysconf/getSysConfList",
		type : "post",
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(acct),
		success : function(response) {
			if (response.result == 00000) {			
				for(var i = 0;i<response.listData.length;i++){
					$("#currency").append("<option value='"+response.listData[i].value+"'>"+response.listData[i].value+"</option>");
				}
			} 

		}
	})
}

function getSupportCode(){
	var acct = {
			"item":"Support_Ccy"		
	}
	$.ajax({
		url : contextPath+"/service/sysconf/getSysConfList",
		type : "post",
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(acct),
		success : function(response) {
			if (response.result == 00000) {			
				for(var i = 0;i<response.listData.length;i++){
					$("#currency").append("<option value='"+response.listData[i].value+"'>"+response.listData[i].value+"</option>");
				}
			}
		}
	})
}


