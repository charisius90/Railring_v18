<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
[<c:forEach var="reply" items="${replys }" varStatus="stat"><c:if test="${not stat.first }">,</c:if>
{"rev_re_no":"${reply.rev_re_no }", 
"rev_re_content":"${reply.rev_re_content }", 
"rev_re_date":"${reply.rev_re_date }", 
"rev_re_po":"${reply.rev_re_pos }", 
"rev_re_depth":"${reply.rev_re_depth }", 
"rev_no":"${reply.rev_no }", 
"mem_no":"${reply.mem_no }", 
"rev_group":"${reply.rev_group }",
"mem_name":"${reply.mem_name }"}
</c:forEach>]