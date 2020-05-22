
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="header.jsp" />
<title>勤務表管理画面</title>
</head>
<body>
${start_latitude_list}
	<div class="container" id="app">
		<div class="manage-top">
		<form class="admin-home-btn" method="get" action="${pageContext.request.contextPath}/Home">
			<input class="btn btn-sm btn-outline-secondary back-btn" type="submit" value="戻る">
		</form>
		<h2 class="inline-block">勤務表管理</h2>
		</div>
		<div class="manage-box">
		<div>
			<select name="example" v-model="selectedUser"
				v-on:change="changePage" class="form-control width300 ">
				<option value=''>ユーザーを選択してください</option>
				<c:forEach items="${nameList}" var="map" varStatus="parentStatus">
					<option>${map}</option>
				</c:forEach>
			</select>
		</div>
		<div class="width200">
			<div>年を選択</div>
			<div class="left-btn" v-on:click="beforeYear"></div>
			<select name="example" v-model="selectedYear"
				v-on:change="changePage" class="inline-block form-control history-select">
				<c:forEach items="${ yearList }" var="year">
					<option>${year}</option>
				</c:forEach>
			</select>
			<div class="right-btn" v-on:click="nextYear"></div>
		</div>
		<div class="width200">
			<div>月を選択</div>
			<div class="left-btn" v-on:click="beforeMonth"></div>
			<select name="example" v-model="selectedMonth"
				v-on:change="changePage" class="inline-block form-control history-select">
				<option>1</option>
				<option>2</option>
				<option>3</option>
				<option>4</option>
				<option>5</option>
				<option>6</option>
				<option>7</option>
				<option>8</option>
				<option>9</option>
				<option>10</option>
				<option>11</option>
				<option>12</option>
			</select>
			<div class="right-btn" v-on:click="nextMonth"></div>
		</div>
		</div>
		<div v-if="visible" class="width1100">
			<c:if test="${target_user_type == '1'}">
				<table class="manage-table table-bordered table-hover">
					<tbody>
						<tr>
							<th class="table-holiday">休日</th>
							<th class="table-date">日付</th>
							<th class="table-day">曜日</th>
							<th class="table-division">勤務区分</th>
							<th class="table-start">出社時刻</th>
							<th class="table-finish">退社時刻</th>
							<th class="table-break">休憩時間</th>
							<th class="table-standard">基本時間</th>
							<th class="table-much">超過・不足</th>
							<th class="table-over">通常残業時間</th>
							<th class="table-late">深夜残業時間</th>
							<th class="table-work">作業時間</th>
							<th class="table-note">備考</th>
							<th class="table-reason">修正理由</th>
						</tr>
						<c:forEach items="${list}" var="map" varStatus="parentStatus">
							<tr>
								<td
									<c:if test="${map.holiday == '1'}">
								class="background-red"
							</c:if>></td>
								<td>${parentStatus.count }</td>
								<td>${map.day}</td>
								<td>${map.division}</td>
								<td>${ map.start_time_hhmm}</td>
								<td>${map.finish_time_hhmm}</td>
								<td>${map.break_time}</td>
								<td>${map.standard_time }</td>
								<td>${map.much_or_little }</td>
								<td>${map.over_time }</td>
								<td>${map.late_over_time }</td>
								<td>${map.work_time }</td>
								<td>${map.note }</td>
								<td>${map.reason }</td>
						</c:forEach>

					</tbody>
				</table>
				<br>

		<b>平日合計</b>
		<table class="sum-table table-bordered table-hover">
					<tbody>
						<tr>
							<th>作業時間</th>
							<th>基本時間</th>
							<th>超過・不足</th>
							<th>通常残業時間</th>
							<th>深夜残業時間</th>
						</tr>
						<tr>
							<td>${week_work_time }</td>
							<td>${week_standard_time }</td>
							<td>${week_much_or_little }</td>
							<td>${week_over_time }</td>
							<td>${week_late_over_time }</td>
						</tr>
					</tbody>
				</table>
				<br>
		<b>休日合計</b>
		<table class="sum-table table-bordered table-hover">
					<tbody>
						<tr>
							<th>作業時間</th>
							<th>通常残業時間</th>
							<th>深夜残業時間</th>
						</tr>
						<tr>
							<td>${holi_work_time }</td>
							<td>${holi_over_time }</td>
							<td>${holi_late_over_time }</td>
						</tr>
					</tbody>
				</table>
			</c:if>
			<c:if test="${target_user_type == '2'}">
				<table class="manage-table table-bordered table-hover">
					<tbody>
						<tr>
							<th class="table-holiday">休日</th>
							<th class="table-date">日付</th>
							<th class="table-day">曜日</th>
							<th class="table-division">勤務区分</th>
							<th class="table-start">出社時刻</th>
							<th class="table-finish">退社時刻</th>
							<th class="table-break-f">休憩時間</th>
							<th class="table-work-f">作業時間</th>
							<th class="table-much">超過・不足</th>
							<th class="table-note-f">備考</th>
							<th class="table-reason-f">修正理由</th>
						</tr>
						<c:forEach items="${list}" var="map" varStatus="parentStatus">
							<tr>
								<td
									<c:if test="${map.holiday == '1'}">
								class="background-red"
							</c:if>></td>
								<td>${parentStatus.count }</td>
								<td>${map.day}</td>
								<td>${map.division}</td>
								<td>${map.start_time_hhmm}</td>
								<td>${map.finish_time_hhmm}</td>
								<td>${map.break_time}</td>
								<td>${map.work_time }</td>
								<td>${map.much_or_little }</td>
								<td>${map.note }</td>
								<td>${map.reason }</td>
						</c:forEach>

					</tbody>
				</table>
				<br>

		<b>平日合計</b>
		<table class="sum-table table-bordered table-hover">
					<tbody>
						<tr>
							<th>作業時間</th>
							<th>超過・不足</th>
						</tr>
						<tr>
							<td>${week_work_time }</td>
							<td>${week_much_or_little }</td>
						</tr>
					</tbody>
				</table>
				<br>
		<b>休日合計</b>
		<table class="sum-table table-bordered table-hover">
					<tbody>
						<tr>
							<th>作業時間</th>
							<th>通常作業時間</th>
							<th>超過作業時間</th>
						</tr>
						<tr>
							<td>${holi_work_time }</td>
							<td>${holi_over_time }</td>
							<td>${holi_late_over_time }</td>
						</tr>
					</tbody>
				</table>
			</c:if>


		</div>
		<div id="sample"></div>
	</div>
	<c:forEach items="${start_latitude_list}" var="map"
		varStatus="parentStatus">
		<input type="hidden" value="${ map }" class="start_latitude">
	</c:forEach>
	<c:forEach items="${start_longitude_list}" var="map"
		varStatus="parentStatus">
		<input type="hidden" value="${ map }" class="start_longitude">
	</c:forEach>
	<c:forEach items="${finish_latitude_list}" var="map"
		varStatus="parentStatus">
		<input type="hidden" value="${ map }" class="finish_latitude">
	</c:forEach>
	<c:forEach items="${finish_longitude_list}" var="map"
		varStatus="parentStatus">
		<input type="hidden" value="${ map }" class="finish_longitude">
	</c:forEach>
	<script src="https://maps.googleapis.com/maps/api/js?callback=initMap"
		async defer>
	</script>
	<script>
	let map;
	let marker = [];
	let infoWindow = [];
	const targetYear = getParam("target_year")
	const targetMonth = getParam("target_month")
	const count_days = days(targetYear, targetMonth)
	const start_latitude_list = document
			.getElementsByClassName('start_latitude');
	const start_longitude_list = document
			.getElementsByClassName('start_longitude');
	const finish_latitude_list = document
			.getElementsByClassName('finish_latitude');
	const finish_longitude_list = document
			.getElementsByClassName('finish_longitude');
	var start_la_list = []
	var start_lo_list = []
	var finish_la_list = []
	var finish_lo_list = []
	var start_day_list = []
	var finish_day_list = []
	var markerData = [];
	let count = 0
	console.log(finish_latitude_list)
	for (var i = 0; i < count_days; i++) {
		const num = start_latitude_list[i].value
		const num2 = start_longitude_list[i].value
		if (num > 0) {
			console.log(i + " " +num + " " + num2 )
			const obj = {
				name : targetMonth + "/" + (i + 1) + " 出勤",
				lat : parseFloat(num),
				lng : parseFloat(num2),
				icon : './images/red_point.png'
			}
			markerData.push(obj)
		}
		const num3 = finish_latitude_list[i].value
		const num4 = finish_longitude_list[i].value
		if (num3 > 0) {
			console.log(i + " " +num3 + " " + num4 )
			const obj = {
				name : targetMonth + "/" + (i + 1) + " 退勤",
				lat : parseFloat(num3),
				lng : parseFloat(num4),
				icon : './images/blue_point.png'
			}
			markerData.push(obj)
		}
	}
	function days(year, month) {
		return new Date(parseInt(year, 10), parseInt(month, 10), 0)
				.getDate();
	}
	//パラメーターで指定された値を取得するメソッド
	function getParam(name, url) {
		if (!url)
			url = window.location.href;
		name = name.replace(/[\[\]]/g, "\\$&");
		var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"), results = regex
				.exec(url)
		if (!results)
			return null
		if (!results[2])
			return ''
		return decodeURIComponent(results[2].replace(/\+/g, " "))
	}
	function initMap() {
		// 地図の作成
		var mapLatLng = new google.maps.LatLng({
			lat : markerData[0]['lat'],
			lng : markerData[0]['lng']
		}); // 緯度経度のデータ作成
		map = new google.maps.Map(document.getElementById('sample'), { // #sampleに地図を埋め込む
			center : mapLatLng, // 地図の中心を指定
			zoom : 15
		// 地図のズームを指定
		});
		// マーカー毎の処理
		for (var i = 0; i < markerData.length; i++) {
			markerLatLng = new google.maps.LatLng({
				lat : markerData[i]['lat'],
				lng : markerData[i]['lng']
			}); // 緯度経度のデータ作成
			marker[i] = new google.maps.Marker({ // マーカーの追加
				position : markerLatLng, // マーカーを立てる位置を指定
				map : map,
				icon : {
					url : markerData[i]['icon']
				// マーカーの画像を変更
				}
			// マーカーを立てる地図を指定
			});
			infoWindow[i] = new google.maps.InfoWindow({ // 吹き出しの追加
				content : '<div class="sample">' + markerData[i]['name']
						+ '</div>' // 吹き出しに表示する内容
			});
			markerEvent(i); // マーカーにクリックイベントを追加
		}
	}
	// マーカーにクリックイベントを追加
	function markerEvent(i) {
		marker[i].addListener('click', function() { // マーカーをクリックしたとき
			infoWindow[i].open(map, marker[i]); // 吹き出しの表示
		});
	}
		new Vue(
				{
					el : "#app",
					data : {
						selectedUser : null,
						selectedYear : null,
						selectedMonth : null,
						visible : false,
					},
					methods : {
						nextYear : function() {
							this.selectedYear++
							this.changePage()
						},
						beforeYear : function() {
							this.selectedYear--
							this.changePage()
						},
						nextMonth : function() {
							this.selectedMonth++
							if(this.selectedMonth>=13){
								this.selectedMonth = 1
							}
							this.changePage()
						},
						beforeMonth : function() {
							this.selectedMonth--
							if(this.selectedMonth<=0){
								this.selectedMonth = 12
							}
							this.changePage()
						},
						getPram : function(name, url) {
							if (!url)
								url = window.location.href;
							name = name.replace(/[\[\]]/g, "\\$&");
							var regex = new RegExp("[?&]" + name
									+ "(=([^&#]*)|&|#|$)"), results = regex
									.exec(url)
							if (!results)
								return null
							if (!results[2])
								return ''
							return decodeURIComponent(results[2].replace(/\+/g,
									" "))
						},
						changePage : function() {
							if (this.selectedUser === '') {
								return
							}
							window.location.href = '/web-time-card/Manage?target_year='
									+ this.selectedYear
									+ '&target_month='
									+ this.selectedMonth
									+ '&target_user='
									+ this.selectedUser

						},
						setSelected : function() {
							if (this.getPram('target_year') === ''
									|| this.getPram('target_year') === null) {
								this.selectedYear = new Date().getFullYear() //プルダウンの初期値として現在年を指定
							} else {
								this.selectedYear = this.getPram('target_year')
							}
							if (this.getPram('target_month') === ''
									|| this.getPram('target_month') === null) {
								this.selectedMonth = new Date().getMonth() + 1 //プルダウンの初期値として現在年を指定
							} else {
								this.selectedMonth = this
										.getPram('target_month')
							}
							if (this.getPram('target_user') === ''
									|| this.getPram('target_user') === null) {
								this.selectedUser = '' //プルダウンの初期値は''
							} else {
								this.selectedUser = this.getPram('target_user')
								this.visible = true
							}
						}
					},
					created : function() {
						this.setSelected()
						this.url = new URL(location);
					}

				})
	</script>
</body>
</html>