<%@ page contentType="text/html; charset=UTF-8" %>
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
				<h2>비밀번호 찾기</h2>
			</div>
			<div id="vgap" style="height: 10px"></div>
			<form id="signup-form" class="form-horizontal" method="post" action="member.action">
				<input type="hidden" name="cmd" value="FINDPW"/>
				<div class="control-group">
					<div class="controls">
						<input type="text" class="span4" id="inputEmail" placeholder="이메일 주소">
					</div>
				</div>
				<div id="vgap" style="height: 10px"></div>
				<div class="control-group">
					<div class="controls">
						<input type="submit" style="width:370px" class="btn btn-info" value="이메일 전송"/>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="row">
		<div class="span6 offset2">
			<div style="margin-left:180px; margin-right:20px;">
				<span class="pull-left"><a href="front.do?cmd=signup">지금 가입하기</a></span>
				<span class="pull-right"><a href="front.do?cmd=login">로그인</a></span>
			</div>
		</div>
	</div>
</div>
<script src="http://localhost:8080/Railring_v18/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>