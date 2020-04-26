<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="header.jsp" />
<title>勤務表画面</title>
</head>
<body>
	<div class="container" id="app">
		<h2>勤務表</h2>
		<form class="home-btn" method="get"
			action="${pageContext.request.contextPath}/Home">
			<input class="btn" type="submit" value="戻る">
		</form>
		<span class="user-name">${ user_name} さん</span>
		<form class="logout-btn" method="post"
			action="${pageContext.request.contextPath}/Logout">
		<input class="btn btn-secondary" type="submit" value="ログアウト">
		</form>
		<div>
		<input class="yajirushi-btn" type="submit" value="＜" v-on:click="beforeYear">
		<select name="example" v-model="selectedYear" v-on:change="changePage" class="inline-block">
			<option>2020</option>
			<option>2021</option>
		</select>
			<input class="yajirushi-btn" type="submit" value="＞" v-on:click="nextYear">
		</div>
				<div>
						<input class="yajirushi-btn" type="submit" value="＜" v-on:click="beforeMonth">
		<select name="example" v-model="selectedMonth" v-on:change="changePage" class="inline-block">
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
			<input class="yajirushi-btn" type="submit" value="＞" v-on:click="nextMonth">
		</div>
		<c:if test="${user_type == '1'}">
			<table class="feel-table table-bordered table-hover">
				<tbody>
					<tr>
						<th>休日</th>
						<th>日付</th>
						<th>曜日</th>
						<th>勤務区分</th>
						<th>出社時刻</th>
						<th>退社時刻</th>
						<th>休憩時間</th>
						<th>基本時間</th>
						<th>超過・不足</th>
						<th>通常残業時間</th>
						<th>深夜残業時間</th>
						<th>作業時間</th>

						<th>備考</th>
						<th>修正</th>
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
							<td>${ map.start_time}</td>
							<td>${map.finish_time}</td>
							<td>${map.break_time}</td>
							<td>${map.standard_time }</td>
							<td>${map.much_or_little }</td>
							<td>${map.over_time }</td>
							<td>${map.late_over_time }</td>
							<td>${map.work_time }</td>
							<td>${map.note }</td>
							<td><input type="button" value="btn">
					</c:forEach>

				</tbody>
			</table>
			<br>

		平日合計
		<table class="feel-table table-bordered table-hover">
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
		休日合計
		<table class="feel-table table-bordered table-hover">
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
		<c:if test="${user_type == '2'}">
			<table class="feel-table table-bordered table-hover">
				<tbody>
					<tr>
						<th>休日</th>
						<th>日付</th>
						<th>曜日</th>
						<th>勤務区分</th>
						<th>出社時刻</th>
						<th>退社時刻</th>
						<th>休憩時間</th>
						<th>作業時間</th>
						<th>超過・不足</th>
						<th>備考</th>
						<th>修正</th>
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
							<td>${ map.start_time}</td>
							<td>${map.finish_time}</td>
							<td>${map.break_time}</td>
							<td>${map.work_time }</td>
							<td>${map.much_or_little }</td>
							<td>${map.note }</td>
							<td><input type="button" value="btn">
					</c:forEach>

				</tbody>
			</table>
			<br>

		平日合計
		<table class="feel-table table-bordered table-hover">
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
		休日合計
		<table class="feel-table table-bordered table-hover">
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
	<script>
		new Vue(
				{
					el : "#app",
					data : {
						url : null,
						selectedYear : null,
						selectedMonth : null
					},
					methods : {
						nextYear: function(){
							this.selectedYear ++
							this.changePage()
						},
						beforeYear: function(){
							this.selectedYear --
							this.changePage()
						},
						nextMonth: function(){
							this.selectedMonth ++
							this.changePage()
						},
						beforeMonth: function(){
							this.selectedMonth --
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
							window.location.href = '/2020project/History?target_year='
									+ this.selectedYear
									+ '&target_month='
									+ this.selectedMonth
						},
						setSelected : function() {
							if (this.getPram('target_year') === '' || this.getPram('target_year') === null) {
								this.selectedYear = new Date().getFullYear() //プルダウンの初期値として現在年を指定
							} else {
								this.selectedYear = this.getPram('target_year')
							}
							if (this.getPram('target_month') === '' || this.getPram('target_month') === null) {
								this.selectedMonth = new Date().getMonth()+1 //プルダウンの初期値として現在年を指定
							} else {
								this.selectedMonth = this.getPram('target_month')
							}
						}
					},
					created: function(){
						this.setSelected()
						this.url = new URL(location);
					}

				})
	</script>
</body>
</html>