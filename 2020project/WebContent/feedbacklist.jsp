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
<title>就活体験談一覧</title>
</head>
<body>
<jsp:include page="nav.jsp" />
	<div class="container">
			<h4>
			<font color="red">${feedbackmessage} </font>
			 <% session.removeAttribute("feedbackmessage");%>
		</h4>

	<table class="table table-bordered">
	<h4><c:out value="${company }"></c:out></h4>
		<tr>
			<th>就活体験談内容</th>
			<th>ニックネーム</th>
			<th>更新日時</th>
			<th>ボタン表示欄</th>
		</tr>
		<c:forEach items="${feedbackList}" var="dto">
			<tr>
				<td><c:out value="${dto.fbcontent }"></c:out></td>
				<td><c:out value="${dto.nickname }"></c:out></td>
				<td><c:out value="${dto.fblastupdate }"></c:out></td>
				<td><form  action="http://localhost:8080/2020project/Servlet/FeedBackDetail" method="GET">
				<c:if test="${loginUser.roleid == 2 ||loginUser.roleid == 1 || loginUser.nickname == dto.nickname }">
						<input type="hidden" name="fbid" value="<c:out value="${dto.fbid }" />" />
						<input type="submit" class="btn btn-success" value="更新削除" />
				</c:if>

					</form></td>

<%-- 				<td><a href="detail?id=<c:out value="${dto.userid }"/>">詳細画面へ</a></td> --%>

			</tr>
		</c:forEach>
	</table>
	<form id="sender" action="http://localhost:8080/2020project/Servlet/FeedBackInput " >

			<input type="hidden" name="fbid" value="<c:out value="${dto.fbid}" />" />
			<input type="hidden" name="token" value="<c:out value="${token}" />" />
			<input type="hidden" name="jobid" value="<c:out value="${jobid}" />" />
			<input type="submit" class="btn btn-success" value="登録する" />
	</form><br>
	<form  action="${pageContext.request.contextPath}/Servlet/JobSearch" method="GET">
				<input type="submit" class="btn btn-danger" value="求人一覧に戻る" />
			</form>
	</div>



<!-- ここから破棄予定 -->
<%-- 	<jsp:include page="nav.jsp" /> --%>
<!-- 	<div class="container"> -->
<!-- <!-- 	就活体験談リスト -->
<!-- 	<table class="table table-bordered"> -->
<!--  		<tr> -->
<!--  			<th></th> -->
<!--  			<th>就活体験談内容</th> -->
<!-- 			<th>ニックネーム</th> -->
<!--  			<th>更新日時</th> -->
<!--  			<th>ボタン表示欄</th> -->
<!-- 		</tr> -->

<!-- <!-- ここから就活体験談内容表示 -->
<%-- 		<c:forEach items="${feedbackList}" var="dto"> --%>
<!-- 			<tr> -->
<%-- 				<td><c:out value="${dto.fbid }"></c:out></td> --%>
<%-- 				<td><c:out value="${dto.fbcontent }"></c:out></td> --%>
<%-- 				<td><c:out value="${dto.nickname }"></c:out></td> --%>
<%-- 				<td><fmt:formatDate value="${dto.fblastupdate }" pattern = "yyyy-MM-dd" /> --%>
<!-- 				<td> -->
<!-- <!-- 				ボタン機能一時コメントアウト(1/7) -->
<!-- 					<form id="sender" -->
<!--  					action="http://localhost:8080/2020project/Servlet/FeedBackDetail" -->
<!--  					method="GET"> -->
<%-- 					<c:choose > --%>
<%--  					<c:when test="${dto.fbid>0}"> --%>
<%-- 					<input type="hidden" name="id" value="<c:out value="${dto.fbid}" />" /> --%>
<%-- 					<input type="hidden" name="token" value="<c:out value="${token}" />" /> --%>
<!-- 					<input type="submit" class="btn btn-success" value="更新する/削除" /> -->
<%--  					</c:when> --%>
<%-- 					</c:choose> --%>
<!-- <!-- 					</form> -->
<!-- <!-- 						<input type="submit" class="btn btn-success" value="削除" /> -->
<!-- 					</form></td> -->
<!-- 			</tr> -->
<%-- 		</c:forEach> --%>
<!-- 	</table> -->

<!-- <!-- 就活体験談入力フォームへ遷移 -->
<!-- 	<form id="sender" -->
<!-- 			action="http://localhost:8080/2020project/Servlet/FeedBackInput" -->
<!-- 			method="GET" id="fbid"> -->
<%-- 		<c:choose > --%>
<%-- 		<c:when test="${dto.fbid>0}"> --%>
<%-- 			<input type="hidden" name="id" value="<c:out value="${dto.fbid}" />" /> --%>
<%-- <%-- 			<input type="hidden" name="token" value="<c:out value="${token}" />" /> --%>
<!-- 			<input type="submit" class="btn btn-success" value="更新する" /> -->
<%-- 		</c:when> --%>
<%-- 		<c:otherwise> --%>
<%-- 			<input type="hidden" name="id" value="<c:out value="${dto.fbid}" />" /> --%>
<%-- <%-- 			<input type="hidden" name="token" value="<c:out value="${token}" />" /> --%>
<!-- 			<input type="submit" class="btn btn-success" value="新規登録" /> -->
<%-- 		</c:otherwise> --%>
<%-- 		</c:choose> --%>
<!-- 	</form> -->
<!-- 	<br> -->
<!-- 		<form id="sender" action="http://localhost:8080/2020project/index.jsp" method="POST"> -->
<!-- 		<input type="submit" class="btn btn-success" value="キャンセル" /> -->
<!-- 	</form> -->
<!-- 	</div> -->
</body>
</html>