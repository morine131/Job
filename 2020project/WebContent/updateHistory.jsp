<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="header.jsp" />
<title>勤務表修正画面</title>
</head>
<body>
	<div class="container" id="app">
		{{feeling}}
		${ob.start_latitude }
		<h2>勤務表修正</h2>
		<div>
			<span class="user-name">社員名： ${ user_name} さん</span>
			<form class="logout-btn" method="post"
				action="${pageContext.request.contextPath}/Logout">
				<input class="btn btn-secondary" type="submit" value="ログアウト">
			</form>
		</div>
		<c:if test="${user_type == '1'}">
			<table class="update_history table-bordered table-hover">
				<tbody>
					<tr>
						<th>休日</th>
						<th>日付</th>
						<th>曜日</th>
						<th>勤務区分</th>
						<th>出社時刻</th>
						<th>退社時刻</th>
						<th>退勤時の気分</th>
						<th>休憩時間</th>
						<th>基本時間</th>
						<th>超過・不足</th>
						<th>通常残業時間</th>
						<th>深夜残業時間</th>
						<th>作業時間</th>
						<th>備考</th>
					</tr>
					<tr>
						<td
								<c:if test="${ob.holiday == '1'}">
								class="background-red"
							</c:if>></td>
						<td>{{target_month}}/{{ target_day }}</td>
						<td>${ob.day}</td>
						<td><input name="division" type="text" value="${ob.division }" v-bind:disabled="isAuto" form="form"></td>
						<td><input name="start_time" type="text" value="${ ob.start_time }" form="form"></td>
						<td><input name="finish_time" type="text" value="${ ob.finish_time }" form="form"></td>
						<td>
							<select name="feeling" v-model="feeling" form="form">
								<option value="0">良好</option>
								<option value="1">普通</option>
								<option value="2">イマイチ</option>
							</select>
						</td>
						<td><input name="break_time" type="text" value="${ ob.break_time }" v-bind:disabled="isAuto" form="form"></td>
						<td><input name="standard_time" type="text" value="${ ob.standard_time }" v-bind:disabled="isAuto" form="form"></td>
						<td><input name="much_or_little" type="text" value="${ ob.much_or_little }" v-bind:disabled="isAuto" form="form"></td>
						<td><input name="over_time" type="text" value="${ ob.over_time }" v-bind:disabled="isAuto" form="form"></td>
						<td><input name="late_over_time" type="text" value="${ ob.late_over_time }" v-bind:disabled="isAuto" form="form"></td>
						<td><input name="work_time" type="text" value="${ ob.work_time }" v-bind:disabled="isAuto" form="form"></td>
						<td><input name="note" type="text" value="${ ob.note }"  form="form"></td>
				</tbody>
			</table>
			<br>
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
		<input type="radio" name="auto_or_manual" value="auto" v-model="picked" >出勤打刻と退勤打刻のみ入力し、自動計算する<br>
		<input type="radio" name="auto_or_manual" value="manual" v-model="picked">全ての項目を手入力する
		<br>
		<div>
			修正理由： <input type="text" name="reason" form="form">
		</div>
		<form class="start-btn" method="POST" id="form"
			action="${pageContext.request.contextPath}/UpdateHistory">
			<input type="hidden" value="${flag}" name="flag">
			<input type="hidden" :value="target_year" name="target_year">
			<input type="hidden" :value="target_month" name="target_month">
			<input type="hidden" :value="target_day" name="target_day">
			<input type="hidden" value="${ob.start_latitude }" name="start_latitude">
			<input type="hidden" value="${ob.finish_latitude }" name="finish_latitude">
			<input type="hidden" value="${ob.start_longitude }" name="start_longitude">
			<input type="hidden" value="${ob.finish_longitude }" name="finish_longitude">
			<input type="hidden" :value="picked" name="isAuto">
			<input class="btn" type="submit" value="修正する">
		</form>
		<form class="start-btn" method="get"
			action="${pageContext.request.contextPath}/Home">
			<input class="btn" type="submit" value="戻る">
		</form>
		<input type="hidden" id="getFeel" value="${ ob.feeling }">

	</div>
	<script>
	new Vue({
				el : "#app",
				data : {
					feeling : null,
					picked: 'auto',
					isAuto: true,
					target_year : null,
					target_month : null,
					target_day : null
				},
				methods : {
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
					setSelected : function() {
							this.target_year = this.getPram('target_year')
							this.target_month = this.getPram('target_month')
							this.target_day = this.getPram('target_day')

					}
				},
				created: function(){
					this.setSelected()
					this.feeling = document.getElementById('getFeel').value

				},
				watch: {
	                picked: function () {
	                    this.isAuto = ! this.isAuto
	                }
	            }

			})
	</script>
</body>
</html>