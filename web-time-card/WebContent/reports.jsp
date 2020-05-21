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

		<h4>
			<font color="red">${repoUpdate_message} </font>
			<%
				session.removeAttribute("repoUpdate_message");
			%>
		</h4>
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
	<script>

		new Vue(
				{
					el : "#app",
					data : {
						target_year : null,
						target_month : null,
						target_date : null,
						today_flag :false,
						edit_flag : true
					},
					computed : {
						today : function(){
							const date =new Date()
							return date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate()
						}
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
						},
						nextYear: function(){
							this.target_year ++
							this.changePage()
						},
						beforeYear: function(){
							this.target_year--
							this.changePage()
						},
						nextMonth: function(){
							this.target_month ++
							if(this.target_month>=13){
								this.target_month = 1
							}
							this.changePage()
						},
						beforeMonth: function(){
							this.target_month --
							if(this.target_month  <= 0){
								this.target_month = 12
							}
							this.changePage()
						},
						changePage : function() {
							const url = '/web-time-card/Reports?target_year=' + this.target_year + '&target_month=' + this.target_month + '&target_date=' + this.target_date
							window.location.href = url;

						},
					},
					created : function() {
						this.setTarget()
						const dateStr = this.target_year + "-"+this.target_month + "-" + this.target_date
						console.log(dateStr)
						console.log(this.today)
						if(this.today == dateStr){
							this.today_flag = true
							this.edit_flag = false
						}
					}

				})
		function Check(){
	    	const regexp =  new RegExp('[<>&?:*;]')
	    	const reportStr = document.getElementById("report").value
	    	const textStr = document.getElementById("text").value
			let check = true
	    	if (regexp.test(reportStr) || regexp.test(textStr)){
	    		check = false
	        	alert("入力内容に特殊記号が含まれています")
	    	}
			console.log (reportStr)
			console.log(textStr)
	    	console.log(check)
	    	return check
	    }

	</script>
</body>
</html>