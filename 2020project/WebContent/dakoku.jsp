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
<jsp:include page="nav.jsp" />
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

			<div class="dakoku-step">
			<div class="dakoku-menu-bar">
			出勤
			</div>
			<div class="dakoku-menu-bar">
			気分を選択して退勤
			</div>
			</div>
			<div class="dakoku-step">
			<div class="dakoku-menu">
			<form class="start-btn" method="post" action="${pageContext.request.contextPath}/Start">
				<input type="hidden" :value="latitude" name="latitude">
				<input type="hidden" :value="longitude" name="longitude">
				<input class="start-btn"  v-bind:class="classObject" type="submit" value="出勤" v-bind:disabled="startDisabled">
			</form>
			</div>
			<div class="dakoku-menu">
			<form class="finish-btn" method="post" action="${pageContext.request.contextPath}/Finish">
				<input type="hidden" value="0" name="feeling">
				<input type="hidden" :value="latitude" name="latitude">
				<input type="hidden" :value="longitude" name="longitude">
				<input class="good-finish-btn" type="submit" value="" v-bind:disabled="disabledGetPositon">
			</form>
			<form class="finish-btn" method="post" action="${pageContext.request.contextPath}/Finish">
				<input type="hidden" value="1" name="feeling">
				<input type="hidden" :value="latitude" name="latitude">
				<input type="hidden" :value="longitude" name="longitude">
				<input class="normal-finish-btn" type="submit" value="" v-bind:disabled="disabledGetPositon">
			</form>
			<form class="finish-btn" method="post" action="${pageContext.request.contextPath}/Finish">
				<input type="hidden" value="2" name="feeling">
				<input type="hidden" :value="latitude" name="latitude">
				<input type="hidden" :value="longitude" name="longitude">
				<input class="bad-finish-btn" type="submit" value="" v-bind:disabled="disabledGetPositon">
			</form>
			</div>
			</div>
			<div class="dakoku-step">
			<form class="history_btn dakoku-menu-bottom " method="GET" action="${pageContext.request.contextPath}/History">
				<input class="btn  " type="submit" value="勤務表">
			</form>
			<form class="update-pass-btn dakoku-menu-bottom" method="GET" action="${pageContext.request.contextPath}/UpdatePassword">
				<input class="btn " type="submit" value="パスワード設定">
			</form>
			</div>
			<br>
			<!--  開発用のテストページ-->
			開発用
			<form class="update-pass-btn" method="GET" action="${pageContext.request.contextPath}/Test">
				<input class="btn " type="submit" value="打刻テスト">
			</form>
			<input class="btn " type="button" value="位置情報取得" @click="getPosition()">
			<input type="hidden" value="${start_btn_flg}" id="start_btn_flg">
		</div>
	</div>
	<script>
		new Vue({
			el : "#app",
			data : {
				latitude: 0,
				longitude: 0,
				isSmartPhpne : false,
				startDisabled :false,
				disabledGetPositon: false
			},
			methods : {
				getPosition : function(){
					navigator.geolocation.getCurrentPosition(this.successGetPosition,this.errorGetPosition);
				},
				successGetPosition : function(position){
					this.latitude = position.coords.latitude
					this.longitude = position.coords.longitude

				},
				errorGetPosition : function (argErr){
					this.disabledGetPositon = true
					this.startDisabled = true
					 switch(argErr.code){
			        case 1 :  alert ("位置情報の利用が許可されていません");break;
			        case 2 : alert("デバイスの位置が判定できません");break;
			        case 3 : alert("タイムアウトしました");break;
			    }
				},
				judgeSmartPhone : function() {
					  if (navigator.userAgent.match(/iPhone|Android.+Mobile/)) {
					    return true;
					  } else {
					    return false;
					  }
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
				},
				classObject: function () {
					    return {
					    	'startBtn btn ': ! this.startDisabled,
					    	'startiDisabled': this.startDisabled
					    }
				}
			},
			created : function(){
				const start_btn_flg = document.getElementById("start_btn_flg").value

				this.isSmartPhone = this.judgeSmartPhone()
				if(this.isSmartPhone){
					this.getPosition()
				}

				if(start_btn_flg === '1' ){
					this.startDisabled = true
				}
			}
		})
	</script>
</body>
</html>