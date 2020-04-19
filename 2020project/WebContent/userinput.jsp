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
<title>管理者入力画面</title>
<jsp:include page="header.jsp"/>

</head>
<body>
<jsp:include page="nav.jsp" />
<div class="container">
<font color="red" >${message} </font>
	<form id="sender" action="./UserRegister" method="POST">
	<table class="table">
	<c:choose>
		<c:when test="${dto.userid == null }">
		<tr>
			<th>ログインＩＤ<font color="red">※必須</font></th>
			<td><input type="text" name="userid" value="<c:out value="${dto.userid }" />" size="20" /></td>
		</tr>
		</c:when>
		<c:when test="${dto.userid != null }">
		<tr>
			<th>ログインＩＤ<font color="red">※更新できません</font></th>
			<td><input type="text" name="userid" value="<c:out value="${dto.userid}"/>" readonly /></td>
		</tr>
		</c:when>
	</c:choose>
		<tr>
			<th>ニックネーム<font color="red">※必須</font></th><td><input type="text" name="nickname" value="<c:out value="${dto.nickname }" />" size="40" /></td>
		</tr>
		<tr>
			<th>パスワード<font color="red">※必須</font></th><td><input type="password" name="password" value="<c:out value="${dto.password }" />" maxlength="8" size="20" /></td>

		</tr>

	<c:if test="${dto.roleid == 3 }">
		<tr>
			<th>メールアドレス<font color="red">※必須</font></th><td><input type="text" name="mail" value="<c:out value="${dto.mail }" />" maxlength="100" size="40" /></td>
		</tr>
		<tr>
			<th>希望職種</th>
			<td>
				<select name="typeid" id="typeid">
					<option value="1">プログラマ</option>
					<option value="2">システムエンジニア</option>
					<option value="3">ネットワークエンジニア</option>
					<option value="4">運用保守</option>
					<option value="5">サポート</option>
					<option value="6">その他</option>
				</select>

				<script type="text/javascript">
					document.getElementById('typed').options[<c:out value="${dto.typeid - 1}" />].selected = true;
				</script>

			</td>
		</tr>
		<tr>
			<th>希望地域</th><td><input type="text" name="area" value="<c:out value="${dto.area }" />" maxlength="4" size="20" /></td>

		</tr>
	</c:if>

<!-- スーパー管理者や一般管理者だったらtypeidは"0"を送る -->
	<c:if test="${loginUser.roleid < 3 }">
		<input type="hidden" name="typeid" value="0" />
	</c:if>

<!-- スーパー管理者の場合はステータスを変更できる。一般管理者や利用者は変更できない。 -->
	<c:choose>
		<c:when test="${dto.roleid == 3 }">
			<tr><th>ステータス<font color="red">※更新できません</font></th>
				<td>
					<select name="roleid" id="roleid">
						<option value="3">利用者</option>
					</select>
				</td>
			</tr>
		</c:when>		<c:when test="${loginUser.roleid == 1 }">
			<tr><th>ステータス<font color="red">※必須</font></th>
				<td>
					<select name="roleid" id="roleid">
						<option value="1">スーパー管理者</option>
						<option value="2">一般管理者</option>
					</select>
						<script type="text/javascript">
							document.getElementById('roleid').options[<c:out value="${dto.roleid - 1}" />].selected = true;
						</script>
				</td>
			</tr>
		</c:when>
		<c:when test="${loginUser.roleid == 2 }">
			<tr><th>ステータス<font color="red">※更新できません</font></th>
				<td>
					<select name="roleid" id="roleid">
						<option value="2">一般管理者</option>
					</select>
				</td>
			</tr>
		</c:when>

	</c:choose>

<!-- 利用者の新規作成はroleidは"3"を送る -->
	<c:if test="${loginUser.roleid == null }">
		<input type="hidden" name="roleid" value="3" />
	</c:if>

	</table>
	<c:if test="${dto.userid == null}" >
		<input type="hidden" name="userid" value="<c:out value="${dto.userid }" />" />
		<input type="hidden" name="token" value="登録" />
		<input type="submit" class="btn btn-success" value="登録する" />
	</c:if><br>
	<c:if test="${dto.userid != null}" >
		<input type="hidden" name="userid" value="<c:out value="${dto.userid }" />" />
		<input type="hidden" name="token" value="更新" />
		<input type="submit" class="btn btn-success" value="更新する" />
	</c:if><br>
	</form>

<%-- 	<c:if test="${dto.userid != null}" > --%>
		<br />
		<c:choose>
			<c:when test="${dto.userid != 'admin'}" >
				<form id="delete" action="./UserDelete" method="POST">
					<input type="hidden" name="userid" value="<c:out value="${dto.userid }" />" />
					<input type="hidden" name="roleid" value="<c:out value="${dto.roleid }" />" />
					<input type="hidden" name="token" value="削除" />
					<input type="submit" class="btn btn-warning" value="削除する" />
				</form>
			</c:when>
			<c:when test="${dto.userid == 'admin'}" >
					adminの削除はできません
			</c:when>
		</c:choose>
<%-- 	</c:if><br> --%>

	<%-- ★変更しています --%>
	<!-- <input type="submit" class="btn btn-success" value="キャンセル" /> -->
	<button type="button" class="btn btn-success" onClick="history.back()">キャンセル</button>

</div>
</body>
</html>