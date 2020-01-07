function turnPage(e) {
  page = e.innerHTML;
  if (page == "«") {
    var currentPage = GetQueryString("page");
    //无page传参
    if (currentPage == null) {
      AddParamVal("page", 1);
    }
    //有page传参
    else {
      currentPage = parseInt(currentPage) - 1;
      if (currentPage <= 0) currentPage = 1;
      replaceParamVal("page", currentPage);
    }
  } else if (page == "»") {
    var currentPage = GetQueryString("page");
    //无page传参
    if (currentPage == null) {
      AddParamVal("page", 2);
    }
    //有page传参
    else {
      currentPage = parseInt(currentPage) + 1;
      //if(currentPage<=0) currentPage=1;
      replaceParamVal("page", currentPage);
    }
  } else {
    var currentPage = GetQueryString("page");
    //无page传参
    if (currentPage == null) {
      AddParamVal("page", parseInt(e.innerHTML));
    }
    //有page传参
    else {
      replaceParamVal("page", parseInt(e.innerHTML));
    }
  }
}
function replaceParamVal(paramName, replaceWith) {
  var oUrl = this.location.href.toString();
  var re = eval("/(" + paramName + "=)([^&]*)/gi");
  var nUrl = oUrl.replace(re, paramName + "=" + replaceWith);
  this.location = nUrl;
  window.location.href = nUrl;
}
function GetQueryString(name) {
  var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
  var r = window.location.search.substr(1).match(reg);
  if (r != null) {
    return unescape(r[2]);
  }
  return null;
}
function AddParamVal(paramName, value) {
  var oUrl = this.location.href;
  if (oUrl.indexOf("?") == -1) {
    nUrl = oUrl + "?" + paramName + "=" + value;
    this.location = nUrl;
    window.location.href = nUrl;
  } else {
    nUrl = oUrl + "&" + paramName + "=" + value;
    this.location = nUrl;
    window.location.href = nUrl;
  }
}
function selectTime(time) {
  if (GetQueryString("timeLimit") == null) {
    AddParamVal("timeLimit", time);
  } else {
    replaceParamVal("timeLimit", time);
  }
}
$(function(){
	if(GetQueryString("timeLimit")!=null){ 
	    var option=GetQueryString("timeLimit");
	    if(option=="day")
	      document.getElementById("filter").innerHTML="一天内 ▼";
	    else if(option=="week")
	      document.getElementById("filter").innerHTML="一周内 ▼";
	    else if(option=="month")
	      document.getElementById("filter").innerHTML="一月内 ▼";
	    else if(option=="year")
	      document.getElementById("filter").innerHTML="一年内 ▼";
	  }
})
