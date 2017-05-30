/**
 * Enquiry acct master info
 */
var params;
var current;
(function(){
	$('#enquiryForm').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			realAcctNum : {
//					group: '.group',
				validators : {
					notEmpty : {
						message : 'please input account number!'
					},

					regexp : {
						regexp : '^([0-9]{12})$|^([0-9]{5})$',
						message : 'Please input five or twelve number!'
					}
				
				}
			},
		}
	}).on('success.form.bv', function(e) {
		// Prevent submit form
		e.preventDefault();
	
		var $form = $(e.target);
		validator = $form.data('bootstrapValidator');
		if (validator) {
			enquiryInfo(e.target,'1');
		}

	});
	

	
})()

	//存储enquiry info返回的数据 
	var listData=[];
	function enquiryInfo(e,currentPage){
		current = currentPage;
		var realAcctNum = $("#realAcctNum").val();
		var json = {'realAccountNumber' : realAcctNum, 'operationCode' : 'B','params':{'pageRecorders':pageRecorders,'currentPage':currentPage}};
		$.ajax({
			url : contextPath+"/service/acct/acctMaintenance",
			type : "post",
			contentType: "application/json",
			dataType : "json",
			data : JSON.stringify(json),
			success : function(response) {
				if (response.result==00000) {
					$("#pageInfo").empty();
					listData = response.listData;
					var list = response.listData;
					var len = list.length;
					
					for(var i = 0;i<len;i++){
						var rec = "<div class=\"alert alert-info alert-dismissible alert-info-new\" role=\"alert\"  onclick=\"edit('"+list[i].id+"')\">";
						
						rec = rec+"<button id=\"closeAcct\" type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span onclick=\"mousdown('"+list[i].account.realAccountNumber+"')\" aria-hidden=\"true\">&times;</span></button>";
						rec = rec+"<i class=\"fa fa-info-circle\"></i>";
						rec = rec+"<input type='hidden' id='"+list[i].id+"' value='"+JSON.stringify(list[i])+"'/>";
						rec=rec+" "+list[i].customerName+"("+list[i].customerId+")";
						rec=rec+"</div>";
					}
					$("#data").append(rec);
				}
				else {
					$(".acctNotExist").fadeIn();
					$(".acctInfo").hide();
					if (response.errorCode[0] == "10021")
					{
						location.href=contextPath+"/login.html";
					}
					$('#enquiryForm').find('.alert-warning').html('Account Not Exist!'+$.errorHandler.prop(response.errorCode[0])).show();
				}
				handlePageInfo(response.params);
			}
		});
	}

function mousdown(realAccountNumber){
		var  realAcctNum = realAccountNumber;
		var json = {'realAccountNumber' : realAcctNum, 'operationCode' : 'D'};
		$.ajax({
			url : contextPath+"/service/acct/acctMaintenance",
			type : "post",
			contentType: "application/json",
			dataType : "json",
			data : JSON.stringify(json),
			success : function(response) {
				if(response.result == 00000 ){
					enquiryInfo(null,current); 
				}
				else {
//					alert("Close fail! The balance is more than 0.Please check the balance.")
					$('#enquiryForm').find('.alert').html('Close fail!'+$.errorHandler.prop(response.errorCode[0])).show();
				}
			},
			error: function() {
				$('#enquiryForm').find('.alert').html('Close fail!'+$.errorHandler.prop(response.errorCode[0])).show();
			}
		
		});
		
	}
function handlePageInfo(params){
	var currentPage = new Number(params.currentPage);
	var totalPage = new Number(params.totalPages);
//	$("#pageInfo").append("<li><a href=\"#\"  onclick=\"enquiryInfo(,'1')\">First Page</a></li>");
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
//	$("#pageInfo").append("<li><a href=\"#\"  onclick=\"enquiryInfo(,'"+totalPage+"')\">Last Page</a></li>");
}