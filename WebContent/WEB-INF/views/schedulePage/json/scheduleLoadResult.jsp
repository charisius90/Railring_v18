<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
{"schedule_no":"${result.schedule_no }", "citys":
[<c:forEach var="city" items="${result.citys }" varStatus="stat"><c:if test="${not stat.first }">,</c:if>
{"city_no":"${city.city_no }", "city_title":"${city.city_title }", "city_index":"${city.city_index }",
"city_lat":"${city.city_lat }", "city_lng":"${city.city_lng }"}
</c:forEach>]}