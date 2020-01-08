<%@page import="com.alibaba.fastjson.JSON"%>
<%@page import="java.util.List"%>
<%@page import="edu.net.itsearch.elasticsearch.dao.impl.EsSuggest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
String keyword = request.getParameter("term");
if(keyword!=null){
	EsSuggest suggest = new EsSuggest();
	List<String> re = suggest.getSuggest(keyword);
	out.println(JSON.toJSONString(re));
}
%>