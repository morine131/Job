<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">求人票検索システム</a>
		</div>


		<div id="navbar"  class="navbar-collapse collapse">
			<ol class="nav navbar-nav">

			<li><a href="${pageContext.request.contextPath}/index.jsp">TOPに戻る</a></li>
			<!-- S管理者（ roleid = 1 ）のみ表示 -->
			<c:if test="${loginUser.roleid == 1 }">
				<li><a href="${pageContext.request.contextPath}/Servlet/AdminSearch">管理者アカウント管理</a></li>
			</c:if>

			<!-- 管理者（ roleid = 2 ）のみ表示 -->
			<c:if test="${loginUser.roleid == 2 }">
				<li><a href="${pageContext.request.contextPath}/Servlet/UserDetail?userid=${loginUser.userid}">マイアカウント管理</a></li>
			</c:if>

			<!-- 利用者（ roleid = 3 ）のみ表示 -->
			<c:if test="${loginUser.roleid == 3 }">
				<li><a href="${pageContext.request.contextPath}/Servlet/UserDetail">マイアカウント管理</a></li>
			</c:if>

			<!-- S管理者、管理者（ roleid = 1 または 2 ）のみ表示 -->
			<c:if test="${loginUser.roleid < 3 }">
				<li><a href="${pageContext.request.contextPath}/Servlet/MemberSearch">利用者アカウント管理</a></li>
			</c:if>

			</ol>
			<ol class="nav navbar-nav navbar-right">
				<li><c:choose>
						<c:when test="${loginUser.nickname == null }" >
							<a href="${pageContext.request.contextPath}/Login">ログイン/新規作成</a>
						</c:when>
						<c:otherwise>
							<a href="${pageContext.request.contextPath}/Logout">${loginUser.nickname}さん　ログアウト</a>
						</c:otherwise>
					</c:choose>
				</li>
			</ol>
		</div>
	</div>
</nav>