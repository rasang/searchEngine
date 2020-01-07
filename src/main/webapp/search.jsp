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
    <script src="js/time-limit.js"></script>
    <title>IT-Search</title>
  </head>

  <body>
    <div class="head">
      <form action="search.jsp" method="GET">
        <img class="IT-logo" src="img/logo.png" height="50" />
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
        <div class="filter-button">时间不限 ▼</div>
        <div class="clear"></div>
        <div class="filter-option">
          <a style="text-align: center;">一天内</a>
          <a style="text-align: center;">一周内</a>
          <a style="text-align: center;">一月内</a>
          <a style="text-align: center;">一年内</a>
        </div>
      </div>
    </div>
    <div class="clear"></div>
    <div>
    <% 
    	EsSearch search = new EsSearch();
    	List<SearchResultEntry> result = search.fullTextSerch(request.getParameter("keyword"));
    	for(int i=0;i<result.size();i++){
    		out.println("<div class=\"result-container\">");
    		out.println("<a href=\""+result.get(i).getUrl()+"\" target=\"_blank\" class=\"title\">"+result.get(i).getTitle()+"</a>");
    		int index = result.get(i).getText().indexOf("<span style=\"color:red;\">");
    		out.println("<div class=\"text\">"+result.get(i).getText()+"</div>");
    		out.println("<div class=\"url\">"+result.get(i).getUrl()+"</div>");
    		out.println("</div>");
    	}
    %>
      <div class="result-container">
        <a href="http://cec.jmu.edu.cn/info/1041/7853.htm" target="_blank" class="title">讲座：将芯比心 “机”智可以过人吗</a>
        <div class="text">周昌乐，男、汉族，生于苏州太仓。自幼秉承传统国学，精通易理、深谙禅法、承继圣道,长期开设有《跨界论道：科学走进人文》通识课程，是“乐易心法”创始人、乐易读书活动的倡导者，致力于化导民众健康幸福生活。1990年毕业于北京大学理论计算机科学专业，获理学博士学位。现为厦门大学智能科学与技术系教授、博士生导师，2014年被评为厦门大学十位最受学生欢迎教师称号。长期从事人工智能及其多学科交叉领域的研究工作，目前主要开展心智仿造、圣学发明、禅法实证等方面的研究工作。先后被聘为计算机科学与技术、基础数学、语言学与应用语言学、中医诊断学、哲学（国学）等五个不同学科门类的博士生导师。是中国人工智能学会理事、福建省人工智能学会理事长、清华大学智能技术与系统国家重点实验室学术委员、浙江大学语言与认知研究中心学术委员、厦门市信息化专家组组长。受聘为浙江大学、上海中医药大学、重庆大学、汕头大学、苏州大学等十余所高校兼职教授。</div>
        <div class="url">http://cec.jmu.edu.cn/info/1041/7853.htm</div>
      </div>
      <div class="result-container">
        <a href="http://cec.jmu.edu.cn/info/1041/7853.htm" target="_blank" class="title">讲座：将芯比心 “机”智可以过人吗</a>
        <div class="text">周昌乐，男、汉族，生于苏州太仓。自幼秉承传统国学，精通易理、深谙禅法、承继圣道,长期开设有《跨界论道：科学走进人文》通识课程，是“乐易心法”创始人、乐易读书活动的倡导者，致力于化导民众健康幸福生活。1990年毕业于北京大学理论计算机科学专业，获理学博士学位。现为厦门大学智能科学与技术系教授、博士生导师，2014年被评为厦门大学十位最受学生欢迎教师称号。长期从事人工智能及其多学科交叉领域的研究工作，目前主要开展心智仿造、圣学发明、禅法实证等方面的研究工作。先后被聘为计算机科学与技术、基础数学、语言学与应用语言学、中医诊断学、哲学（国学）等五个不同学科门类的博士生导师。是中国人工智能学会理事、福建省人工智能学会理事长、清华大学智能技术与系统国家重点实验室学术委员、浙江大学语言与认知研究中心学术委员、厦门市信息化专家组组长。受聘为浙江大学、上海中医药大学、重庆大学、汕头大学、苏州大学等十余所高校兼职教授。</div>
        <div class="url">http://cec.jmu.edu.cn/info/1041/7853.htm</div>
      </div>
    </div>
    <div class="clear"></div>
  </body>
  <div class="copyright-research-page">
    Copyright © 2019 Designed By PlumK
  </div>
</html>
<script>
document.getElementById("input").setAttribute("value","<%=request.getParameter("keyword")%>");
</script>