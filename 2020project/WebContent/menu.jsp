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
<title>管理者メニュー</title>
</head>
<body>
	<div class="container">
		<h2>メニュー</h2>
		<span class="user-name">${ user_name} さん</span>

		<form class="logout-btn" method="post"
			action="${pageContext.request.contextPath}/Logout">
			<input class="btn btn-secondary" type="submit" value="ログアウト">
		</form>
		<form class="menu-btn" method="GET"
			action="${pageContext.request.contextPath}/Manage">
			<input class="btn" type="submit" value="勤務表管理">
		</form>
		<form class="menu-btn" method="GET"
			action="${pageContext.request.contextPath}/FeelList">
			<input class="btn" type="submit" value="気分一覧">
		</form>
		<form class="menu-btn" method="GET"
			action="${pageContext.request.contextPath}/OverTimeWorkList">
			<input class="btn" type="submit" value="残業一覧">
		</form>
		<form class="menu-btn" method="GET"
			action="${pageContext.request.contextPath}/UpdatePassword">
			<input class="btn" type="submit" value="パスワード設定">
		</form>
	</div>
</body>
</html>