/**
 * Enquiry acct master info
 */
var params;
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
	
	//存储enquiry info返回的数据 +"<td><a class='btn btn-info' href='#'><i class='glyphicon glyphicon-edit icon-white' onclick='edit(list[i])'></i>Edit</a>&nbsp;&nbsp;"
	var listData=[];
	function enquiryInfo(e,currentPage){
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
					console.log(listData);
					var len = list.length,html="";
					for(var i = 0;i<len;i++){
						params = list[i];
						html+="<tr>" +"<input type='hidden' id='realAcct' value='"+list[i].account.realAccountNumber+"'/>"
							+"<input type='hidden' id='edit' value='"+JSON.stringify(list[i])+"'/>"
							+"<td>"+list[i].customerName+"</td>"
							+"<td>"+list[i].customerId+"</td>"
							+"<td>"+list[i].dateOfBirth+"</td>"
							+"<td>"+list[i].address+"</td>"
							+"<td>"+list[i].contactAddress+"</td>"
							+"<td>"+list[i].contactNumber+"</td>"
							+"<td>"+list[i].wechatId+"</td>"
//							+"<td><a class='btn btn-info' href='javascript:void(0);' onclick=\"edit(this)\"> <i mgf='maoguifeng' class='glyphicon glyphicon-edit icon-white'></i> Edit</a>&nbsp;&nbsp;"
							+"<td><button type='button' class='btn btn-info' id='edit' onclick=\"edit()\"><i class='glyphicon glyphicon-edit icon-white'></i>Edit</button>"
							+"<button type='button' class='btn btn-danger' id='closeAcct'><i class='glyphicon glyphicon-trash icon-white'></i>Delete</button></td></tr>";
							
					}
					$(".infoCon").html(html);
					$(".acctNotExist").hide();
					$(".acctInfo").fadeIn();
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
	
	
//	function a(){
//		alert("alert");
//	}
	
	$("#acctInfoList").on("click","#closeAcct",function(){
		//alert("hello");
		var  realAcctNum =$("#realAcct").val();
		var json = {'realAccountNumber' : realAcctNum, 'operationCode' : 'D'};
		$.ajax({
			url : contextPath+"/service/acct/acctMaintenance",
			type : "post",
			contentType: "application/json",
			dataType : "json",
			data : JSON.stringify(json),
			success : function(response) {
				if(response.result == 00000 ){
					window.location.reload(); 
					$('#enquiryForm').find('.alert').html('Close success!').show();
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
		
	})
})();
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