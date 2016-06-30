<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
[<c:forEach var="city" items="${result}" varStatus="stat"><c:if test="${not stat.first }">,</c:if>
{"city_no":"${city.city_no }", "city_title":"${city.city_title }", 
"city_title_kor":"${city.city_title_kor }","city_index":"${city.city_index }","infos":
[<c:forEach var="info" items="${city.infos }" varStatus="stat"><c:if test="${not stat.first }">,</c:if>
{"info_no":"${info.info_no }", "info_title":"${info.info_title }", "info_title_kor":"${info.info_title_kor }",
"info_index":"${info.info_index }"}
</c:forEach>]}
</c:forEach>]
