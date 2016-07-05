<!-- 
	로그인 페이지
	@author 수항
 -->
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="http://localhost:8080/Railring_v18/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="http://localhost:8080/Railring_v18/bootstrap/css/bootstrap-responsive.min.css" />
<script src="http://localhost:8080/Railring_v18/bootstrap/js/jquery-2.2.3.min.js"></script>
<meta charset="UTF-8">
<title>Log in</title>
</head>
<body>
<jsp:include page="../../../bannerNavBar.jsp"></jsp:include>
<div style="background:url('http://localhost:8080/Railring_v18/images/bs_1.jpg') no-repeat; background-size:cover; height:300px; margin-top: 21px"></div>
<div class="container">
	<div class="row">
		<div class="span6 offset2" style="margin-top: 5%;">
			<div style="margin-left: 180px">
				<h2>레일링 로그인하기</h2>
			</div>
			<div id="vgap" style="height: 10px"></div>
			<form id="signup-form" class="form-horizontal" method="post" action="member.action">
				<input type="hidden" name="cmd" value="LOGIN"/>
				<div class="control-group">
					<div class="controls">
						<input type="button" style="width:370px" class="btn btn-large btn-info" value="Facebook 으로 로그인"/>
					</div>
				</div>
				<div id="vgap" style="margin-left:180px; margin-right:20px; height: 5px"><hr/></div>
				<div id="vgap" style="height: 20px"></div>
				<div class="control-group">
					<div class="controls">
						<input type="text" class="span4" name="inputEmail" id="inputEmail" placeholder="이메일 주소" value="${inputEmail}"/>
					</div>
				</div>
				<div id="vgap" style="height: 10px"></div>
				<div class="control-group">
					<div class="controls">
						<input type="password" class="span4" name="inputPassword" id="inputPassword" placeholder="비밀번호" value="${inputPassword}">
				   	</div>
				</div>
				<div id="vgap" style="height: 10px"></div>
				<div class="control-group">
					<div class="controls">
						<input type="submit" style="width:370px" class="btn btn-large btn-info" value="로그인"/>
						<c:if test="${error == 'email'}">
							<br/><br/>
							<span style="color:red">잘못된 이메일입니다.</span>
						</c:if>
						<c:if test="${error == 'pw'}">
							<br/><br/>
							<span style="color:red">잘못된 비밀번호입니다. 다시 확인하세요.</span>
						</c:if>
						<c:if test="${error == 'guest'}">
							<br/><br/>
							<span style="color:red">해당 서비스는 로그인이 필요합니다.</span>
						</c:if>
						<c:if test="${mem_no == ''}"><span style="color:red">시스템 오류! 서비스에 문의하세요.</span></c:if>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="row">
		<div class="span6 offset2">
			<div style="margin-left:180px; margin-right:20px;">
				<span class="pull-left"><a href="front.do?cmd=signup">지금 가입하기</a></span>
				<span class="pull-right"><a href="front.do?cmd=password">비밀번호 찾기</a></span>
			</div>
		</div>
	</div>
</div>
<script src="http://localhost:8080/Railring_v18/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>