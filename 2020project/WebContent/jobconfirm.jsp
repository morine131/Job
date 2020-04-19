<!-- 10章detail.jspを編集しjob_input readonly.jspとして作成 by高安,高橋　12/19 -->
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
			<c:if test="${commandMessage.equals('登録')}" >action="./JobRegister" </c:if>
			<c:if test="${commandMessage.equals('更新')}" >action="./JobUpdate" </c:if>
			<c:if test="${commandMessage.equals('削除')}" >action="./JobDelete" </c:if>
			method="POST">
			<input type="hidden" value="${dto.empid}" name="empid" />
			<input type="hidden" value="${dto.typeid}" name="typeid" />
			<input type="hidden" value="${dto.publish}" name="publish" />
			<div class="jobinput-table">
			<table class="table jobinput-form">
			<p>以下の内容を<c:out value="${commandMessage }" />します。よろしいでしょうか。</p>
				<tr>
					<th>求人票コード</th>
					<td><input readonly type="text" name="jobid"
						value="<c:out value="${dto.jobid }" />" size="20" /></td>
				</tr>
				<tr>
					<th>会社名</th>
					<td><input readonly type="text" name="company"
						value="<c:out value="${dto.company }" />" size="40" /></td>
				</tr>
				<tr>
					<th>郵便番号</th>
					<td><input readonly type="text" name="postcode"
						value="<c:out value="${dto.postcode }" />" maxlength="8" size="10" /></td>
				<tr>
					<th>給与</th>
					<td><input readonly type="text" name="salary1"
						value="<c:out value="${dto.salary1 }" />" size="10" /> ～ <input
						readonly type="text" name="salary2"
						value="<c:out value="${dto.salary2 }" />" size="10" /></td>
				</tr>
				<tr>
					<th>雇用形態</th>
					<td><input disabled type="text" name="disabled-empid"
					<c:choose>
							<c:when test="${dto.empid == 1 }">
								value='<c:out value="正社員" />'
							</c:when>
							<c:when test="${dto.empid == 2 }">
								value='<c:out value="契約社員" />'
							</c:when>
							<c:when test="${dto.empid == 3 }">
								value='<c:out value="派遣社員" />'
							</c:when>
							<c:when test="${dto.empid == 4 }">
								value='<c:out value="請負" />'
							</c:when>
					</c:choose> />
					</td>
				</tr>
				<tr>
					<th>職種</th>
					<td><input disabled type="text" name="disabled-typeid"
					<c:choose>
							<c:when test="${dto.typeid == 1 }">
								value='<c:out value="システムエンジニア" />'
							</c:when>
							<c:when test="${dto.typeid == 2 }">
								value='<c:out value="プログラマー" />'
							</c:when>
							<c:when test="${dto.typeid == 3 }">
								value='<c:out value="ネットワークエンジニア" />'
							</c:when>
					</c:choose> />
					</td>
				</tr>
				<tr>
					<th>公開設定</th>
					<td><input disabled type="text" name="disabled-publish"
					<c:choose>
							<c:when test="${dto.publish == 1 }">
								value='<c:out value="公開" />'
							</c:when>
							<c:when test="${dto.publish == 0 }">
								value='<c:out value="非公開" />'
							</c:when>
					</c:choose> />
					</td>
				</tr>
				<tr>
					<th>期限</th>
					<td><input readonly type="text" name="inputvalidperiod"

					<c:if test="${commandMessage.equals('登録')}" >value="<c:out value="${dto.inputvalidperiod }" />"  </c:if>
					<c:if test="${commandMessage.equals('更新')}" >value="<c:out value="${dto.inputvalidperiod }" />" </c:if>
					<c:if test="${commandMessage.equals('削除')}" >value="<c:out value="${dto.validperiod }"/>" </c:if>
					size="20" /></td>
				</tr>
				<tr>
					<th>勤務地</th>
					<td><input readonly type="text" name="workarea"
						value="<c:out value="${dto.workarea }" />" size="10" /></td>
				</tr>

			</table>
			</div>



			<%-- 	<input readonly type="hidden" name="id" value="<c:out value="${dto.jobid }" />" /> --%>
			<input  type="submit" name="submit" value="<c:out value='${commandMessage}' />"  class="btn btn-success table-btn inline-btn"/>
			<button type="button" class="btn btn-danger inline-btn" onclick="history.back()">修正</button>

		</form>
		<br>
<!-- 		<input readonly type="submit" class="btn btn-success" -->
<!-- 			value="キャンセル" onClick="location.href='index.jsp'" /> -->
	<form  action="${pageContext.request.contextPath}/Servlet/JobSearch" method="GET">
				<input type="submit" class="btn btn-danger table-btn" value="キャンセル" />
			</form>
	</div>
</body>
</html>