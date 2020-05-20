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
<title>ログイン画面</title>
</head>
<body>
<div class="container">
<h2>勤務表ログイン</h2>
		<h4>
			<font color="red">${message} </font>
			<%
				session.removeAttribute("message");
			%>
		</h4>

	<form action="${pageContext.request.contextPath}/Login" method="post">
		<div class="login-form">
	    <input type="text" name="emp_id" placeholder="社員ID">
	    <br/>
	   	<input type="password" name="pass" placeholder = "パスワード">
		</div>
	    <br/>

	    <input type="submit" class="btn btn-info" value="ログイン">

	</form>
</div>
</body>
</html>