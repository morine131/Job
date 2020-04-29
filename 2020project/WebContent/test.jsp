<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="header.jsp" />
<title>開発テスト画面</title>
</head>
<body>
<div id="app" class="container">
			<span class="user-name">社員名： ${ user_name}
				さん
			</span>
	<form class="start-btn" method="get" action="${pageContext.request.contextPath}/Home">
		<input class="btn btn-secondary" type="submit" value="戻る">
	</form>
	<br>
	<form class="start-btn" method="post"
		action="${pageContext.request.contextPath}/Test">

		<h3>出勤テスト打刻</h3>
		年:<input type="text" name="year"><br>
		月:<input type="text" name="month"><br>
		日: <input type="text"name="day"><br>
		時: <input type="text" name="hour"><br>
		分:<input type="text" name="minute"><br>
						<input type="hidden" :value="latitude" name="latitude">
				<input type="hidden" :value="longitude" name="longitude">
		<input class="btn btn-primary" type="submit" value="出勤"><br>
	</form>
	<br>
	<br>
	<br>
	<h3>退勤テスト打刻</h3>
	<br>
		年:<input type="text" name="year" v-model="year"><br>
		月:<input type="text" name="month" v-model="month"><br>
		日: <input type="text"name="day" v-model="day"><br>
		時: <input type="text" name="hour" v-model="hour"><br>
		分:<input type="text" name="minute" v-model="minute"><br>

		<form class="finish-btn" method="post"
				action="${pageContext.request.contextPath}/Test">
								<input type="hidden" :value="latitude" name="latitude">
				<input type="hidden" :value="longitude" name="longitude">
				<input type="hidden" value="0" name="feeling">
				<input type="hidden" :value="year" name="year">
				<input type="hidden" :value="month" name="month">
				<input type="hidden" :value="day" name="day">
				<input type="hidden" :value="hour" name="hour">
				<input type="hidden" :value="minute" name="minute">
 		<input class="btn btn-success" type="submit" value="良好"><br>
 		</form>
 				<form class="finish-btn" method="post"
				action="${pageContext.request.contextPath}/Test">
								<input type="hidden" :value="latitude" name="latitude">
				<input type="hidden" :value="longitude" name="longitude">
				<input type="hidden" :value="year" name="year">
				<input type="hidden" :value="month" name="month">
				<input type="hidden" :value="day" name="day">
				<input type="hidden" :value="hour" name="hour">
				<input type="hidden" :value="minute" name="minute">
		<input type="hidden" value="1" name="feeling"> <input
			class="btn btn-warning" type="submit" value="普通"><br>
			</form>
					<form class="finish-btn" method="post"
				action="${pageContext.request.contextPath}/Test">
								<input type="hidden" :value="latitude" name="latitude">
				<input type="hidden" :value="longitude" name="longitude">
				<input type="hidden" :value="year" name="year">
				<input type="hidden" :value="month" name="month">
				<input type="hidden" :value="day" name="day">
				<input type="hidden" :value="hour" name="hour">
				<input type="hidden" :value="minute" name="minute">
				<input type="hidden" value="2" name="feeling"> <input
			class="btn btn-danger" type="submit" value="イマイチ">
		</form>

	<br>
	<br>
	</div>
	<script>
		new Vue({
			el : "#app",
			data : {
				latitude: 0,
				longitude: 0,
				year: null,
				month: null,
				day: null,
				hour:null,
				minute: null
			},
			methods : {
				getPosition : function(){
					navigator.geolocation.getCurrentPosition(this.successGetPosition);
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