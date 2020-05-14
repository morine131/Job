<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="header.jsp" />
<title>削除確認画面</title>
</head>
<body>
	<c:if test="${ user_type == '1' || user_type == '2' }"><jsp:include
			page="nav.jsp" /></c:if>
	<div class="container">
		<h2>削除確認</h2>
		<p class="delete-confirm">打刻データを削除します。この操作は取り消せません。<p>
		<div class="target-date delete-date">対象日:${target_year}年${target_month}月${ target_day }日
			${day}曜日</div>
		<form class="under-btn" method="POST"
			action="${pageContext.request.contextPath}/Delete">
			<input type="hidden" value="${target_year}" name="target_year">
			<input type="hidden" value="${target_month}" name="target_month">
			<input type="hidden" value="${ target_day }" name="target_day">
			<input class="btn btn-danger under-btn" type="submit" value="削除">
		</form>
		<form method="get" class="under-btn"
			action="${pageContext.request.contextPath}/UpdateHistory">
			<input class="btn btn-secondary" type="submit" value="戻る">
			<input type="hidden" value="${target_year}" name="target_year">
			<input type="hidden" value="${target_month}" name="target_month">
			<input type="hidden" value="${ target_day }" name="target_day">
			<input type="hidden" value="${day }" name="day">
		</form>
	</div>
</body>
</html>