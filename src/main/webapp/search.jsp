<%@page import="crawler.SearchResultEntry"%>
<%@page import="java.util.List"%>
<%@page import="edu.net.searchEngine.elasticsearch.dao.impl.EsSearch"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="css/index-wrapper-input-button.css" />
    <link rel="stylesheet" href="css/jquery-ui.min.css" />
    <link rel="stylesheet" href="css/search-page.css" />
    <link rel="icon" type="image/x-ico" href="img/logo.ico" />
    <script src="js/jquery.min.js"></script>
    <script src="js/jquery-ui.min.js"></script>
    <script src="js/autocomplete.js"></script>
    <script src="js/searchFunction.js"></script>
    <title>IT-Search</title>
  </head>

  <body>
    <div class="head">
      <form action="search.jsp" method="GET">
      <a href="/">
        <img class="IT-logo" src="img/logo.png" height="50" />
      </a>
        <div class="research-wrapper">
          <input
            id="input"
            class="research-input search-input"
            type="text"
            placeholder="请输入关键字"
            name="keyword"
            autocomplete="off"
          />
          <button class="research-button" onclick="submit">GO!</button>
        </div>
      </form>
    </div>
    <div class="clear"></div>
    <div class="search-result">
      <div style="margin-left: 16.5%;" class="search-count">IT-Search为您找到相关结果约**个</div>
      <div class="filter">
        <div id="filter" class="filter-button">时间不限 ▼</div>
        <div class="clear"></div>
        <div class="filter-option">
          <a style="text-align: center;" onclick="selectTime('day')">一天内</a>
          <a style="text-align: center;" onclick="selectTime('week')">一周内</a>
          <a style="text-align: center;" onclick="selectTime('month')">一月内</a>
          <a style="text-align: center;" onclick="selectTime('year')">一年内</a>
        </div>
      </div>
    </div>
    <div class="clear"></div>
    <div>
    <% 
    	String keyword = request.getParameter("keyword");
    	EsSearch search = new EsSearch();
    	search.inseartSearch(keyword);
    	List<SearchResultEntry> result = search.fullTextSerch(keyword);
    	for(int i=0;i<result.size();i++){
    		out.println("<div class=\"result-container\">");
    		out.println("<a href=\""+result.get(i).getUrl()+"\" target=\"_blank\" class=\"title\">"+result.get(i).getTitle()+"</a>");
    		int index = result.get(i).getText().indexOf("<span style=\"color:red;\">");
    		out.println("<div class=\"text\">"+result.get(i).getText()+"</div>");
    		out.println("<div class=\"url\">"+result.get(i).getUrl()+"</div>");
    		out.println("</div>");
    	}
    %>
    </div>
    <div class="clear"></div>
  </body>
  <div>
    <ul class="pagination">
      <li><a onclick="turnPage(this)">«</a></li>
      <li><a onclick="turnPage(this)">1</a></li>
      <li><a class="active" href="#">2</a></li>
      <li><a onclick="turnPage(this)">3</a></li>
      <li><a onclick="turnPage(this)">4</a></li>
      <li><a onclick="turnPage(this)">5</a></li>
      <li><a onclick="turnPage(this)">6</a></li>
      <li><a onclick="turnPage(this)">7</a></li>
      <li><a onclick="turnPage(this)">»</a></li>
    </ul>
  </div>
  <div class="copyright-research-page">
    Copyright © 2019 Designed By PlumK
  </div>
</html>
<script>
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
document.getElementById("input").setAttribute("value","<%=keyword%>");
</script>