<!-- 作成者　荒井、安西　12/18　10章のdetailを参考にした -->



<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Chache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<title>利用者入力画面</title>
<jsp:include page="header.jsp"/>
<script type="text/javascript">
window.onload = function(){
	var status = document.getElementById("status");
	status.selectedIndex = ${dto.status};
};
</script>
</head>
<body>
<jsp:include page="nav.jsp" />
<div class="container">
	<form id="sender" action="register" method="POST">
	<table class="table">
		<tr>
			<th>番号</th>
			<td>
				<c:choose>
					<c:when test="${dto.id > 0 }">
						<c:out value="${dto.id}" />
					</c:when>
					<c:otherwise>
						（新規)
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<th>ログインＩＤ</th>
			<td><input type="text" name="userid" value="<c:out value="${user_info_Beans.userid }" />" size="20" /></td>
		</tr>
		<tr>
			<th>ニックネーム</th><td><input type="text" name="nickname" value="<c:out value="${user_info_Beans.nickname }" />" size="40" /></td>
		</tr>
		<tr>
			<th>パスワード</th><td><input type="text" name="password" value="<c:out value="${user_info_Beans.password }" />" maxlength="8" size="20" /></td>

		</tr>
		<tr>
			<th>メールアドレス</th>
			<td><input type="text" name="mail" value="<c:out value="${user_info_Beans.mail }" />" size="60"/></td>
		</tr>
		<tr>
			<th>希望職種</th>
			<td>
				<select name="job_type" id="job_type">
					<option value="0">システムエンジニア</option>
					<option value="1">ネットワークエンジニア</option>
					<option value="2">プログラマ</option>
					<option value="3">その他</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>希望地域</th>
			<td>
				<select name="area" id="area">
					<option value="0">千葉</option>
					<option value="1">東京</option>
					<option value="2">その他</option>
				</select>

			</td>
		</tr>
		<c:if test="${user_info_Beans.id != 0 }">
		</c:if>
	</table>

<%-- 	<input type="hidden" name="id" value="<c:out value="${user_info_Beans.id }" />" /> --%>
<%-- 	<input type="hidden" name="token" value="<c:out value="${token}" />" /> --%>
	<input type="submit" class="btn btn-success" value="登録する" />
	</form>
	<c:if test="${user_info_Beans.id > 0}" >
	<br />
	<form id="delete" action="delete" method="POST">
<%-- 		<input type="hidden" name="id" value="<c:out value="${user_info_Beans.id }" />" /> --%>
<%-- 		<input type="hidden" name="token" value="<c:out value="${token}" />" /> --%>
		<input type="submit" class="btn btn-warning" value="削除する" />
	</form>
	</c:if><br>
	<input type="submit" class="btn btn-success" value="キャンセル" />
</div>
</body>
</html>