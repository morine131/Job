<!-- 10章detail.jspを編集しjob_input readonly.jspとして作成 by高安,高橋　12/19         ←を元に就活体験談分作成中 1/8-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Chache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<title>確認画面</title>
<jsp:include page="header.jsp" />

</head>
<body>
	<jsp:include page="nav.jsp" />
	<div class="container">
		<form id="sender"
			<c:if test="${commandMessage.equals('登録')}" >action="./FeedBackRegister" </c:if>
			<c:if test="${commandMessage.equals('更新')}" >action="./FeedBackUpdate" </c:if>
			<c:if test="${commandMessage.equals('削除')}" >action="./FeedBackDelete" </c:if>
			method="POST">
			<input type="hidden" name="jobid" value="<c:out value="${jobid}" />" />
			<input type="hidden" name="userid" value="<c:out value="${userid}" />" />
				<input type="hidden" name="fbid" value="<c:out value="${fbid}" />" />
			<table class="table">
			<h5><font color="red"> 以下の内容を<c:out  value="${commandMessage }" />します。よろしいでしょうか。 </font></h5>
			<h4><c:out value="${company }"></c:out></h4>
				<tr>
				<%-- 	<th>就活体験談ID</th>
					<td><input readonly type="text" name="fbid"
						value="<c:out value="${fbid }" />" size="20" /></td>
				</tr> --%>
				<tr>
					<th>就活体験談</th>
					<td><input readonly type="text" name="fbcontent"
						value="<c:out value="${fbcontent }" />" size="40" /></td>
				</tr>
				<tr>
					<th>ニックネーム</th>
					<td><input readonly type="text" name="nickname"
						value="<c:out value="${nickname }" />" maxlength="8" size="10" /></td>
				</tr>
		</table>



			<%-- 	<input readonly type="hidden" name="id" value="<c:out value="${dto.fbid }" />" /> --%>
			<input  type="submit" name="submit" value="<c:out value='${commandMessage}' />"  class="btn btn-success"/>

		</form>
		<br><button type="button" class="btn btn-danger" onclick="history.back()">修正</button>
	</div>
</body>
</html>