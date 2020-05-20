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

		<div class="manage-box">

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
									<form action="${pageContext.request.contextPath}/Reports" method="GET">
									<input type="hidden" :value="target_year" name="target_year">
									<input type="hidden" :value="target_month" name="target_month">
									<input type="hidden" value="${dateList[status.index][num.index] }" name="target_date">
									<c:choose>
										<c:when test="${dateList[status.index][num.index] == target_date}">
											<input type="submit" class="btn calendar-date btn-primary " value="${dateList[status.index][num.index] }" >
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

		<div>
			<p>作業報告</p>
			<textarea form="form" name="report"></textarea>
		</div>
		<div>
			<p>備考</p>
			<textarea form="form" name="text"></textarea>
		</div>

		<form id="form" action="${pageContext.request.contextPath}/CreateReport" method="POST">
			<input type="hidden" :value="target_year" name="target_year">
			<input type="hidden" :value="target_month" name="target_month">
			<input type="hidden" :value="target_day" name="target_day">
			<input type="submit" value="更新" class="btn btn-primary">
		</form>
	</div>
	<script>
		new Vue(
				{
					el : "#app",
					data : {
						target_year : null,
						target_month : null,
						target_date : null
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
								this.target_year = new Date().getFullYear()
							} else {
								this.target_year= this.getPram('target_year')
							}

							if (this.getPram('target_month') === ''
									|| this.getPram('target_month') === null) {
								this.target_month = new Date().getMonth() + 1
							} else {
								this.target_month = this
										.getPram('target_month')
							}

							if (this.getPram('target_date') === ''
									|| this.getPram('target_date') === null) {
								this.target_date = new Date().getDate()
							} else {
								this.target_date = this.getPram('target_date')
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