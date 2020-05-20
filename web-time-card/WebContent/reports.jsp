<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="header.jsp" />
<title>業務報告</title>
</head>
<body>
	<jsp:include page="nav.jsp" />
	<div class="container" id="app">
		<h2>業務報告</h2>
		<form action="${pageContext.request.contextPath}/CreateReport"
			method="GET">
			<input type="submit" value="新規作成" class="btn btn-primary">
		</form>
		<div class="manage-box">

			<div class="width200">
				<div>年を選択</div>
				<div class="left-btn" v-on:click="beforeYear"></div>
				<select name="example" v-model="selectedYear"
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
				<select name="example" v-model="selectedMonth"
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
			<div class="width200">
				<div>週を選択</div>
				<div class="left-btn" v-on:click="beforeYear"></div>
				<select name="example" v-model="selectedYear"
					v-on:change="changePage"
					class="inline-block form-control history-select">
					<option>第1週</option>
					<option>第2週</option>
					<option>第3週</option>
					<option>第4週</option>
					<option>第5週</option>
				</select>
				<div class="right-btn" v-on:click="nextYear"></div>
			</div>
		</div>

		${line } <br>
		<br> ${dateList }
		<table class="table-bordered calendar">
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
									${dateList[status.index][num.index] }
								</c:if>
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<script>
		new Vue(
				{
					el : "#app",
					data : {
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

						setTarget : function() {
							if (this.getPram('target_year') === ''
									|| this.getPram('target_year') === null) {
								this.target_year = new Date().getFullYear() //プルダウンの初期値として現在年を指定
							} else {
								this.selected = this.getPram('target_year')
							}

							if (this.getPram('target_month') === ''
									|| this.getPram('target_month') === null) {
								this.target_month = new Date().getMonth() + 1 //プルダウンの初期値として現在年を指定
							} else {
								this.target_month = this
										.getPram('target_month')
							}

							if (this.getPram('target_day') === ''
									|| this.getPram('target_day') === null) {
								this.target_day = new Date().getDate() //プルダウンの初期値として現在年を指定
							} else {
								this.target_day = this.getPram('target_day')
							}
						}
					},
					created : function() {
						this.setTarget()
					}

				})
	</script>
</body>
</html>