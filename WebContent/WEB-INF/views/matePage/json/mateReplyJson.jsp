<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
[<c:forEach var="reply" items="${replys }" varStatus="stat"><c:if test="${not stat.first }">,</c:if>
{"mat_re_no":"${reply.mat_re_no }", 
"mat_re_content":"${reply.mat_re_content }", 
"mat_re_date":"${reply.mat_re_date }", 
"mat_re_po":"${reply.mat_re_pos }", 
"mat_re_depth":"${reply.mat_re_depth }", 
"mat_no":"${reply.mat_no }", 
"mem_no":"${reply.mem_no }", 
"mat_group":"${reply.mat_group }",
"mem_name":"${reply.mem_name }"}
</c:forEach>]