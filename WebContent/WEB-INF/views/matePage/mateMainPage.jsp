<%@page import="matereply.model.MateReplyDao"%>
<%@page import="matereply.model.MateReplyDto"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="utils.Paging"%>
<%@page import="mate.model.MateDto"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<title>메이트 게시판 메인 화면</title>
<link rel="stylesheet" href="http://192.168.20.112:8080/Railring_v18/bootstrap/css/bootstrap.min.css" />
<script src="http://192.168.20.112:8080/Railring_v18/bootstrap/js/jquery-2.2.3.min.js"></script>
<script type="text/javascript"> 
	function searchKeywordSubmit() {
		if(document.searchForm.keyword.value == ""){
			alert("검색어를 입력하세요.");
			document.searchForm.keyword.focus();
			return;
		}
		document.searchForm.submit();
	}
	
</script>
</head>
<style>
body {
	margin: 40px;
} /* 사방 40px 떨어뜨려놓기 */

p.test{
	white-space: nowrap;
	width: 400px;
	overflow: hidden;
	text-overflow: ellipsis;
}
.newImg{
    width: 15px;
    height: auto;
    max-width: 100%;
    vertical-align: middle;
    border: 0;
}
div.container{width:1200px}
</style>
<body>

<%
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");

	Paging<MateDto> paging = (Paging<MateDto>) request.getAttribute("paging");
	MateReplyDao rdao = new MateReplyDao();

%>
	<jsp:include page="../../../bannerNavBar.jsp"></jsp:include>
	<div class="container">
		<div class="row" style="margin-top:40px">
<!-- 			<div class="span12"> -->
				<div style="border: 1px solid gray; border-radius: 10px">
					<h4 style="margin-left: 40px;">메이트 게시판</h4>
<!-- 					color: #2478FF -->
				</div>
				<hr />
				<div>
				<table align=center border=0 width=80%>
					<tr>
						<td align=left> Total : <%=paging.getTotalCount() %> Articles
						(<font color=red>  <%=paging.getCurrentPage()%> / <%=paging.getTotalPageCount()%> page </font>)
						</td>
					</tr>
				</table>
					<table class="table table-hover">
					<thead>
						<tr>
		                     <th class="span2" style="text-align: center;">번호</th>
		<!--                      <th class="span2" style="text-align: center;"></th> -->
		                     <th class="span4" style="text-align: center;">제목</th>
		                     <th class="span2" style="text-align: center;">작성자</th>
		                     <th class="span2" style="text-align: center;">작성일</th>
		                     <th class="span2" style="text-align: center;">조회</th>
                  		</tr>
					</thead>
					<tbody>
						<%if(paging.getList().size() == 0){%>
							<tr>
								<td colspan="6" style="text-align: center; font-size: 14px">게시판에 저장된 글이 없습니다.</td>
							</tr>
						<%} else {
								for(MateDto mateDto : paging.getList()){
						%>
							<tr>
								<td style="text-align: center; vertical-align: middle;"><%=mateDto.getMat_no()%></td>
<%-- 								<td><a href="mate.action?cmd=READ&mate_no=<%=mateDto.getMat_no()%>"> --%>
<!-- 									<img src ="./images/busan.jpg" style="width:130px; height:130px"/> -->
<!-- 								</a></td> -->
								<td style="vertical-align: middle; margin-left: 20px">
								<a href="mate.action?cmd=READ&mate_no=<%=mateDto.getMat_no()%>">
									<%=mateDto.getMat_subject()%>
									<%
			                              SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			                              Date resdate = t.parse(mateDto.getMat_date());
			                              
			                               String now = t.format(new Date());
			                              Date nowdate = t.parse(now);
			                              
			                              long diff = nowdate.getTime() - resdate.getTime();
			                              long diffTime = (diff/(1000*60))%60;
                          			 %>
                          			 	<strong style="color: #ff1e1e">
		                           <%
		                              if(rdao.getSize(Integer.parseInt(mateDto.getMat_no())) ==0 ){
		                                 
		                              }else{
		                           %>   
		                                 [<%=rdao.getSize(Integer.parseInt(mateDto.getMat_no()))%>]</strong>
		                           <% 
		                              }
		                              if(diffTime < 1){
		                           %>
		                                 &nbsp;<img class="newImg" src="./images/ico-new.gif" /></a></td>
		                           <%
		                              }
		                           %>
								<td style="text-align: center; vertical-align: middle;"><%=mateDto.getMem_name()%></td>
								<td style="text-align: center; vertical-align: middle;"><%=mateDto.getMat_date()%></td>
								<td style="text-align: center; vertical-align: middle;"><%=mateDto.getMat_count()%></td>
							</tr>
						<%
							}
						}
						%>
						</tbody>
					</table>
				</div>
				<div align="right">
					<a class="btn" href="mate.action?cmd=POST"><i class="icon-pencil"></i>글쓰기</a>
					<a class="btn" href="mate.action?cmd=MATE&reload=true"><i class="icon-home"></i>처음으로</a>
					<!-- 연필아이콘 -->
				</div>
				<hr />
				<div align="center">Go to Page&nbsp;&nbsp;
					<%	
						if(paging.isPrev()){
					%>
						<a href="mate.action?cmd=MATE&page=<%=paging.getPrevBlockPage()%><%if(paging.isSearchKeyword()){%>&keyword=<%=paging.getSearchKeyword()%>&searchOption=<%=paging.getSearchOption()%><%}%>">이전</a>&nbsp;:::&nbsp;&nbsp;
					<%	
						} 
					%>	
					<%
						for(int i=paging.getPrevBlockPage()+1; i<paging.getNextBlockPage(); i++){
							if(paging.getTotalPageCount() < i) {
								break;
							}
							
							if(i == paging.getCurrentPage()){
					%>
								<strong><%=i%></strong>&nbsp;&nbsp;&nbsp;
					<%
							} else {
					%>
								<a href="mate.action?cmd=MATE&page=<%=i%><%if(paging.isSearchKeyword()){%>&keyword=<%=paging.getSearchKeyword()%>&searchOption=<%=paging.getSearchOption()%><%}%>"><%=i%></a>&nbsp;&nbsp;&nbsp;
					<%		
							}
						}
					%>
					<%	
						if(paging.isNext()){
					%>
						<a href="mate.action?cmd=MATE&page=<%=paging.getNextBlockPage() %><%if(paging.isSearchKeyword()){%>&keyword=<%=paging.getSearchKeyword()%>&searchOption=<%=paging.getSearchOption()%><%}%>">다음</a>
					<%	
						} 
					%>
				</div>
				<br /> <br />
				<div align="center">
					<form name="searchForm" class="form-search" action="mate.action">
						<select name="searchOption" class="span2">
							<option value="ALL" <%if("ALL".equals(paging.getSearchOption())){%>selected<%}%>>제목+내용</option>
							<option value="SUBJECT" <%if("SUBJECT".equals(paging.getSearchOption())){%>selected<%}%>>제목만</option>
							<option value="MEMNO" <%if("MEMNO".equals(paging.getSearchOption())){%>selected<%}%>>글작성자</option>
						</select> <input type="text" class="input-large" name="keyword" value="<%if(paging.getSearchKeyword() != null) {%><%=paging.getSearchKeyword()%><%} %>" />
						<input type="hidden" name="cmd" value="MATE" />
						<input type="hidden" name="page" value="1" />
						<button type="button" onclick="searchKeywordSubmit()" class="btn">검색</button>
					</form>
				</div>
<!-- 			</div> -->
		</div>
	</div>
	<jsp:include page="../../../footer.jsp"></jsp:include>
</body>
<script src="http://192.168.20.112:8080/Railring_v18/bootstrap/js/bootstrap.min.js"></script>
</html>