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

 <!--/* ログイン失敗/ログアウトはgetパラメータにステータスが渡されているので、Thymeleafではparamから取得 */-->
		<h4>
			<font color="red">${message} </font>
			<%
				session.removeAttribute("message");
			%>
		</h4>

    <!--/* 「/sign_in」がログイン判定処理を呼び出すViewNameとなっている */-->
	<form action="${pageContext.request.contextPath}/Login" method="post">
	    <input type="text" name="emp_id">社員ID
	    <br/>
	    <input type="password" name="pass">パスワード

	    <br/>

	    <input type="submit" value="ログイン">
	</form>
</body>
</html>