<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/><!-- http://localhost:8081/controller/ 프로젝트 톰켓서버에 등록된 컨텍스트 경로를 가져와 변수에저장하고 변수를 사용한다. -->
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <script type="text/javascript">

  	$(document).ready(function(){//messageType
  		if(${!empty msgType}){
  			//해당 실패메시지 모달에 값을 넣고 쇼한다.(class속성값을 변경한다.)
  			//if(${msgType eq "실패 메세지"}){
  				$("#messageType").attr("class","modal-content panel-wrning");
  			//}
  				$("#myMessage").modal("show");
  		}
  	});
  	
  	function registerCheck(){
  		var memID = $("#memID").val();
  		$.ajax({
  			url : "${contextPath}/memRegisterCheck.do",
  			type : "get",
  			data : {"memID" : memID},
  			success : function(result){
  				// 중복유무 출력(result=1 : 사용할수있는 아이디, 0 : 사용할수없는 아이디)
  				if(result ==1){
  					$("#checkMessage").html("사용할 수 있는 아이디입니다.");
  					$("checkType").attr("class","modal-content panel-success");
  				}else{
  					$("#checkMessage").html("사용할 수 없는 아이디입니다.");
  					$("checkType").attr("class","modal-content panel-wrning");  					
  				}
  				$("#myModal").modal("show");//모달창은 .modal("show")를 넣어주면 뜬다.
  			},
  			error : function(){ alert("error");}
  		});
  		
  	}
  	
  	function passwordCheck(){
  		var memPassword1 = $("#memPassword1").val();
  		var memPassword2 = $("#memPassword2").val();
  		if(memPassword1 != memPassword2){
  			$("#passMessage").html("비밀번호가 일치하지 않습니다.");
  		}else{
  			$("#passMessage").html("");
  			$("#memPassword").val(memPassword1);
  		}
  		
  		
  	}
  
  	function goInsert(){
  		var memAge = $("#memAge").val();
  		if(memAge==null || memAge=="" || memAge==0){
  			alert("나이를 입력하세요");
  			return false;
  		}
  		document.frm.submit(); //전송   form에 name을 "frm"으로 지어줘야한다.
  	}
  </script>
</head>
<body>

<div class="container">
<jsp:include page="../common/header.jsp"/>
  <h2>회원가입</h2>
  <div class="panel panel-default">
    <div class="panel-heading">회원가입</div>
    <div class="panel-body">
     <form name="frm" action="${contextPath}/memRegister.do" method="post" >
     <input type="hidden" id="memPassword" name="memPassword"  value=""/>
     	<table class="table table-bordered" style="text-align: center; border: 1px solid #dddddd;">
     		<tr>
	     		<th style="width: 110px; vertical-align: middle;">아이디</td>
	     		<td colspan="2"><input type="text" class="form-control" id="memID" name="memID" mexlength="20" placeholder="아이디를 입력하세요"/></td>
	     		<td style="width: 110px;"><button type="button"  class="btn btn-primary btn-sm" onclick="registerCheck()">중복확인</button></td>     		
     		</tr>
     		<tr>
	     		<th style="width: 110px; vertical-align: middle;">비밀번호</td>
	     		<td colspan="2"><input type="password" onkeyup="passwordCheck()" class="form-control" id="memPassword1" name="memPassword1" mexlength="20" placeholder="비밀번호를 입력하세요"/></td>    		
     		</tr> 
     		<tr>
	     		<th style="width: 110px; vertical-align: middle;">비밀번호확인</td>
	     		<td colspan="2"><input type="password" onkeyup="passwordCheck()" class="form-control"  id="memPassword2" name="memPassword2" mexlength="20" placeholder="비밀번호를 입력하세요"/></td>    		
     		</tr> 
     		<tr>
	     		<th style="width: 110px; vertical-align: middle;">사용자 이름</td>
	     		<td colspan="2"><input type="text" class="form-control" id="memName" name="memName" mexlength="20" placeholder="이름을 입력하세요"/></td>    		
     		</tr>  
     		<tr>
	     		<th style="width: 110px; vertical-align: middle;">나이</td>
	     		<td colspan="2"><input type="number" class="form-control" id="memAge" name="memAge" mexlength="3" placeholder="나이를 입력하세요"/></td>    		
     		</tr>    
     		<tr>
	     		<th style="width: 110px; vertical-align: middle;">성별</td>
	     		<td colspan="2">
	     			<div class="form-group" stype="text-align : center; margin: 0 auto;">
	     				<div class="btn-group" data-toggle="buttons">
	     					<lable class="btn btn-primary active">
	     						<input type="radio" id="memGender" name="memGender" autocomplete="off" value="남자" checked/>남자
	     					</lable>
	     					<lable class="btn btn-primary">
	     						<input type="radio" id="memGender" name="memGender" autocomplete="off" value="여자" />여자
	     					</lable>	     					
	     				</div>
	     			</div>
	     		</td>    		
     		</tr> 
     		<tr>
	     		<th style="width: 110px; vertical-align: middle;">이메일</td>
	     		<td colspan="2"><input type="text" class="form-control" id="memEmail" name="memEmail" mexlength="20" placeholder="이메일을 입력하세요"/></td>    		
     		</tr>  
     		<tr>
     			<td colspan="3" style="text-align: left;">
     				<span id="passMessage" style="color:red;"></span> <input type="button" class="btn btn-primary btn-sm pull-right" value="등록" onclick="goInsert()"/>
     			</td>
     		</tr>    		    		    		      		    	
     	</table>
     </form>
    </div>
    <!--  다이얼로그창(모달) -->
    <!-- Modal -->
	<div id="myModal" class="modal fade" role="dialog" >
	  <div class="modal-dialog">	
	    <!-- Modal content-->
	    <div id="checkType" class="modal-content panel-info">
	      <div class="modal-header panel-heading">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">메세지 확인</h4>
	      </div>
	      <div class="modal-body">
	        <p id="checkMessage"></p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	      </div>
	    </div>	
	  </div>
	</div> 
	<!-- 실패 메세지를 출력(modal) -->
	<div id="myMessage" class="modal fade" role="dialog" >
	  <div class="modal-dialog">	
	    <!-- Modal content-->
	    <div id="messageType" class="modal-content panel-info">
	      <div class="modal-header panel-heading">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">${msgType}</h4>
	      </div>
	      <div class="modal-body">
	        <p>${msg}</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	      </div>
	    </div>	
	  </div>
	</div>    
    <div class="panel-footer">스프1탄_인프런(박매일)</div>
  </div>
</div>
</body>
</html>