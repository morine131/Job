<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>業務報告管理</title>
</head>
<body>
<div class="container" id="app">
		<h2>業務報告管理</h2>

		<div class="calendar-box">
		<div class="repo-select-box">
			<div class="width200">
				<div>年を選択</div>
				<div class="left-btn" v-on:click="beforeYear"></div>
				<select name="example" v-model="target_year"
					v-on:change="changePage"
					class="inline-block form-control history-select">
					<c:forEach items="${ yearList }" var="year">
						<option>${year}</option>
					</c:forEach>
				</select>
				<div class="right-btn" v-on:click="nextYear"></div>
			</div>
			<div class="width200">
				<div>月を選択</div>
				<div class="left-btn" v-on:click="beforeMonth"></div>
				<select name="example" v-model="target_month"
					v-on:change="changePage"
					class="inline-block form-control history-select">
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
		<table class="calendar ">
			<tbody>
				<tr>
					<th>月</th>
					<th>火</th>
					<th>水</th>
					<th>木</th>
					<th>金</th>
					<th>土</th>
					<th>日</th>
				</tr>
				<c:forEach begin="0" end="${line }" step="1" varStatus="status">
					<tr>
						<c:forEach var="obj" items="${dateList[status.index]}" varStatus="num">
							<td><c:if test="${dateList[status.index][num.index] >= 1}">
									<form action="${pageContext.request.contextPath}/Reports" method="GET">
									<input type="hidden" :value="target_year" name="target_year">
									<input type="hidden" :value="target_month" name="target_month">
									<input type="hidden" value="${dateList[status.index][num.index] }" name="target_date">
									<c:choose>
										<c:when test="${dateList[status.index][num.index] == target_date}">
											<input type="submit" class="btn calendar-date btn-pink " value="${dateList[status.index][num.index] }" >
										</c:when>
										<c:otherwise>
											<input type="submit" class="btn calendar-date " value="${dateList[status.index][num.index] }" >
										</c:otherwise>
									</c:choose>
									</form>
								</c:if>
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
		<div class="inline-block">
		<div>
			<div class="repo-title">作業報告</div>
			<textarea placeholder="作業報告を入力" :readonly="edit_flag"  class="repo-area" form="form" name="report" id="report" rows="8" cols="40">${rb.report }</textarea>
		</div>
		<div>
			<div class="text-title">備考</div>
			<textarea placeholder="備考を入力"  :readonly="edit_flag" class="repo-area" form="form" name="text" id="text" rows="5" cols="40" >${rb.text }</textarea>
		</div>
		</div>
		<form id="form" action="${pageContext.request.contextPath}/Reports" method="POST" class="repo-up" v-if="today_flag">
			<input type="hidden" :value="target_year" name="target_year">
			<input type="hidden" :value="target_month" name="target_month">
			<input type="hidden" :value="target_date" name="target_date">
			<input type="submit" value="本日の業務報告を登録" class="btn btn-primary" type="submit"  onclick="return Check()">
		</form>
	</div>
</body>
<script>
new Vue(
		{
			el : "#app",
			data : {
				selectedUser : null,
				selectedYear : null,
				selectedMonth : null,
				visivle : false,
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
</html>