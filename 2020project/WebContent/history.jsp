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
		<div>
		<form class="home-btn" method="get"
			action="${pageContext.request.contextPath}/Home">
			<input class="btn" type="submit" value="戻る">
		</form>
		<span class="user-name">${ user_name} さん</span>
		<form class="logout-btn" method="post"
			action="${pageContext.request.contextPath}/Logout">
			<input class="btn btn-secondary" type="submit" value="ログアウト">
		</form>
		</div>
		<div class="inline-block select-box">
			<div>年を選択</div>
			<div>
				<div class="left-btn" v-on:click="beforeYear"></div>
				<select name="example" v-model="selectedYear"
					v-on:change="changePage" class="inline-block form-control">
					<option>2020</option>
					<option>2021</option>
				</select>
				<div class="right-btn" v-on:click="nextYear"></div>
			</div>
		</div>
		<div class="inline-block select-box">
			<div>月を選択</div>
			<div>
				<div class="left-btn" v-on:click="beforeMonth"></div>
				<select name="example" v-model="selectedMonth"
					v-on:change="changePage" class="inline-block form-control">
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
		<h4>
			<font color="red">${confirm_message} </font>
			<%
				session.removeAttribute("confirm_message");
			%>
		</h4>
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
							<td>
								<form method="POST"
									action="${pageContext.request.contextPath}/ChangeHoliday">
									<input type="hidden" :value="selectedYear" name="target_year">
									<input type="hidden" :value="selectedMonth" name="target_month">
									<input type="hidden" value="${parentStatus.count }"
										name="target_day"> <input type="hidden"
										value="${map.division }" name="division"> <input
										type="hidden" value="${map.start_time }" name="start_time">
									<input type="hidden" value="${map.finish_time }"
										name="finish_time"> <input type="hidden"
										value="${map.break_time }" name="break_time"> <input
										type="hidden" value="${map.standard_time }"
										name="standard_time"> <input type="hidden"
										value="${map.much_or_little }" name="much_or_little">
									<input type="hidden" value="${map.over_time }" name="over_time">
									<input type="hidden" value="${map.late_over_time }"
										name="late_over_time"> <input type="hidden"
										value="${map.work_time }" name="work_time"> <input
										type="hidden" value="${map.note }" name="note"> <input
										type="hidden" value="${map.reason }" name="reason"> <input
										type="hidden" value="${map.start_latitude }"
										name="start_latitude"> <input type="hidden"
										value="${map.start_longitude }" name="start_longitude">
									<input type="hidden" value="${map.finish_latitude }"
										name="finish_latitude"> <input type="hidden"
										value="${map.finish_longitude }" name="finish_longitude">
									<input type="hidden" value="${map.date }" name="date">
									<input type="hidden" value="${map.notExist }" name="notExist">

									<c:if test="${map.holiday == '0'}">
										<input type="hidden" value="平日" name="status">
										<input type="submit" value="">
									</c:if>
									<c:if test="${map.holiday == '1'}">
										<input type="hidden" value="休日" name="status">
										<input type="submit" class="background-red" value="">
									</c:if>
									<c:if test="${map.holiday == '2'}">
										<input type="hidden" value="有給" name="status">
										<input type="submit" class="background-red" value="有">
									</c:if>
								</form>
							</td>
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
							<td>
								<form class="home-btn" method="get"
									action="${pageContext.request.contextPath}/UpdateHistory">
									<input class="btn" type="submit" value="btn"> <input
										type="hidden" :value="selectedYear" name="target_year">
									<input type="hidden" :value="selectedMonth" name="target_month">
									<input type="hidden" value="${parentStatus.count }"
										name="target_day"> <input type="hidden"
										value="${map.day }" name="day">
								</form>
							</td>
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
							<td>
								<form method="POST"
									action="${pageContext.request.contextPath}/ChangeHoliday">
									<input type="hidden" :value="selectedYear" name="target_year">
									<input type="hidden" :value="selectedMonth" name="target_month">
									<input type="hidden" value="${parentStatus.count }"
										name="target_day"> <input type="hidden"
										value="${map.division }" name="division"> <input
										type="hidden" value="${map.start_time }" name="start_time">
									<input type="hidden" value="${map.finish_time }"
										name="finish_time"> <input type="hidden"
										value="${map.break_time }" name="break_time"> <input
										type="hidden" value="${map.standard_time }"
										name="standard_time"> <input type="hidden"
										value="${map.much_or_little }" name="much_or_little">
									<input type="hidden" value="${map.over_time }" name="over_time">
									<input type="hidden" value="${map.late_over_time }"
										name="late_over_time"> <input type="hidden"
										value="${map.work_time }" name="work_time"> <input
										type="hidden" value="${map.note }" name="note"> <input
										type="hidden" value="${map.reason }" name="reason"> <input
										type="hidden" value="${map.start_latitude }"
										name="start_latitude"> <input type="hidden"
										value="${map.start_longitude }" name="start_longitude">
									<input type="hidden" value="${map.finish_latitude }"
										name="finish_latitude"> <input type="hidden"
										value="${map.finish_longitude }" name="finish_longitude">
									<input type="hidden" value="${map.date }" name="date">
									<input type="hidden" value="${map.notExist }" name="notExist">

									<c:if test="${map.holiday == '0'}">
										<input type="hidden" value="平日" name="status">
										<input type="submit" value="">
									</c:if>
									<c:if test="${map.holiday == '1'}">
										<input type="hidden" value="休日" name="status">
										<input type="submit" class="background-red" value="">
									</c:if>
									<c:if test="${map.holiday == '2'}">
										<input type="hidden" value="有給" name="status">
										<input type="submit" class="background-red" value="有">
									</c:if>
								</form>
							</td>
							<td>${parentStatus.count }</td>
							<td>${map.day}</td>
							<td>${map.division}</td>
							<td>${ map.start_time}</td>
							<td>${map.finish_time}</td>
							<td>${map.break_time}</td>
							<td>${map.work_time }</td>
							<td>${map.much_or_little }</td>
							<td>${map.note }</td>
							<td>
								<form class="home-btn" method="get"
									action="${pageContext.request.contextPath}/UpdateHistory">
									<input class="btn" type="submit" value="btn"> <input
										type="hidden" :value="selectedYear" name="target_year">
									<input type="hidden" :value="selectedMonth" name="target_month">
									<input type="hidden" value="${parentStatus.count }"
										name="target_day">
								</form>
							</td>
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
		<table>
			<tbody>
				<tr>
					<th>有給残日数</th>
				</tr>
				<tr>
					<td>${ paid_vacations}日</td>
				</tr>
			</tbody>
		</table>
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
							this.changePage()
						},
						beforeMonth : function() {
							this.selectedMonth--
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