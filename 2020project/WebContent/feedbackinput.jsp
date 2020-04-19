<!-- 1/8高橋作成 -->



<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="header.jsp" />
<title>就活体験談入力画面</title>
<script type="text/javascript">
	window.onload = function() {
		var status = document.getElementById("fbid");
		status.selectedIndex = $
		{
			dto.fbid
		}
		;
	};
</script>
</head>
<body>
	<jsp:include page="nav.jsp" />

	<div class="container">
		<h2>就活体験談入力</h2>
		<h4><c:out value="${company }"></c:out></h4>
		<form id="sender"
			<c:if test="${dto.fbid == 0 }" >action="${pageContext.request.contextPath}/Servlet/FeedBackRegister" </c:if>
			<c:if test="${dto.fbid > 0}" >action="${pageContext.request.contextPath}/Servlet/FeedBackUpdate" </c:if>
			method="GET">
			<table class="table">
				<tr>
					<th>就活体験談</th>
					<td><textarea name="fbcontent" cols="50" rows="4" required><c:out
								value="${dto.fbcontent }" /></textarea></td>
					<!-- 			textareaで送信できない場合text入力欄残しておく（高橋） -->
					<%-- 			<input type="text" name="fbcontent" value="<c:out value="${feedback_list_Beans.fbcontent }" />" maxlength="100" size="80"  required /> --%>
				</tr>
				<tr>
					<th>ニックネーム</th>
					<td><input readonly type="text" name="nickname"

						<c:if test="${dto.nickname != null }">
						value="<c:out value="${dto.nickname }" />"
					 	</c:if>
						<c:if test="${dto.nickname == null }">
						value="<c:out value="${loginUser.nickname }" />"
					 	</c:if>

						maxlength="8" size="10" /></td>
				</tr>
			</table>

			<c:choose>
				<c:when test="${dto.fbid>0}">

				<%-- 	<input type="hidden" name="nickname"
						<c:if test="${dto.nickname != null }">
						value="<c:out value="${dto.nickname }" />"
					 	</c:if>
						<c:if test="${dto.nickname == null }">
						value="<c:out value="${loginUser.nickname }" />"
					 	</c:if> /> --%>

					<input type="hidden" name="userid"
						value="<c:out value="${dto.userid }" />" />

					<input type="hidden" name="jobid"
						value="<c:out value="${dto.jobid}" />" />
					<input type="hidden" name="fbid"
						value="<c:out value="${dto.fbid}" />" />
					<input type="submit" class="btn btn-success" value="更新する" />
				</c:when>
				<c:otherwise>
					<input type="hidden" name="jobid"
						value="<c:out value="${jobid}" />" />
					<input type="hidden" name="userid"
						value="<c:out value="${loginUser.userid}" />" />
					<input type="submit" class="btn btn-success" value="登録する" />
				</c:otherwise>
			</c:choose>
		</form>

		<br>
		<!-- fbidが１以上の時削除ボタン出現? -->
		<c:if test="${dto.fbid > 0}">
			<form id="delete" action="http://localhost:8080/2020project/Servlet/FeedBackDelete" method="GET">
			<input type="hidden" name="fbid" value="<c:out value="${dto.fbid}" />" />
			<input type="hidden" name="fbcontent" value="<c:out value="${dto.fbcontent }" />" />
			<input type="hidden" name="jobid" value="<c:out value="${dto.jobid }" />" />
			<input type="hidden" name="userid" value="<c:out value="${dto.userid }" />" />
			<input type="hidden" name="nickname" value="<c:out value="${dto.nickname }" />" />
			<input type="submit" class="btn btn-warning" value="削除する" />
			</form>
		</c:if>

		<!-- キャンセルボタンから体験談一覧へ戻る -->
	<button type="button" class="btn btn-danger" onclick="history.back()">戻る</button>
	</div>
</body>
</html>