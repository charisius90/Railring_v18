<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
[<c:forEach var="result" items="${result }" varStatus="stat"><c:if test="${not stat.first }">,</c:if>
{"schedule_no":"${result.schedule_no }", "mem_no":"${result.mem_no }"}
</c:forEach>]