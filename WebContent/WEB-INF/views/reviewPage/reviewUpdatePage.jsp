<%@page import="review.model.ReviewDto"%>
<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<link rel="stylesheet" href="http://localhost:8080/Railring_v18/bootstrap/css/bootstrap.min.css" />
<script src="http://localhost:8080/Railring_v18/bootstrap/js/jquery-2.2.3.min.js"></script>
<script type="text/javascript" src="http://localhost:8080/Railring_v18/SmartEditor2.8.2/js/HuskyEZCreator.js" charset="utf-8"></script>
</head>
<style>
body {
	margin: 40px;
} /* 사방 40px 떨어뜨려놓기 */
textarea {
	
}
</style>
<body>
	<%
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		ReviewDto dto = (ReviewDto)request.getAttribute("review");

	%>
	<jsp:include page="../../../bannerNavBar.jsp"></jsp:include>
	<div class="container">
		<div class="row" style="margin-top:40px">
			<div class="span12">
				<form action="review.action?cmd=UPCOMPLETE&review_no=<%=dto.getRev_no()%>" method="post">
					<table class="table" align="center">
						<tr>
							<td>제목</td>
							<td><input name="subject" type="text" value="<%=dto.getRev_subject()%>"/></td>
						</tr>
						<tr>
							<td>작성자</td>
							<td><%=dto.getMem_no()%></td>
						</tr>
						<tr>
						</tr>
						<tr>
							<td>내용</td>
							<td><textarea name="content" id="ir1" rows="10" cols="100"
									style="width: 766px; height: 412px; display: none;">
									<%=dto.getRev_content()%>
									</textarea> <!--textarea name="ir1" id="ir1" rows="10" cols="100" style="width:100%; height:412px; min-width:610px; display:none;"></textarea-->
<!-- 								<p> -->
<!-- 									<input type="button" onclick="pasteHTML();" value="본문에 내용 넣기" /> -->
<!-- 									<input type="button" onclick="showHTML();" value="본문 내용 가져오기" /> -->
<!-- 									<input type="button" onclick="submitContents(this);" -->
<!-- 										value="서버로 내용 전송" /> <input type="button" -->
<!-- 										onclick="setDefaultFont();" value="기본 폰트 지정하기 (궁서_24)" /> -->
<!-- 								</p>  -->
								<script type="text/javascript">
									var oEditors = [];

									// 추가 글꼴 목록
									//var aAdditionalFontSet = [["MS UI Gothic", "MS UI Gothic"], ["Comic Sans MS", "Comic Sans MS"],["TEST","TEST"]];

									nhn.husky.EZCreator
											.createInIFrame({
												oAppRef : oEditors,
												elPlaceHolder : "ir1",
												sSkinURI : "http://localhost:8080/Railring_v18/SmartEditor2.8.2/SmartEditor2Skin.html",
												htParams : {
													bUseToolbar : true, // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
													bUseVerticalResizer : true, // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
													bUseModeChanger : false, // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
													//aAdditionalFontList : aAdditionalFontSet,		// 추가 글꼴 목록
													fOnBeforeUnload : function() {
														alert("완료!");
													}
												}, //boolean
												fOnAppLoad : function() {
													//예제 코드
// 													oEditors.getById["ir1"]
// 															.exec(
// 																	"PASTE_HTML",
// 																	[ "로딩이 완료된 후에 본문에 삽입되는 text입니다." ]);
												},
												fCreator : "createSEditor2"
											});

									function pasteHTML() {
										var sHTML = "<span style='color:#FF0000;'>이미지도 같은 방식으로 삽입합니다.<\/span>";
										oEditors.getById["ir1"].exec(
												"PASTE_HTML", [ sHTML ]);
									}

									function showHTML() {
										var sHTML = oEditors.getById["ir1"]
												.getIR();
										alert(sHTML);
									}

									function submitContents(elClickedObj) {
										oEditors.getById["ir1"].exec(
												"UPDATE_CONTENTS_FIELD", []); // 에디터의 내용이 textarea에 적용됩니다.

// 										에디터의 내용에 대한 값 검증은 이곳에서 document.getElementById("ir1").value를 이용해서 처리하면 됩니다.

										try {
											elClickedObj.form.submit();
										} catch (e) {
										}
									}

									function setDefaultFont() {
										var sDefaultFont = '궁서';
										var nFontSize = 24;
										oEditors.getById["ir1"].setDefaultFont(
												sDefaultFont, nFontSize);
									}
								</script></td>
						</tr>
					</table>
				
				<hr />
				<div style="float: left">
					<a href="" class="btn btn-mini"><i class="icon-chevron-left"></i>이전글</a>
					<a href="" class="btn btn-mini">다음글<i
						class="icon-chevron-right"></i></a>
				</div>
				<div style="float: right">
					<a href="review.action?cmd=REVIEW" class="btn btn-mini"><i class="icon-th-list"></i>목록</a>
					<button type="submit" class="btn btn-mini" onclick="submitContents(this);"><i class="icon-pencil"></i>수정</button> 
					<a href="" class="btn btn-mini" onclick="history.back()"><i	class="icon-remove"></i>취소</a>
				</div>
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="../../../footer.jsp"></jsp:include>
</body>
<script	src="http://localhost:8080/Railring_v18/bootstrap/js/bootstrap.min.js"></script>
</html>