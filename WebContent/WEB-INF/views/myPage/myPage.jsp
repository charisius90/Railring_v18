<%@page import="review.model.ReviewDao"%>
<%@page import="review.model.ReviewDto"%>
<%@page import="member.model.MemberDto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="mate.model.MateDao"%>
<%@page import="member.model.MemberDao"%>
<%@page import="mate.model.MateDto"%>
<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="http://localhost:8080/Railring_v18/bootstrap/css/bootstrap.min.css" />
<script src="http://localhost:8080/Railring_v18/bootstrap/js/jquery-2.2.3.min.js"></script>
<title>My Page</title>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<style>
div.container{width:1200px}
</style>
<!-- 
	마이페이지
	@author 수항
 -->
<script>
	$(
		// jQueryUI 데이트피커
		function(){
			$("#datepicker").datepicker(
				{
					dateFormat: "yy-mm-dd",
					changeMonth: true,
					changeYear: true,
					minDate: "-60y",
					maxDate: "+0y",
					yearRange: "1970:2000"
				}		
			);
		}		
	);
	
	// 프로필 사진 등록용 함수
	function addFile1(){
		var formData = new FormData();
		formData.append("file",$("#imgFile")[0].files[0]);
		
		var id = "#imgFile"
		if(!fnFileValidCheck(id)){
			return false;
		}
		
		$.ajax({
			url:"image.action?cmd=img",
			type:"POST",
			data:formData,
			dataType:"json",
			async:false,
			cache:false,
			contentType:false,
			processData:false,
			success:function(data){
				alert("업로드 완료");
				location.href = "member.action?cmd=MYPAGE";
			},
			error:function(request, status, error){
				alert("[에러]\n 에러가 계속되면 서비스에 문의하세요");
			}
		});
		
		dialog.dialog("close");
		return true;
	}
	
	// 배경사진 등록용 함수
	function addFile2(){
		var formData = new FormData();
		formData.append("file",$("#backImgFile")[0].files[0]);
		
		var id = "#backImgFile";
		if(!fnFileValidCheck(id)){
			return false;
		}
		
		$.ajax({
			url:"image.action?cmd=back",
			type:"POST",
			data:formData,
			dataType:"json",
			async:false,
			cache:false,
			contentType:false,
			processData:false,
			success:function(data){
				alert("업로드 완료");
				location.href = "member.action?cmd=MYPAGE";
			},
			error:function(request, status, error){
				alert("[에러]\n 에러가 계속되면 서비스에 문의하세요");
			}
		});
		
		dialog.dialog("close");
		return true;
	}
	
	// 파일 용량, 형식 체크 함수
	function fnFileValidCheck(id){
		var maxSize = 5 * 1024 * 1024;	// 최대 용량 5MB
		var size = $(id)[0].files[0].size;
		
		if(size > maxSize){
			alert("파일 용량이 5MB를 초과하였습니다.");
			return false;
		}
		
		var fileName = $(id)[0].files[0].name;
		
		var ext = fileName.substring(fileName.lastIndexOf(".")+1);
		if(ext.toUpperCase() != "JPG"){
			alert("JPG 파일만 업로드 할 수 있습니다.");
			return false;
		}
		
		return true;
	}
	var dialog;
	
	// 페이지 로딩 시 기본 세팅
	$(document).ready(
		function(){
			// 프로필 사진 클릭시 이벤트 등록
			$("#myImgSpan").click(
				function(){
					dialog = $("#myImgModal").dialog({
						resizable: false,
						height:240,
						width:400,
						modal:true,
						buttons:{
							"등록":addFile1,
							"취소":function(){
								$(this).dialog("close");
							}
						}
					});
				}
			);
			
			// 배경사진 등록 버튼 클릭 이벤트 등록
			$("#backImgSpan").click(
				function(){
					dialog = $("#backImgModal").dialog({
						resizable: false,
						height:240,
						width:400,
						modal:true,
						buttons:{
							"등록":addFile2,
							"취소":function(){
								$(this).dialog("close");
							}
						}
					});
				}
			);
			
			// 이전 작업하던 tab을 active해주는 조작 : 비밀번호 변경 시 페이지가 새로고침되므로 새로고침시 서버에서 넘겨주는 히든 값 act 확인하여 값이 존재하면 active설정 
			var act = $("#act").val();
			if(act){
				$("#menu1").removeClass("active");
				$("#check1").removeClass("active");
				$("#menu2").removeClass("fade");
				$("#menu2").addClass("active");
				$("#check2").addClass("active");
			}
			
			// 성별이 DB에 등록되어있는 경우 그 값으로 초기화
			var gender = $("#gender").val();
			if(gender == "female"){
				$("#optionGender1").attr({checked:"checked"});
			}
			else{
				$("#optionGender2").attr({checked:"checked"});
			}
			
			// 이미지 비율 조정
			var $img = $("#myImg");
			var ratio = $img.height()/$img.width();
			if(ratio > 1){
				$img.attr("style", "width:100%; height:auto");
			}
			else{
				$img.attr("style", "width:auto; height:100%;");
			}
		}
	);
</script>
</head>
<body>
<jsp:include page="../../../bannerNavBar.jsp"></jsp:include>
<input type="hidden" id="act" value="${active}"/>
<input type="hidden" id="gender" value="${member.mem_gender}"/>

<div id="myImgModal" title="프로필 사진 등록" class="hide">
	<p>
		<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>새 프로필 사진을 등록하시겠습니까?
		<br/>* 5 MB 이하의 JPG(.jpg) 파일만 가능
	</p>
	<form id="imgFileForm" enctype="multipart/form-data">
		<input type="file" name="imgFile" id="imgFile"/>
	</form>
</div>
<div id="backImgModal" title="프로필 사진 등록" class="hide">
	<p>
		<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>새 배경 사진을 등록하시겠습니까?
		<br/>* 5 MB 이하의 JPG(.jpg) 파일만 가능
	</p>
	<form id="backImgFileForm" enctype="multipart/form-data">
		<input type="file" name="imgFile" id="backImgFile"/>
	</form>
</div>

<div class="container">
	<div class="row" id="myBackImgDiv" style="padding-left:5px; height:400px; background-image:url('http://localhost:8080/Railring_v18/upload/${member.mem_background}'); background-repeat:no-repeat; background-position:0 20px; background-size:cover;">
		<div id="changeBackImg" style="width:125px; height:20px; margin-top:50px;">
			<span id="backImgSpan" style="background-color:#bbbbbb; overflow:hidden; border:gray 1px solid; cursor:pointer; cursor:hand;"><i class="icon-user icon-camera"></i>배경 이미지 변경</span>
		</div>
		<div id="divider" style="background-color:none; height:150px"></div>
		<div id="myImgDiv" align="left" style="border:gray 1px solid; background-color:white; width:168px; height:168px; padding:2px; overflow:hidden;">
			<span id="myImgSpan" style="cursor:pointer; cursor:hand;">
				<img id="myImg" src="http://localhost:8080/Railring_v18/upload/${member.mem_image}" alt="프로필 사진 등록"/>
			</span>
		</div>
	</div>
	<div class="row" id="rowDivider" style="height: 20px"></div>
	<div class="row" id="row2">
		<div class="span2" id="mymenu">
			<div id="myinfo">
				<ul class="nav nav-tabs nav-stacked">
					<li id="divider" style="height: 10px"></li>
					<li class="nav-header"><font size="3" color="black">MY 개인정보</font></li>
					<li id="check1" class="active"><a data-toggle="tab" href="#menu1"><font size="2" color="black"><b>내 프로필</b></font></a></li>
					<li id="check2"><a data-toggle="tab" href="#menu2"><font size="2" color="black"><b>비밀번호변경</b></font></a></li>
					<li id="divider" style="height: 30px"></li>
					<li class="nav-header"><font size="3" color="black">MY 여행정보</font></li>
					<li><a data-toggle="tab" href="#menu3"><font size="2" color="black"><b>내 스케쥴</b></font></a></li>
					<li><a data-toggle="tab" href="#menu4"><font size="2" color="black"><b>내 체크인</b></font></a></li>
					<li><a data-toggle="tab" href="#menu5"><font size="2" color="black"><b>내 게시글</b></font></a></li>
					<li id="divider" style="height: 30px"></li>
					<li class="nav-header"><font size="3" color="black">MY 문의사항</font></li>
					<li><a data-toggle="tab" href="#menu6"><font size="2" color="black"><b>FAQ</b></font></a></li>
					<li><a data-toggle="tab" href="#menu7"><font size="2" color="black"><b>Q&A</b></font></a></li>
					<li><a data-toggle="tab" href="#menu8"><font size="2" color="black"><b>ABOUT US</b></font></a></li>
				</ul>
			</div>
		</div>
		<div class="span10" id="mycontent" style="height: 800px">
			<div class="tab-content">
				<div id="menu1" class="tab-pane active">
					<div id="menuFrame" style="margin: 5px">
						<h5>My Profile</h5>
						<form class="form-horizontal" method="post" action="member.action">
							<hr/>
							<input type="hidden" name="cmd" value="changeInfo"/>
							<div class="control-group info">
								<label class="control-label" for="inputEmail">이메일</label>
								<div class="controls">
									<input type="text" name="inputEmail" id="inputEmail" value="${member.mem_email}" disabled/>
									<span class="help-inline">등록된 이메일</span>
								</div>
							</div>
							<div class="control-group info">
								<label class="control-label" for="inputName">이름</label>
								<div class="controls">
									<input type="text" name="inputName" id="inputName" value="${member.mem_name}"/>
									<span class="help-inline" style="color:red">${error}</span>
								</div>
							</div>
							<div class="control-group info">
								<label class="control-label" for="inputGender">성별</label>
								<div class="controls">
									<label class="radio">
										<input type="radio" name="inputGender" id="optionGender1" value="female"/>
										여자
									</label>
									<label class="radio">
										<input type="radio" name="inputGender" id="optionGender2" value="male"/>
										남자
									</label>
								</div>
							</div>
							<div class="control-group info">
								<label class="control-label" for="inputGender">생년월일</label>
								<div class="controls">
									<p><input type="text" name="inputBirth" id="datepicker" value="${member.mem_birth}"/></p>
								</div>
							</div>
							<div class="control-group info">
								<div class="controls">
									<button type="submit" class="btn btn-primary">변경</button>
								</div>
							</div>
						</form>
					</div>
				</div>
				<div id="menu2" class="tab-pane fade">
					<h5>Change Password</h5>
					<form class="form-horizontal" method="post" action="member.action">
						<input type="hidden" name="cmd" value="changePw"/>
						<input type="hidden" name="email" value="${member.mem_email}"/>
						<hr/>
						<div id="mypw">
							<div class="control-group info">
								<label class="control-label" for="oldPw">기존 비밀번호</label>
								<div class="controls">
									<input type="password" name="oldPw" id="oldPw" value="${oldPw}"/>
									<span class="help-inline" id="oldPwMsg" style="color:red">${wrong}${success}</span>
								</div>
							</div>
							<div class="control-group info">
								<label class="control-label" for="newPw">새 비밀번호</label>
								<div class="controls">
									<input type="password" name="newPw" id="newPw" value="${newPw}"/>
									<span class="help-inline" id="newPwMsg" style="color:red">${fault}</span>
								</div>
							</div>
							<div class="control-group info">
								<label class="control-label" for="checkPw">새 비밀번호 확인</label>
								<div class="controls">
									<input type="password" name="checkPw" id="checkPw" value="${checkPw}"/>
									<span class="help-inline" id="checkPwMsg" style="color:red">${diff}</span>
								</div>
							</div>
							<div class="control-group info">
								<div class="controls">
									<button type="submit" class="btn btn-primary">변경</button>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div id="menu3" class="tab-pane fade">
					내 스케쥴 목록
				</div>
				<div id="menu4" class="tab-pane fade">
					내 체크인 목록
				</div>
				<div id="menu5" class="tab-pane fade">
					<hr/>
					내 리뷰 목록
					<%
						// 규채 : 각 게시판(메이트, 리뷰)에서 작성한 글들을 리스트 형식으로 뿌려줌
						MemberDto member = (MemberDto)session.getAttribute("member");
						MemberDao memberdao = new MemberDao();
						ReviewDto reviewDto;
						ReviewDao reviewdao = new ReviewDao();
						
						String member_no = member.getMem_no();
						
						ArrayList getreviewList = reviewdao.getReview(member_no);
						
						if(getreviewList.size() != 0){
					%>
						<table  class="table table-hover">
							<thead>
								<tr>
									<th class="span2" style="text-align: center;">제목</th>
									<th class="span2" style="text-align: center;">게시판</th>
									<th class="span1" style="text-align: center;">작성일</th>
									<th class="span1" style="text-align: center;">조회수</th>
								</tr>
							</thead>
							<%					
								for(int i=0; i< getreviewList.size(); i++){
									reviewDto = (ReviewDto)getreviewList.get(i);
							 %>
							<tbody>
								<tr>
									<td style="text-align: center; vertical-align: middle;"><a href="mate.action?cmd=READ&mate_no=<%=reviewDto.getRev_no()%>"><%=reviewDto.getRev_subject()%></a></td>
									<td style="text-align: center; vertical-align: middle;">리뷰 게시판</a></td>
									<td style="text-align: center; vertical-align: middle;"><%=reviewDto.getRev_date()%></td>
									<td style="text-align: center; vertical-align: middle;"><%=reviewDto.getRev_count()%></td>
								</tr>
							</tbody>
							<%	} %>
				
						</table>
					<%	
						}else{
					%>
							<br/><br/>
							<h3>작성한 글이 없습니다.</h3>					
					<%		
						}					
					%>
					<hr/>
					내 메이트 목록
					<%
						MateDto mateDto = new MateDto();
						MateDao matedao = new MateDao();
						
						ArrayList getMateList = matedao.getMate(member_no);
						
						if(getMateList.size() != 0){
					%>
						<table  class="table table-hover">
							<thead>
								<tr>
									<th class="span2" style="text-align: center;">제목</th>
									<th class="span2" style="text-align: center;">게시판</th>
									<th class="span1" style="text-align: center;">작성일</th>
									<th class="span1" style="text-align: center;">조회수</th>
								</tr>
							</thead>
							<%					
								for(int i=0; i< getMateList.size(); i++){
									mateDto = (MateDto)getMateList.get(i);
							 %>
							<tbody>
								<tr>
									<td style="text-align: center; vertical-align: middle;"><a href="mate.action?cmd=READ&mate_no=<%=mateDto.getMat_no()%>"><%=mateDto.getMat_subject()%></a></td>
									<td style="text-align: center; vertical-align: middle;">메이트 게시판</a></td>
									<td style="text-align: center; vertical-align: middle;"><%=mateDto.getMat_date()%></td>
									<td style="text-align: center; vertical-align: middle;"><%=mateDto.getMat_count()%></td>
								</tr>
							</tbody>
							<%	} %>
				
						</table>
					<%	
						}else{
					%>
							<br/><br/>
							<h3>작성한 글이 없습니다.</h3>					
					<%		
						}
					%>					
				</div>

				<div id="menu6" class="tab-pane fade">
					<h5>FAQ</h5>
					<hr/>
					<div class="accordion" id="accordion2">
						<div class="accordion-group">
							<div class="accordion-heading">
								<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseOne">
									Q1. ${question1}
								</a>
							</div>
							<div id="collapseOne" class="accordion-body collapse in">
								<div class="accordion-inner">
									Answer1. ${answer1}
								</div>
							</div>
						</div>
						<div class="accordion-group">
							<div class="accordion-heading">
								<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
					  				Q2. ${question2}
								</a>
							</div>
							<div id="collapseTwo" class="accordion-body collapse">
								<div class="accordion-inner">
					    	    Answer2. ${answer2}
					 			</div>
							</div>
						</div>
						<div class="accordion-group">
							<div class="accordion-heading">
								<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseThree">
					  				Q3. ${question3}
								</a>
							</div>
							<div id="collapseThree" class="accordion-body collapse">
								<div class="accordion-inner">
					    	    Answer3. ${answer3}
					 			</div>
							</div>
						</div>
						<div class="accordion-group">
							<div class="accordion-heading">
								<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseFour">
					  				Q4. ${question4}
								</a>
							</div>
							<div id="collapseFour" class="accordion-body collapse">
								<div class="accordion-inner">
					    	    Answer4. ${answer4}
					 			</div>
							</div>
						</div>
					</div>
				</div>
				<div id="menu7" class="tab-pane fade">
					Q&A
				</div>
				<div id="menu8" class="tab-pane fade">
					ABOUT US
				</div>
			</div>
		</div>
	</div>
</div>
<script src="http://localhost:8080/Railring_v18/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>