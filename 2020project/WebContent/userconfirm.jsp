<%---------------------------------------------------------------------------------------
	処理概要：
	  ・ユーザ系DB更新処理／ログアウト時の実行前最終確認画面を表示する
	    スコープから処理種別（commandMessage）を取得し、表示する確認画面を決定する
	  ・DB更新系処理の場合は、POSTで呼び出し元にアクションを返却する

		ログアウト ： ログアウト処理   ログインID、ニックネーム

		更新 ： フル表示    ログインID、ニックネーム、パスワード、メールアドレス、
	                        希望職種、希望地域、ステータス名称（IDから名称を取得）
		登録 ： フル表示         〃
		削除 ： フル表示？

	作成者 : 市橋  2020/01/09
---------------------------------------------------------------------------------------%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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


	<%-- 表示する確認画面 -------------------------------------------------------------------------------------%>

	<c:choose>

		<%-- ログアウト処理の場合 --%>
		<c:when test="${commandMessage.equals('ログアウト')}">

			<p class="text-danger"> <c:out value="${commandMessage }" />します。よろしいでしょうか。</p>

			<form action="${pageContext.request.contextPath}/Logout" method="post">
				<table class="table">
					<tr>	<th>ログインＩＤ</th>	<td>${loginUser.userid}		</td>	</tr>
					<tr>	<th>ニックネーム</th>	<td>${loginUser.nickname}	</td>	</tr>
				</table>

				<input type="submit" name="submit" value="実行" class="btn btn-success"/>

<%-- 				<input type="submit" name="submit" value="<c:out value='${commandMessage}' />" class="btn btn-success"/> --%>
				<button type="button" class="btn btn-danger" onClick="history.back()">キャンセル</button>
			</form>
		</c:when>


		<%-- ユーザ新規登録／更新／削除処理の場合 --%>
		<c:otherwise>

			<p class="text-danger">以下の内容を<c:out value="${commandMessage }" />します。よろしいでしょうか。</p>

			<form
				<c:if test="${commandMessage.equals('登録')}" >action="${pageContext.request.contextPath}/Servlet/UserRegister?action=exec" </c:if>
				<c:if test="${commandMessage.equals('更新')}" >action="${pageContext.request.contextPath}/Servlet/UserRegister?action=exec" </c:if>
				<c:if test="${commandMessage.equals('削除')}" >action="${pageContext.request.contextPath}/Servlet/UserDelete?action=exec" </c:if>
			method="post">
				<table class="table">  <%-- リクエスト属性からユーザ情報を取得する --%>
					<tr> <td>ログインＩＤ	</td>	<td>${dto.userid}	</td>	</tr>
					<c:if test="${commandMessage != '削除' }">
					<tr> <td>ニックネーム	</td>	<td>${dto.nickname }</td>	</tr>
					<tr> <td>パスワード		</td>	<td>${dto.password }</td>	</tr>
					<tr> <td>メールアドレス	</td>	<td>${dto.mail }	</td>	</tr>
					<tr>
						<td>希望職種</td>
						<td>
							<c:choose>
								<c:when test="${dto.typeid == 1 }">プログラマ				</c:when>
								<c:when test="${dto.typeid == 2 }">システムエンジニア		</c:when>
								<c:when test="${dto.typeid == 3 }">ネットワークエンジニア	</c:when>
								<c:when test="${dto.typeid == 4 }">運用保守					</c:when>
								<c:when test="${dto.typeid == 5 }">サポート					</c:when>
								<c:when test="${dto.typeid == 6 }">その他					</c:when>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td>希望地域</td>
						<td>${dto.area }</td>
					</tr>
					</c:if>
					<tr>
						<td>ステータス</td>
						<td>
							<c:choose>
								<c:when test="${dto.roleid == 1 }">スーパー管理者</c:when>
								<c:when test="${dto.roleid == 2 }">一般利用者</c:when>
								<c:when test="${dto.roleid == 3 }">利用者</c:when>
							</c:choose>
						</td>
					</tr>
				</table>

				<input type="hidden" name="token" value="${token}">

				<input type="submit" name="submit" value="実行" class="btn btn-success">
				<button type="button" class="btn btn-success" onClick="history.back()">修正</button>
				<button type="button" class="btn btn-success"
					onClick="location.href='${pageContext.request.contextPath}/${referer}'">キャンセル</button>

			</form>
		</c:otherwise>
	</c:choose>

	</div>

</body>
</html>