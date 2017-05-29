function logOut(){
	sessionStorage.removeItem("userId");
	sessionStorage.removeItem("userName");
	window.location.href = contextPath+"/login.html"
}
document.write("<div class=\"navbar navbar-default\" role=\"navigation\">");
document.write("	<div class=\"navbar-inner\">");        
document.write("		<button type=\"button\" class=\"navbar-toggle pull-left animated flip\">"); 		
document.write("			<span class=\"sr-only\">Toggle navigation</span>"); 
document.write("			<span class=\"icon-bar\"></span>"); 
document.write("			<span class=\"icon-bar\"></span>"); 
document.write("			<span class=\"icon-bar\"></span>"); 
document.write("		</button>"); 
var userId = sessionStorage.getItem("userId");
var userName = sessionStorage.getItem("userName");
document.write("		<a class=\"navbar-brand\" href=\""+contextPath+"/index.html\"> <img alt=\"Charisma Logo\" src=\""+contextPath+"/img/title_logo.png\" class=\"hidden-xs\"/><span>VBS</span></a>"); 
//<!-- user dropdown starts -->
document.write(" 		<div class=\"btn-group pull-right\">"); 
document.write("			<button class=\"btn btn-default dropdown-toggle\" data-toggle=\"dropdown\">"); 
document.write("				<i class=\"glyphicon glyphicon-user\"></i><span class=\"hidden-sm hidden-xs\"> "+userName+"</span>"); 
document.write("					<span class=\"caret\"></span>"); 
document.write(" 			</button>"); 
document.write(" 			<ul class=\"dropdown-menu\">"); 
//document.write("				<li><a href=\"#\">Profile</a></li>"); 
document.write(" 				<li class=\"divider\"></li>"); 
document.write("				<li><a href=\"#\" onclick=\"logOut()\">Logout</a></li>"); 
document.write("			</ul>"); 
document.write("		</div>"); 
//<!-- user dropdown ends -->
document.write("	</div>"); 
document.write("</div>");
