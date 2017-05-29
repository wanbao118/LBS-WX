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

			if (response.result == 00000) {
				
				var tr = $("#cloneTr");
				$(".data").hide();
				 $("#pageInfo").empty();
				$("#cloneTr").show();
				$.each(response.listData, function(index,item){                              
                                                  
                      var clonedTr = tr.clone();  
                      var _index = index; 
                      clonedTr.children("td").each(function(inner_index){
                    	  switch(inner_index){  
                          case(0):   
                             $(this).html(item.sourceAccountNum);  
                             break;  
                          case(1):  
                             $(this).html(item.targetAccountNum);  
                             break;  
                         case(2):  
                        	 switch(item.transferType){
                        	 case("D"):
                        		 $(this).html("Deposit"); 
                        	 	 break;
                        	 
	                         case("W"):
	                    		 $(this).html("Withdraw"); 
	                    	 	 break;
                    	 
	                         case("T"):
                        		 $(this).html("Transfer"); 
                        	 	 break;
                        	 }
                             break;  
                         case(3):  
                        	 $(this).html(item.transferAmount);  
                             break;  
                         case(4):  
                        	 $(this).html(item.currency); 
                             break;  
                         case(5):
                        	 $(this).html(new Date(item.createTime).toLocaleString());
                    	  }                          
                      });
                      clonedTr.insertAfter(tr);  
                });  
				$("#cloneTr").hide();
				$("#transferHistoryTable").show();  
				handlePageInfo(response.params);
			}else{
				$(".data").hide();
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


