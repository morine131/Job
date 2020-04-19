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

<title>PDFアップロード画面</title>
<script type="text/javascript">

</script>
</head>
<body>
	<jsp:include page="nav.jsp" />
	<div class="container">
		アップロードするファイルを選択し、[アップロード]ボタンを押してください。
		<form name="upload" action="../upload" method="post"
			enctype="multipart/form-data">
			<input type="file" name="uploadfile" /><br>
			<input type="submit" class="btn btn-primary" value="アップロード" />
			<input type="hidden" name="jobid" value="<c:out value="${jobid}"/>" />
		</form>

		<br> <%-- <input type="submit" class="btn btn-success" value="キャンセル"
			onClick="location.href='${pageContext.request.contextPath}/index.jsp'" /> --%>
			<form  action="${pageContext.request.contextPath}/Servlet/JobSearch" method="GET">
				<input type="submit" class="btn btn-danger" value="キャンセル" />
			</form>
	</div>
</body>
</html>