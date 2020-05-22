<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="header.jsp" />
<title>打刻画面</title>
</head>
<body>
	<jsp:include page="nav.jsp" />
	<div class="container" id="app">
		<div>
			<div class="date">{{ date }}</div>
			<div class="dakoku-step">
				<div class="dakoku-menu">
					<div class="dakoku-menu-bar">出勤</div>
					<form class="start-btn" method="post"
						action="${pageContext.request.contextPath}/Start">
						<input type="hidden" :value="latitude" name="latitude"> <input
							type="hidden" :value="longitude" name="longitude"> <input
							class="start-btn" v-bind:class="classObject" type="submit"
							value="出勤" v-bind:disabled="startDisabled">
					</form>
				</div>
				<div class="dakoku-menu">
					<div class="dakoku-menu-bar">気分を選択して退勤</div>
					<%-- <form class="finish-btn" method="post"
						action="${pageContext.request.contextPath}/Finish">
						<input type="hidden" value="${exist }" name="exist">
						<input type="hidden" value="0" name="feeling">
						<input type="hidden" :value="latitude" name="latitude">
						<input type="hidden" :value="longitude" name="longitude">
						<input class="good-finish-btn" type="submit" value=""
							v-bind:disabled="finishDisabled"
							v-on:mouseover="mouseOverGood" v-on:mouseleave="mouseLemoveGood">
					</form> --%>
<%-- 					<form class="finish-btn" method="post"
						action="${pageContext.request.contextPath}/Finish">
						<input type="hidden" value="${exist }" name="exist"> <input
							type="hidden" value="1" name="feeling"> <input
							type="hidden" :value="latitude" name="latitude"> <input
							type="hidden" :value="longitude" name="longitude"> <input
							class="normal-finish-btn" type="submit" value=""
							v-bind:disabled="finishDisabled" v-on:mouseover="mouseOverNormal"
							v-on:mouseleave="mouseLemoveNormal">
					</form>
					<form class="finish-btn" method="post"
						action="${pageContext.request.contextPath}/Finish">
						<input type="hidden" value="${exist }" name="exist"> <input
							type="hidden" value="2" name="feeling"> <input
							type="hidden" :value="latitude" name="latitude"> <input
							type="hidden" :value="longitude" name="longitude"> <input
							class="bad-finish-btn" type="submit" value=""
							v-bind:disabled="finishDisabled" v-on:mouseover="mouseOverBad"
							v-on:mouseleave="mouseLemoveBad">
					</form> --%>
					<div v-on:mouseover="mouseOverGood" v-on:mouseleave="mouseLemoveGood" v-on:click="goodFinish"
						class="finish-svg" style="width:90px; height:90px; background-image:url(./images/good.svg); background-size:100%;"></div>
					<div v-on:mouseover="mouseOverNormal" v-on:mouseleave="mouseLemoveNormal" v-on:click="normalFinish"
						class="finish-svg" style="width:90px; height:90px; background-image:url(./images/normal.svg); background-size:100%;"></div>
					<div v-on:mouseover="mouseOverBad" v-on:mouseleave="mouseLemoveBad" v-on:click="badFinish"
						class="finish-svg" style="width:90px; height:90px; background-image:url(./images/bad.svg); background-size:100%;"></div>
					<div>
						<div class="balloon2-top good" v-if="goodSerif">
							<p>良好</p>
						</div>
						<div class="balloon2-top normal" v-if="normalSerif">
							<p>普通</p>
						</div>
						<div class="balloon2-top bad" v-if="badSerif">
							<p>イマイチ</p>
						</div>
					</div>
				</div>
			</div>
		</div>
		<input type="hidden" value="${start_btn_flg}" id="start_btn_flg">
		<input type="hidden" value="${exist }" id="exist">
	</div>
	<script>
		new Vue(
				{
					el : "#app",
					data : {
						latitude : 0,
						longitude : 0,
						isSmartPhpne : false,
						startDisabled : false,
						finishDisabled : false,
						goodSerif : false,
						normalSerif : false,
						badSerif : false,
						width : 0,
						exist : null
					},
					methods : {
						goodFinish : function(){
							const data = {'exist':this.exist, 'feeling':0 , 'latitude':this.latitude,'longitude':this.longitude}
							this.execPost(data)
						},
						normalFinish : function(){
							const data = {'exist':this.exist, 'feeling':1 , 'latitude':this.latitude,'longitude':this.longitude}
							this.execPost(data)
						},
						badFinish : function(){
							const data = {'exist':this.exist, 'feeling':2 , 'latitude':this.latitude,'longitude':this.longitude}
							this.execPost(data)
						},
						execPost : function(data){
								 // フォームの生成
								 var form = document.createElement("form");
								 form.setAttribute("action","/web-time-card/Finish" );
								 form.setAttribute("method", "post");
								 form.style.display = "none";
								 document.body.appendChild(form);
								 // パラメタの設定
								  for (var paramName in data) {
								   var input = document.createElement('input');
								   input.setAttribute('type', 'hidden');
								   input.setAttribute('name', paramName);
								   input.setAttribute('value', data[paramName]);
								   form.appendChild(input);
								  }
								 // submit
								 form.submit();
						},
						mouseOverGood : function() {
							this.goodSerif = true
						},
						mouseLemoveGood : function() {
							this.goodSerif = false
						},
						mouseOverNormal : function() {
							this.normalSerif = true
						},
						mouseLemoveNormal : function() {
							this.normalSerif = false
						},
						mouseOverBad : function() {
							this.badSerif = true
						},
						mouseLemoveBad : function() {
							this.badSerif = false
						},
						getPosition : function() {
							navigator.geolocation.getCurrentPosition(
									this.successGetPosition,
									this.errorGetPosition);
						},
						successGetPosition : function(position) {
							this.latitude = position.coords.latitude
							this.longitude = position.coords.longitude

						},
						errorGetPosition : function(argErr) {
							this.finishDisabled = true
							this.startDisabled = true
							switch (argErr.code) {
							case 1:
								alert("位置情報の利用が許可されていません");
								break;
							case 2:
								alert("デバイスの位置が判定できません");
								break;
							case 3:
								alert("タイムアウトしました");
								break;
							}
						},
						judgeSmartPhone : function() {
							if (navigator.userAgent
									.match(/iPhone|Android.+Mobile/)) {
								return true;
							} else {
								return false;
							}
						},
						handleResize : function() {
							this.width = window.innerWidth;
						}
					},
					computed : {
						date : function() {
							const now = new Date();
							const year = now.getFullYear();
							const mon = now.getMonth() + 1; //１を足さないとずれる
							const day = now.getDate();
							const dayOfWeek = now.getDay(); // 曜日(数値)
							const dayArray = [ "日", "月", "火", "水", "木", "金",
									"土", "日" ]
							const youbi = dayArray[dayOfWeek]
							//出力用
							const dayStr = year + "年" + mon + "月" + day + "日"
									+ "(" + youbi + ")";
							return dayStr
						},
						classObject : function() {
							return {
								'startBtn btn ' : !this.startDisabled,
								'startiDisabled' : this.startDisabled
							}
						}
					},
					mounted : function() {
						window.addEventListener('resize', this.handleResize)
					},
					beforeDestroy : function() {
						window.removeEventListener('resize', this.handleResize)
					},
					created : function() {
						this.exist = document.getElementById("exist").value
						const start_btn_flg = document.getElementById("start_btn_flg").value

						if (start_btn_flg === '1') {
							this.finishDisabled = false
							this.startDisabled = true
						}

						const dt = new Date()
						dt.setHours(8)
						dt.setMinutes(0)
						dt.setSeconds(0)
						const Now = new Date()

						this.isSmartPhone = this.judgeSmartPhone()
						if (this.isSmartPhone) {
							this.getPosition()
						}
						this.handleResize()
					}
				})
	</script>
</body>
</html>