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
<jsp:include page="nav.jsp" />
	<div class="container" id="app">
	<input type="hidden" id="getFeel" value="${ ob.feeling }">
		<h2>勤務表修正</h2>
		<div class="target-date">
			対象日:{{target_year}}年{{target_month}}月{{ target_day }}日 ${ob.day}曜日
		</div>
		<div class="auto-btn">
		<input type="radio" name="auto_or_manual" value="auto"
			v-model="picked"> 出勤打刻と退勤打刻を元に自動計算　　　　　<input
			type="radio" name="auto_or_manual" value="manual" v-model="picked"> 全ての項目を入力
		</div>
		<c:if test="${user_type == '1'}">
		<table class="update-table table-bordered table-hover">
				<tbody>
					<tr>
						<th class="update-table-division">勤務区分</th>
						<th class="update-table-start">出社時刻</th>
						<th class="update-table-finish">退社時刻</th>
						<th class="update-table-feel">退勤時の気分</th>
					</tr>
					<tr>
						<td><input name="division" type="text" class="form-control" pattern="[亜-熙ぁ-んァ-ン０-９a-zA-Z0-9\-ー]*" title="特殊記号は使えません"
							value="${ob.division }" v-bind:disabled="isAuto" form="form"></td>
						<td><input name="start_time" type="text" class="form-control" required pattern="\d{2}:\d{2}" title="HH:MMの形式で入力してください"
							value="${ ob.start_time_hhmm }" form="form"></td>
						<td><input name="finish_time" type="text" class="form-control" required pattern="\d{2}:\d{2}" title="HH:MMの形式で入力してください"
							value="${ ob.finish_time_hhmm }" form="form"></td>
						<td><select name="feeling" v-model="feeling" form="form" class="form-control update-table-feel" required>
								<option value="0">良好</option>
								<option value="1">普通</option>
								<option value="2">イマイチ</option>
						</select></td>
				</tbody>
			</table>
			<table class="update-table table-bordered table-hover">
				<tbody>
					<tr>
						<th>休憩時間</th>
						<th>作業時間</th>
						<th >超過・不足</th>
					</tr>
					<tr>
						<td><input name="break_time" type="text"  class=" form-control update-table-break-f" required pattern="\d{2}:[30]0" title="HH:MMの形式で入力してください"
							value="${ ob.break_time }" v-bind:disabled="isAuto" form="form"></td>
						<td><input name="work_time" type="text"  class="form-control update-table-work-f" required pattern="\d{2}:[30]0" title="HH:MMの形式で入力してください"
							value="${ ob.work_time }" v-bind:disabled="isAuto" form="form"></td>
						<td><input name="much_or_little" type="text" class=" form-control update-table-much-f" required  pattern="^[1-9]\d*:[30]0|0:[30]0|-[1-9]\d*:[30]0" title="H:MM or -H:MMの形式で入力してください"
							value="${ ob.much_or_little }" v-bind:disabled="isAuto"
							form="form"></td>
				</tbody>
			</table>
			<table class="update-table table-bordered table-hover">
				<tbody>
					<tr>
						<th>基本時間</th>
						<th>通常残業時間</th>
						<th >深夜残業時間</th>
					</tr>
					<tr>
						<td><input name="standard_time" type="text"  class=" form-control update-table-break-f" required pattern="\d{2}:[30]0" title="HH:MMの形式で入力してください"
							value="${ ob.standard_time }" v-bind:disabled="isAuto" form="form"></td>
						<td><input name="over_time" type="text"  class="form-control update-table-work-f" required pattern="\d{2}:[30]0" title="HH:MMの形式で入力してください"
							value="${ ob.over_time }" v-bind:disabled="isAuto" form="form"></td>
						<td><input name="late_over_time" type="text" class=" form-control update-table-much-f" required pattern="\d{2}:[30]0" title="HH:MMの形式で入力してください"
							value="${ ob.late_over_time }" v-bind:disabled="isAuto"
							form="form"></td>
				</tbody>
			</table>
			<table class="update-table table-bordered table-hover">
				<tbody>
					<tr>
						<th class="update-table-note-f">備考</th>
						<th class="update-table-note-f">修正理由</th>
					</tr>
					<tr>
						<td><input name="note" type="text" value="${ ob.note }" class="update-table-note-f" form="form" pattern="[亜-熙ぁ-んァ-ン０-９a-zA-Z0-9\-ー]*" title="特殊記号は使えません"></td>
						<td><input type="text" name="reason" value="${ ob.reason }"  class="update-table-reason-f" form="form" required pattern="[亜-熙ぁ-んァ-ン０-９a-zA-Z0-9\-ー]*" title="特殊記号は使えません"></td>
				</tbody>
			</table>
		</c:if>
		<c:if test="${user_type == '2'}">
			<table class="update-table table-bordered table-hover">
				<tbody>
					<tr>
						<th class="update-table-division">勤務区分</th>
						<th class="update-table-start">出社時刻</th>
						<th class="update-table-finish">退社時刻</th>
						<th class="update-table-feel">退勤時の気分</th>
					</tr>
					<tr>
						<td><input name="division" type="text" class="form-control" required pattern="[亜-熙ぁ-んァ-ン０-９a-zA-Z0-9\-]*" title="特殊記号は使えません"
							value="${ob.division }" v-bind:disabled="isAuto" form="form"></td>
						<td><input name="start_time" type="text" class="form-control" required
							value="${ ob.start_time_hhmm }" form="form"></td>
						<td><input name="finish_time" type="text" class="form-control" required
							value="${ ob.finish_time_hhmm }" form="form"></td>
						<td><select name="feeling" v-model="feeling" form="form" class="form-control update-table-feel" required>
								<option value="0">良好</option>
								<option value="1">普通</option>
								<option value="2">イマイチ</option>
						</select></td>
				</tbody>
			</table>
			<table class="update-table table-bordered table-hover">
				<tbody>
					<tr>
						<th>休憩時間</th>
						<th>作業時間</th>
						<th >超過・不足</th>
					</tr>
					<tr>
						<td><input name="break_time" type="text"  class=" form-control update-table-break-f" required pattern="\d{2}:[30]0" title="HH:MMの形式で入力してください"
							value="${ ob.break_time }" v-bind:disabled="isAuto" form="form"></td>
						<td><input name="work_time" type="text"  class="form-control update-table-work-f" required pattern="\d{2}:[30]0" title="HH:MMの形式で入力してください"
							value="${ ob.work_time }" v-bind:disabled="isAuto" form="form"></td>
						<td><input name="much_or_little" type="text" class=" form-control update-table-much-f" required pattern="^[1-9]\d*:[30]0|0:[30]0|-[1-9]\d*:[30]0" title="H:MM or -H:MMの形式で入力してください"
							value="${ ob.much_or_little }" v-bind:disabled="isAuto"
							form="form"></td>
				</tbody>
			</table>
			<table class="update-table table-bordered table-hover">
				<tbody>
					<tr>
						<th class="update-table-note-f">備考</th>
						<th class="update-table-note-f">修正理由</th>
					</tr>
					<tr>
						<td><input name="note" type="text" value="${ ob.note }" class="update-table-note-f" form="form" required pattern="[亜-熙ぁ-んァ-ン０-９a-zA-Z0-9\-ー]*" title="特殊記号は使えません"></td>
						<td><input type="text" name="reason" value="${ ob.reason }"  class="update-table-reason-f" form="form" required pattern="[亜-熙ぁ-んァ-ン０-９a-zA-Z0-9\-ー]*" title="特殊記号は使えません"></td>
				</tbody>
			</table>
		</c:if>
		<br>
		<form class="under-btn" method="GET"
			action="${pageContext.request.contextPath}/Delete">

			 <input class="btn btn-danger"
				type="submit" value="削除">
				<input type="hidden" :value="target_year" name="target_year">
				<input type="hidden" :value="target_month" name="target_month">
				<input type="hidden" :value="target_day" name="target_day">
				<input type="hidden" value="${ob.day}" name="day">
		</form>
		<form class="under-btn" method="POST" id="form"
			action="${pageContext.request.contextPath}/UpdateHistory">
			<input type="hidden" value="${flag}" name="flag"> <input
				type="hidden" :value="target_year" name="target_year"> <input
				type="hidden" :value="target_month" name="target_month"> <input
				type="hidden" :value="target_day" name="target_day"> <input
				type="hidden" value="${ob.start_latitude }" name="start_latitude">
				<input name="standard_time" type="hidden"
							value="${ ob.standard_time }" v-bind:disabled="isAuto"
							form="form">
				<input name="over_time" type="hidden"
							value="${ ob.over_time }" v-bind:disabled="isAuto" form="form">
				<input name="late_over_time" type="hidden"
							value="${ ob.late_over_time }" v-bind:disabled="isAuto"
							form="form">
			<input type="hidden" value="${ob.finish_latitude }"
				name="finish_latitude"> <input type="hidden"
				value="${ob.start_longitude }" name="start_longitude"> <input
				type="hidden" value="${ob.finish_longitude }"
				name="finish_longitude"> <input type="hidden"
				:value="picked" name="isAuto"> <input class="btn btn-primary"
				type="submit" value="修正">
		</form>
		<form class="under-btn" method="get"
			action="${pageContext.request.contextPath}/History">
			<input type="hidden" name="target_year" value="${target_year }">
			<input type="hidden" name="target_month" value="${target_month }">
			<input class="btn btn-secondary" type="submit" value="戻る">
		</form>

	</div>
	<script>
		new Vue(
				{
					el : "#app",
					data : {
						feeling : null,
						picked : 'auto',
						isAuto : true,
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
					created : function() {
						this.setSelected()
						this.feeling = document.getElementById('getFeel').value

					},
					watch : {
						picked : function() {
							this.isAuto = !this.isAuto
						}
					}

				})
	</script>
</body>
</html>