<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="header.jsp"/>
<title>打刻画面</title>
</head>
<body>
	<div class="container" id="app">
		<h2>インターソフト WEB勤務表</h2>
		<div>
			<span class="user-name">社員名： ${ user_name}
				さん
			</span>

			<form class="logout-btn" method="post" action="${pageContext.request.contextPath}/Logout">
				<input class="btn btn-sm btn-outline-secondary" type="submit" value="ログアウト">
			</form>
			<div class="date">{{ date }}</div>

			<form class="start-btn" method="post" action="${pageContext.request.contextPath}/Start">
				<input type="hidden" :value="latitude" name="latitude">
				<input type="hidden" :value="longitude" name="longitude">
				<input class="btn menu-btn" type="submit" value="出勤" <c:if test="${start_btn_flg == '1'}">disabled</c:if> >
			</form>
			気分を選択して退勤
			<form class="finish-btn" method="post" action="${pageContext.request.contextPath}/Finish">
				<input type="hidden" value="0" name="feeling">
				<input type="hidden" :value="latitude" name="latitude">
				<input type="hidden" :value="longitude" name="longitude">
				<input class="btn btn-success" type="submit" value="アイコン">
			</form>
			<form class="finish-btn" method="post" action="${pageContext.request.contextPath}/Finish">
				<input type="hidden" value="1" name="feeling">
				<input type="hidden" :value="latitude" name="latitude">
				<input type="hidden" :value="longitude" name="longitude">
				<input class="btn btn-warning" type="submit" value="アイコン">
			</form>
			<form class="finish-btn" method="post" action="${pageContext.request.contextPath}/Finish">
				<input type="hidden" value="2" name="feeling">
				<input type="hidden" :value="latitude" name="latitude">
				<input type="hidden" :value="longitude" name="longitude">
				<input class="btn btn-danger" type="submit" value="アイコン">
			</form>
			<form class="history_btn" method="GET" action="${pageContext.request.contextPath}/History">
				<input class="btn menu-btn" type="submit" value="勤務表">
			</form>
			<form class="update-pass-btn" method="GET" action="${pageContext.request.contextPath}/UpdatePassword">
				<input class="btn menu-btn" type="submit" value="パスワード設定">
			</form>
			<br>
			<!--  開発用のテストページ-->
			開発用
			<form class="update-pass-btn" method="GET" action="${pageContext.request.contextPath}/Test">
				<input class="btn " type="submit" value="打刻テスト">
			</form>
			<input class="btn " type="button" value="位置情報取得" @click="getPosition()">
		</div>
	</div>
	<script>
		new Vue({
			el : "#app",
			data : {
				latitude: 0,
				longitude: 0
			},
			methods : {
				getPosition : function(){
					navigator.geolocation.getCurrentPosition(this.successGetPosition);
					console.log("getPosition")
				},
				successGetPosition : function(position){
					this.latitude = position.coords.latitude
					this.longitude = position.coords.longitude
				}
			},
			computed : {
				date : function() {
					const now = new Date();
					const year = now.getFullYear();
					const mon = now.getMonth() + 1; //１を足さないとずれる
					const day = now.getDate();
					const dayOfWeek = now.getDay(); // 曜日(数値)
					const dayArray = [ "日", "月", "火", "水", "木", "金", "土", "日" ]
					const youbi = dayArray[dayOfWeek]
					//出力用
					const dayStr = year + "年" + mon + "月" + day + "日" + "("
							+ youbi + ")";
					return dayStr
				}
			},
			created : function(){
				this.getPosition()
			}
		})
	</script>
</body>
</html>