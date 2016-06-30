<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
{"city_no":"${result.city_no }", "sch_no":"${result.sch_no }", "infos":
[<c:forEach var="info" items="${result.infos }" varStatus="stat"><c:if test="${not stat.first }">,</c:if>
{"info_no":"${info.info_no }", "info_title":"${info.info_title }", "info_title_kor":"${info.info_title_kor }",
"info_index":"${info.info_index }", "info_lat":"${info.info_lat }", "info_lng":"${info.info_lng }"}
</c:forEach>]}