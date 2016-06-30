<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<link rel="stylesheet" href="http://192.168.20.112:8080/Railring_v18/bootstrap/css/bootstrap.min.css" />
<script src="http://192.168.20.112:8080/Railring_v18/bootstrap/js/jquery-2.2.3.min.js"></script>

<style>
	.image-wh{
		width:100%;
		height:230px;
	}
	.span4 a{
		display: block;
	    width: 330px;
	    height: 350px;
	    border: 1px solid #dfdfdf;
	    position: relative;
		margin-right: auto;
		margin-left: auto;
	    margin-bottom: 30px;
	}
</style>

</head>
<body>
<jsp:include page="bannerNavBar.jsp"></jsp:include>
<!-- git 테스트 -->
<div style="background:url('./images/bs_1.jpg') no-repeat; background-size:cover; height:500px; margin-top: 21px"></div>
	<div class="wrap" style="padding-top:70px; padding-bottom:70px">
		<div class="container" style="margin-top: 20px; width: 1200px">
			<div class="row-fluid">
				<h2 style="text-align: center">Railring 추천</h2>
				<div class="carousel slide" id="theCarousel"
					style="margin-bottom: 20px; margin-top: 20px; padding-right:70px; padding-left:70px">
					<div class="carousel-inner">
						<div class="item active">
							<div class="span4">
								<a href="#"><img class="image-wh" src="./images/dy1.jpg" /></a>
							</div>
							<div class="span4">
								<a href="#"><img class="image-wh" src="./images/kj1.jpg" /></a>
							</div>
							<div class="span4">
								<a href="#"><img class="image-wh" src="./images/bs1.jpg" /></a>
							</div>
						</div>
						<div class="item">
							<div class="span4">
								<a href="#"><img class="image-wh" src="./images/bs1.jpg" /></a>
							</div>
							<div class="span4">
								<a href="#"><img class="image-wh" src="./images/dy1.jpg" /></a>
							</div>
							<div class="span4">
								<a href="#"><img class="image-wh" src="./images/kj1.jpg" /></a>
							</div>
						</div>
						<div class="item">
							<div class="span4">
								<a href="#"><img class="image-wh" src="./images/kj1.jpg" /></a>
							</div>
							<div class="span4">
								<a href="#"><img class="image-wh" src="./images/bs1.jpg" /></a>
							</div>
							<div class="span4">
								<a href="#"><img class="image-wh" src="./images/dy1.jpg" /></a>
							</div>
						</div>
					</div>
					<a class="carousel-control left" style="margin-top: 0px"
						href="#theCarousel" data-slide="prev">&lsaquo;</a> <a
						class="carousel-control right" style="margin-top: 0px"
						href="#theCarousel" data-slide="next">&rsaquo;</a>
				</div>
			</div>
		</div>

		<div class="container" style="margin-top: 20px; width: 1200px">
			<div class="row-fluid">
				<h2 style="text-align: center">Railer 추천</h2>
				<div class="carousel slide" id="theCarousel2" align="center"
					style="margin-bottom: 20px; margin-top: 20px; padding-right:69px; padding-left:69px">
					<div class="carousel-inner" style="align: center">
						<div class="item active">
							<div class="span4">
								<a href="#"><img class="image-wh" src="./images/dy1.jpg" /></a>
							</div>
							<div class="span4">
								<a href="#"><img class="image-wh" src="./images/kj1.jpg" /></a>
							</div>
							<div class="span4">
								<a href="#"><img class="image-wh" src="./images/bs1.jpg" /></a>
							</div>
						</div>
						<div class="item">
							<div class="span4">
								<a href="#"><img class="image-wh" src="./images/bs1.jpg" /></a>
							</div>
							<div class="span4">
								<a href="#"><img class="image-wh" src="./images/dy1.jpg" /></a>
							</div>
							<div class="span4">
								<a href="#"><img class="image-wh" src="./images/kj1.jpg" /></a>
							</div>
						</div>
						<div class="item">
							<div class="span4">
								<a href="#"><img class="image-wh" src="./images/kj1.jpg" /></a>
							</div>
							<div class="span4">
								<a href="#"><img class="image-wh" src="./images/bs1.jpg" /></a>
							</div>
							<div class="span4">
								<a href="#"><img class="image-wh" src="./images/dy1.jpg" /></a>
							</div>
						</div>
					</div>
					<a class="carousel-control left" style="margin-top: 0px"
						href="#theCarousel2" data-slide="prev">&lsaquo;</a> <a
						class="carousel-control right" style="margin-top: 0px"
						href="#theCarousel2" data-slide="next">&rsaquo;</a>
				</div>
			</div>
		</div>
	</div>
<jsp:include page="footer.jsp"></jsp:include>

<script src="http://192.168.20.112:8080/Railring_v18/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>