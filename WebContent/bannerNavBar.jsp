<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- html by. 김소영, 손승한 -->
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<style>
	.nav-li{
	margin-right:20px;
	}
</style>
</head>
<div class="navbar navbar-fixed-top">
	<div class="navbar-inner" style="border-radius: 0px">
		<div class="container" style="width: 1200px;">
			<div class="nav-collapse collapse">
				<ul class="nav navbar-nav style="position:relative">
					<li class="nav-li"><a href="front.do?cmd=main">MAIN</a></li>
					
					<li class="nav-li"><a href="front.do?cmd=schedule">SCHEDULE</a></li>
					
					<li class="nav-li"><a href="front.do?cmd=mate">MATE</a></li>
					
					<li class="nav-li"><a href="front.do?cmd=review">REVIEW</a></li>
					
					<li class="nav-li"><a href="front.do?cmd=mypage">MYPAGE</a></li>
				</ul>
				<div align="right" style="height: 24px; padding-top: 10px">
					<c:if test="${member!=null}"><a href="front.do?cmd=mypage">${member.mem_name}</a>님 환영합니다. &nbsp;<a
							href="front.do?cmd=logout"><i class="icon-user icon-off"></i>로그아웃</a>
					</c:if>
					<c:if test="${member==null}">
						<a href="front.do?cmd=login"><i class="icon-ok-sign icon-ok"></i>로그인</a>&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="front.do?cmd=signup"><i class="icon-user icon-black"></i>회원가입</a>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</div>

<body>
</body>
</html>