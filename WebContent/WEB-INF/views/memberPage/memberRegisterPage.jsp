<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="http://192.168.20.112:8080/Railring_v18/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="http://192.168.20.112:8080/Railring_v18/bootstrap/css/bootstrap-responsive.min.css" />
<script src="http://192.168.20.112:8080/Railring_v18/bootstrap/js/jquery-2.2.3.min.js"></script>
<script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
<meta charset="UTF-8">
<title>Join us</title>
<script>
	var flag1 = false;
	var flag2 = false;

	$(document).ready(function(){
	    $('#inputEmail').keyup(function(){
	        if ( $('#inputEmail').val().length > 0) {
	            var email = $(this).val();
	            $.ajax({
	                type : 'POST',
	                url : 'valid.action',
	                data:{email:email, cmd:"email"},
	                success : function(data) {
	                	var result = data.trim();
	                	if (result == "yes"){
	                        $("#emailCheck").html("<span style='color:blue'>사용 가능한 이메일 입니다.</span>");
	                        flag1 = true;
	                    } 
	                	else if(result == "not"){
	                        $("#emailCheck").html("<span style='color:red'>잘못된 이메일 형식입니다.</span>");
	                        flag1 = false;
	                    }
	                	else if(result == "no"){
	                		$("#emailCheck").html("<span style='color:red'>중복된 이메일 입니다.</span>");
	                		flag1 = false;
	                	}
	                }
	            });
	        }
	        else{
	        	 $("#emailCheck").empty();
	        }
	    });
	    
	    $('#inputPassword').keyup(function(){
	        if ( $('#inputPassword').val().length > 0) {
	            var pw = $(this).val();
	            $.ajax({
	                type : 'POST',
	                url : 'valid.action',
	                data:{pw:pw, cmd:"pw"},
	                success : function(data) {
	                	var result = data.trim();
	                	if (result == "yes"){
	                        $("#pwCheck").html("<span style='color:blue'>사용 가능한 비밀번호 입니다.</span>");
	                        flag1 = true;
	                    } 
	                	else if(result == "no"){
	                		$("#pwCheck").html("<span style='color:red'>영문,숫자,특수문자 조합 8자 이상 16자 이하만 가능합니다.</span>");
	                		flag1 = false;
	                	}
	                }
	            });
	        }
	        else{
	        	 $("#pwCheck").empty();
	        }
	    });
	    
	    $('#inputName').keyup(function(){
	        if ( $('#inputName').val().length > 0) {
	            var name = $(this).val();
	            $.ajax({
	                type : 'POST',
	                url : 'valid.action',
	                data:{name:name, cmd:"name"},
	                success : function(data) {
	                	var result = data.trim();
	                	if (result == "yes"){
	                        $("#nameCheck").html("<span style='color:blue'>사용 가능한 이름입니다.</span>");
	                        flag1 = true;
	                    } 
	                	else if(result == "no"){
	                		$("#nameCheck").html("<span style='color:red'>6자 이하만 가능합니다.</span>");
	                		flag1 = false;
	                	}
	                }
	            });
	        }
	        else{
	        	 $("#nameCheck").empty();
	        }
	    });
	    
	    $("#register_btn").click(function(){
	        if(!$('#inputName').val()){
	        	$('#inputCheck').html('이름을 입력해 주세요.');
	        	$('#inputName').focus();
	        	flag2 = false;
	        }	
	        else if(!$('#inputEmail').val()){
	        	$('#inputCheck').html('이메일을 입력해 주세요.');
	        	$('#inputEmail').focus();
	        	flag2 = false;
	        }
	        else if(!$('#inputPassword').val()){ 
	        	$('#inputCheck').html('비밀번호를 입력해 주세요.');
	        	$('#inputPassword').focus();
	            flag2 = false;
	        }
	        else{
	        	$('#inputCheck').empty();
	        	flag2 = true;
	        }
	        if(flag1 == true && flag2 == true){
	           	$('#signup-form').submit();
	        }
	    });
	});

</script>

</head>
<body>
<jsp:include page="../../../bannerNavBar.jsp"></jsp:include>
<div style="background:url('http://192.168.20.112:8080/Railring_v18/images/bs_1.jpg') no-repeat; background-size:cover; height:300px; margin-top: 21px"></div>
<div class="container">
	<div class="row">
		<div class="span6 offset2" style="margin-top: 5%;">
			<div style="margin-left: 180px">
				<h2>지금 레일링에 가입하세요</h2>
			</div>
			<div id="vgap" style="height: 10px"></div>
			<form id="signup-form" class="form-horizontal" method="post" action="member.action">
				<input type="hidden" name="cmd" value="CONFIRM"/>
				<div class="control-group">
					<div class="controls">
						<input type="button" style="width:370px" class="btn btn-large btn-info" value="Facebook 으로 로그인"/>
					</div>
				</div>
				<div id="vgap" style="margin-left:180px; margin-right:0px; height:5px;"><hr/></div>
				<div id="vgap" style="height: 20px"></div>
				<div class="control-group">
					<div class="controls">
						<input type="text" class="span4" name="inputName" id="inputName" placeholder="이름"/>
						<br/><div id="nameCheck"></div>
					</div>
				</div>
				<div id="vgap" style="height: 10px"></div>
				<div class="control-group">
					<div class="controls">
						<input type="text" class="span4" name="inputEmail" id="inputEmail" placeholder="이메일 주소"/>
						<br/><div id="emailCheck"></div>
					</div>
				</div>
				<div id="vgap" style="height: 10px"></div>
				<div class="control-group">
					<div class="controls">
						<input type="password" class="span4" name="inputPassword" id="inputPassword" placeholder="비밀번호"/>
						<br/><div id="pwCheck"></div>
				   	</div>
				</div>
				<div id="vgap" style="height: 10px"></div>
				<div class="control-group">
					<div class="controls">
						<input type="button" style="width:370px" class="btn btn-large btn-info" value="가입하기" id="register_btn"/>
						<br/><span id="inputCheck" style="color:red"></span>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<script src="http://192.168.20.112:8080/Railring_v18/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>