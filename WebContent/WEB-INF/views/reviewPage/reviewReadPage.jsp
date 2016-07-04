<%@page import="schedule.model.ScheduleDao"%>
<%@page import="member.model.MemberDto"%>
<%@page import="reviewreply.model.ReviewReplyDto"%>
<%@page import="java.util.List"%>
<%@page import="reviewreply.model.ReviewReplyDao"%>
<%@page import="review.model.ReviewDto"%>
<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>후기 게시판 읽기 화면</title>
<link rel="stylesheet" href="http://192.168.20.112:8080/Railring_v18/bootstrap/css/bootstrap.min.css" />
<script src="http://192.168.20.112:8080/Railring_v18/bootstrap/js/jquery-2.2.3.min.js"></script>
<script type="text/javascript">
	<%
		ReviewDto dto = (ReviewDto)request.getAttribute("review");
		ReviewReplyDao dao = new ReviewReplyDao(); 
		List<ReviewReplyDto> list = dao.getReviewReplyList(dto.getRev_no());
		MemberDto member = (MemberDto)session.getAttribute("member");
// 		String sch_no=dto.getSch_no();
		
		String rev_no = dto.getRev_no();
		ScheduleDao sDao = new ScheduleDao();
// 		String sch_no= dto.getSch_no();
		String sch_no= sDao.getSchNo(rev_no);
	%>
	//댓글 등록 : re_no - 부모 댓글의 번호 (null시 root댓글)
	function replyBtnClick(rev_re_no, rev_no, mem_no) {
		var param = {};
		
		if(rev_re_no == "parent"){
			param.parent_no = rev_re_no;
			param.rev_re_content = $("#parent_text").val();
			param.rev_no = rev_no;
			param.mem_no = mem_no;
		}else{
// 			alert("자식댓글 등록");
			param.parent_no = rev_re_no;
			param.rev_re_content = $("#child_text").val();
			param.rev_no = rev_no;
		}
		
// 		jQueryApp플젝에 ajax03.html 참고
		  $.ajax({
	        type: 'post',
	        url: 'reviewReply.action?cmd=POST',
	        data: param,
// 	        여기서 서블릿으로 감
	        success: function(result) {
 	      		console.dir(result);
	      		addReplyDiv($.parseJSON(result), rev_re_no);
	      	},
	      	fail: function(result) {
	      		alert('대댓글 등록 실패:'+result);
	      	}
		  });			
	}
	
// 	댓글 등록함수 -> 등록 눌렀을때
	function addReplyDiv(data, re_no) {
		var $replyList = $("#replyListDiv");
		$replyList.text('');
		var $replyAdd = $replyList;
		var replyDiv = "";
		
// 		list받아오면 for문 돌리면됨
		for(var i=0; i<data.length; i++){
				replyDiv = "<div class='replyDiv_"+data[i].rev_re_no+"'>"
					+"<span class='mem_name'>"+setDepth(data[i].rev_re_depth)+data[i].mem_name+" : </span>"
					+"<span class='rev_re_content'>"+data[i].rev_re_content+"</span>"
					+"&nbsp;<button onclick='displayReplyDiv("+data[i].rev_re_no+","+data[i].rev_no+")'>답글</button>"
					+"</div>";
				$replyAdd.prepend(replyDiv);
			}	
		$("#parent_text").val("");
	}

	function setDepth(depth){
		if(depth == 0){
			return "";
		}else{
			var result = "";
			for(var i=0; i<depth*3; i++){
				result +="&nbsp;&nbsp;";
			}
			result +="<img src='./images/re.gif'/>&nbsp;";
		}
		return result;
	}
	
// 	대댓글 입력창 -> 답글 눌렀을때
	function displayReplyDiv(parent_no, rev_no) {
		var $replyList = $("#replyListDiv");
// 		아래 두줄 잘안됨 -> 답글 눌러서 입력창 뜬 후 답글 버튼이 닫기 버튼으로 바뀌면서 누르면 닫히게 하는. 댓글 달려다 취소
		$(this).attr('onclick', "removeReplyDiv("+parent_no+")");
		$(this).text("닫기");
		
		var replyDiv = "<form method='post' action='reviewReply.action?cmd=POST' id='replyForm_"+parent_no+"'>댓글"
		+"<textarea id='child_text' class='span12' rows='5' style='margin-left: 10px'></textarea>"
		+"<div align='right'><input class='btn btn-mini' onclick='replyBtnClick("+parent_no+","+rev_no+")' type='button' value='등록'/></div>"
		+"</form>"
// 		alert('.replyDiv_'+parent_no);
		$replyList.find('.replyDiv_'+parent_no).append(replyDiv);
	}
	
	//답글입력창 닫기.
	function removeReplyDiv(re_no) {
		var $replyList = $("#replyListDiv");
		
		$(this).attr('onclick', "displayReplyDiv("+re_no+")");
		$(this).text("답글");
		
		$(this).remove();
	}
	
	// 댓글삭제
	function removeReply(re_no) {

		var param = {"re_no" : re_no};
		
		//ajax 로 댓글 삭제 후
		 $.ajax({
	        type: 'post',
	        url: '',
	        data: param,
// 	        여기서 서블릿으로 감. 아래 result는 삭제 불린 값
	        success: function(result) {
				if(result == true) {
					$('.replyDiv_'+data.rev_re_no).remove();	
				} else {
					alert('삭제에 실패함');
				}
	      	},
	      	fail: function(result) {
	      		alert('ajax 통신 실패');
	      	}
		  });			
	}
	
	$(window).load(function(){
		   var $timeTable = $("#timeTable");
		   var sch_no=<%=sch_no%>;
		   var params={
				   "sch_no":sch_no,
		   }
			$.ajax({
				   type : "POST",
				   url : "schedule.action?cmd=TIMETABLE",
				   cache : false,
				   data : params,
				   success : function (data) {
					   data = $.parseJSON(data);
					  console.dir(data);
					  
					  for(var i=0; i<data.length; i++){
						  $timeTable.append("<tr><td>"+(i+1)+"번째 - "+data[i].city_title_kor+"</td></tr>");
						  for(var j=0; j<data[i].infos.length; j++){
							  $timeTable.append("<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;"+(j+1)+"번째 - "+data[i].infos[j].info_title_kor+"</td></tr>");
						  }
					  }
					  
				   },
				   fail : function (e) {
					   alert('등록된 스케줄이 없습니다.');
				   }
				});
		});
</script>
</head>
<style>
body {
	margin: 40px;
} /* 사방 40px 떨어뜨려놓기 */
p img{width:auto; height:auto; max-height: 600px; max-width: 600px;}
</style>
<body>
	<jsp:include page="../../../bannerNavBar.jsp"></jsp:include>
	<div class="container">
		<div class="row" style="margin-top:40px">
			<div class="span12">
				<table class="table" align="center">
					<tr>
						<td class="span1"style="text-align: center;">제목</td>
						<td class="span9"><%=dto.getRev_subject()%></td>
						<td class="span1" style="text-align: center;">조회수</td>
						<td class="span1" style="text-align: center;"><%=dto.getRev_count()%></td>
					</tr>
					<tr>
						<td style="text-align: center;">작성자</td>
						<td><%=dto.getMem_name()%></td>
						<td style="text-align: center;">등록일</td>
						<td style="text-align: center;"><%=dto.getRev_date()%></td>
					</tr>
					<tr>
						<td colspan="4" style="padding: 30px 30px 30px 30px">
							<h4 style="text-align: center;">시간표</h4>
							<div style="border-width:3px;border-style:solid;border-color:black; overflow-y: scroll; height: 200px;"><table id="timeTable" class="table" align="center"></table></div>
			                     <br/><%=dto.getRev_content()%>
	                 		</td>
					<tr>
				</table>
				<hr/>
<!-- 				댓글 보이는 부분 -->
				<div id="replyListDiv">
					<%for(int i=list.size()-1; i>=0; i--){%>
						<div class='replyDiv_<%=list.get(i).getRev_re_no()%>'>
							<input type="hidden" class="mem_no" value="<%=list.get(i).getMem_no()%>"/>
							<span class='mem_name'>
							<%
								int depth = Integer.parseInt(list.get(i).getRev_re_depth());
								
								if(depth > 0){
									for(int j=0; j<depth*3; j++){
										%>
										&nbsp;
									<%
									}
									 %>
									<img src='./images/re.gif'/>
								<%
								}
							%><%=list.get(i).getMem_name()%> : </span>	
							<span class='rev_re_content'><%=list.get(i).getRev_re_content()%></span>
							<button onclick='displayReplyDiv(<%=list.get(i).getRev_re_no()%>,<%=dto.getRev_no()%>)'>답글</button>				
						</div>
					<%}%>
				</div>
<!-- 				댓글 등록하는 부분 -->
					<form method="post" action="reviewReply.action?cmd=POST">댓글
						<textarea id="parent_text" class="span12" rows="5" style="margin-left: 10px"></textarea>
						<div align="right"><input class="btn btn-mini" type="button" onclick="replyBtnClick('parent',<%=dto.getRev_no()%>)" value="등록"/></div>
					</form>
					

				<hr/>
				<div style="float: left">
					<a href="" class="btn btn-mini"><i class="icon-chevron-left"></i>이전글</a>
					<a href="" class="btn btn-mini">다음글<i class="icon-chevron-right"></i></a>
				</div>
				<div style="float: right">
					<a href="review.action?cmd=REVIEW" class="btn btn-mini"><i class="icon-th-list"></i>목록</a>
					<%
					// 규채 : 로그인 세션 값이 읽는 글과 다를 경우 수정, 삭제 버튼이 안보임
						if(member != null){
							if(dto.getMem_no().equals(member.getMem_no())){
					%>
					<a href="review.action?cmd=UPDATE&review_no=<%=dto.getRev_no()%>" class="btn btn-mini"><i class="icon-pencil"></i>수정</a>
					<a href="review.action?cmd=DELETE&review_no=<%=dto.getRev_no()%>" class="btn btn-mini"><i class="icon-remove"></i>삭제</a>
					<%
							}
						}
					%>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../../../footer.jsp"></jsp:include>
</body>
<script src="http://192.168.20.112:8080/Railring_v18/bootstrap/js/bootstrap.min.js"></script>
</html>