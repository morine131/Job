<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="header.jsp" />
<title>勤務表画面</title>
</head>
<body>
	<div class="container">
		<h2>勤務表</h2>
		<form class="home-btn" method="get"
			action="${pageContext.request.contextPath}/Home">
			<input class="btn" type="submit" value="戻る">
		</form>
		<span class="user-name">${ user_name} さん</span>

		<form class="logout-btn" method="post"
			action="${pageContext.request.contextPath}/Logout">
			<input class="btn btn-secondary" type="submit" value="ログアウト">
		</form>

		<table class="feel-table table-bordered">
	<tbody>
		<tr>
			<th>休日</th>
			<th>日付</th>
			<th>曜日</th>
			<th>出社時刻</th>
			<th>退社時刻</th>
			<th>休憩時間</th>
			<th>基本時間</th>
			<th>通常残業時間</th>
			<th>深夜残業時間</th>
			<th>作業時間</th>
			<th>備考</th>
			<th>修正</th>
		</tr>
		<c:forEach items="${list}" var="map" varStatus="parentStatus">
		<tr>
			<td>${ map.holiday }</td>
			<td>${ map.date}</td>
			<td>${map.day}</td>
			<td>${ map.start_time}</td>
			<td>${map.finish_time}</td>
			<td>${map.break_time}</td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		</c:forEach>

	</tbody>
</table>
	</div>
</body>
</html>