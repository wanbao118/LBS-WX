document.write("<hr><footer class=\"row\">");
document.write("	<p class=\"col-md-9 col-sm-9 col-xs-12 copyright\">@2017-2017 Chinasoft HSBC Site</p>");        
document.write("	<p class=\"col-md-3 col-sm-3 col-xs-12 powered-by\">Powered by : Virtual Banking System </p>"); 		
document.write("</footer>"); 
function IsPC() {
	var sUserAgent = navigator.userAgent.toLowerCase();
    var userAgentInfo = navigator.userAgent;
    var Agents = ["Android", "iPhone",
                "SymbianOS", "Windows Phone",
                "iPad", "iPod"];
    var flag = true;
    for (var v = 0; v < Agents.length; v++) {
        if (userAgentInfo.indexOf(Agents[v]) > 0) {
            flag = false;
            break;
        }
    }
    return flag;
}