<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
/*

jsp 기본 템플릿파일
* 해당파일을 기반으로 신규 jsp파일 작성시 항상 이 파일을 참고한다. template.jsp는 말그대로 모든 view의 기본틀이다

*/
%>
<!DOCTYPE html>
<html>
<head>
  <title>Spring MVC01</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
  <h2>Spring MVC01</h2>
  <div class="panel panel-default">
    <div class="panel-heading">BOARD</div>
    
    
    
    <div class="panel-body">Panel Content</div>
    
    	<table class="table table-bordered table-hover">
    		<tr>
	    		<td>번호</td>
	  			<td>제목</td>    			
	  			<td>작성자</td>    			
	   			<td>작성일</td>  
	  			<td>조회수</td>   			 			
    		</tr>
    	</table>
    
    <div class="panel-footer">푸터코드 한줄 추가</div>
  </div>
</div>
</body>
</html>