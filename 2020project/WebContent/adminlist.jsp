<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="header.jsp" />
<title>管理者の一覧</title>
</head>
<body>
	<jsp:include page="nav.jsp" />
	<div class="container">
	<font color="red" >${message} </font>
	<table class="table table-bordered">
		<tr>
			<th>ログインＩＤ</th>
			<th>ニックネーム</th>
			<th>ステータス</th>
			<th>リンク</th>		</tr>
		<c:forEach items="${adminList}" var="dto">
			<tr>
				<td><c:out value="${dto.userid }"></c:out></td>
				<td><c:out value="${dto.nickname }"></c:out></td>
				<td><c:out value="${dto.rolename}"></c:out></td>

			<c:choose>
				<c:when test="${dto.userid != 'admin' }">
							<td>
							<form  action="./UserDetail" method="GET">
									<input type="hidden" name="userid" value="<c:out value="${dto.userid }" />" />
									<input type="submit" class="btn btn-success" value="更新削除" />
							</form>
							</td>
				</c:when>
				<c:when test="${dto.userid == 'admin' }">
							<td>
							<form  action="./UserDetail" method="GET">
									<input type="hidden" name="userid" value="<c:out value="${dto.userid }" />" />
									<input type="submit" class="btn btn-success" value="更新" />
							</form>
							</td>
				</c:when>
			</c:choose>
			</tr>
		</c:forEach>
	</table>
	<form id="sender" action="./UserInput" method="POST">
		<input type="hidden" name="roleid" value="<c:out value="1" />" />
		<input type="submit" class="btn btn-success" value="新規登録" />
	</form><br>
	<br>

	</div>
</body>
</html>