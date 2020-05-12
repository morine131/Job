<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	<a class="navbar-brand" href="${pageContext.request.contextPath}/Home">株式会社インターソフト
		WEB勤務表</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarNav" aria-controls="navbarNav"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarNav">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item "><a class="nav-link"
				href="${pageContext.request.contextPath}/Home"><span class="nav-gray">打刻</span></a></li>
			<li class="nav-item "><a class="nav-link"
				href="${pageContext.request.contextPath}/History"><span class="nav-gray">勤務表確認</span></a></li>
			<li class="nav-item "><a class="nav-link"
				href="${pageContext.request.contextPath}/UpdatePassword"><span class="nav-gray">パスワード設定</span></a>
			</li>
		</ul>
		<span class="nav-name"> ${user_name} </span>
		<form class="logout-btn" method="post"
			action="${pageContext.request.contextPath}/Logout">
			<input class="btn btn-sm btn-light" type="submit" value="ログアウト">
		</form>

	</div>
</nav>