<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="header.jsp" />
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<h2>残業一覧</h2>
		<form class="home-btn" method="get"
			action="${pageContext.request.contextPath}/Home">
			<input class="btn" type="submit" value="戻る">
		</form>
		<span class="user-name">${ user_name} さん</span>

		<form class="logout-btn" method="post"
			action="${pageContext.request.contextPath}/Logout">
			<input class="btn btn-secondary" type="submit" value="ログアウト">
		</form>
		<div>
			<table class="feel-table table-bordered">
				<tbody>
					<tr>
						<td></td>
						<td>1</td>
						<td>2</td>
						<td>3</td>
						<td>4</td>
						<td>5</td>
						<td>6</td>
						<td>7</td>
						<td>8</td>
						<td>9</td>
						<td>10</td>
						<td>11</td>
						<td>12</td>
					</tr>
					<c:forEach items="${map}" var="obj" varStatus="parentStatus">
					<tr>
						<td>${obj.key }</td>
						<c:forEach begin="0" end="23" step="2" varStatus="status">
							<td
							<c:if test="${ obj.value[status.index+1] == '1'}" >class="background-yellow"</c:if>
							<c:if test="${ obj.value[status.index+1] == '2'}" >class="background-orange"</c:if>
							<c:if test="${ obj.value[status.index+1] == '3'}" >class="background-red"</c:if>

							>
							${ obj.value[status.index] }+ ${obj.value[status.index+1] }
							</td>
						</c:forEach>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>