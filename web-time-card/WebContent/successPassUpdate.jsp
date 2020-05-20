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
<title>パスワード修正完了画面</title>
</head>
<body>
<c:if test="${ user_type == '1' || user_type == '2' }"><jsp:include page="nav.jsp" /></c:if>
<div class="container">
	<h2>修正完了</h2>

	<div>
		<span>パスワードの修正が完了しました</span>
	</div>
	<form class="start-btn" method="get" action="${pageContext.request.contextPath}/Home">
		<input class="btn btn-secondary" type="submit" value="戻る">
	</form>
</div>
</body>
</html>