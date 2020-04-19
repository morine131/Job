<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="header.jsp"/>
<title>打刻完了画面</title>
</head>
<body>
<div class="container">
	<h2>打刻完了</h2>
	打刻が完了しました
	<div>
		<span>${ punchMessage }</span> <span>${ date }</span> <span>${ time }</span>
	</div>
	<form class="start-btn" method="get" action="${pageContext.request.contextPath}/Home">
		<input class="btn btn-secondary" type="submit" value="戻る">
	</form>
</div>
</body>
</html>