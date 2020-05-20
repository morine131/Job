<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="header.jsp" />
<title>Insert title here</title>
</head>
<body>
	<div class="container" id="app">
		<div class="manage-top">
		<form class="admin-home-btn" method="get" action="${pageContext.request.contextPath}/Home">
			<input class="btn btn-sm btn-outline-secondary back-btn" type="submit" value="戻る">
		</form>
		<h2 class="inline-block">残業一覧</h2>
		</div>
		<div class="width200">
			<div class="padding-top">年を選択</div>
			<div class="left-btn" v-on:click="beforeYear"></div>
			<select name="example" v-model="selected"
				v-on:change="changePage" class="inline-block form-control history-select">
				<c:forEach items="${ yearList }" var="year">
					<option>${year}</option>
				</c:forEach>
			</select>
			<div class="right-btn" v-on:click="nextYear"></div>
		</div>
		<div>
			<table class="feel-table table-bordered table-hover">
				<tbody>
					<tr>
						<td class="over-table-name"></td>
						<td>1月</td>
						<td>2月</td>
						<td>3月</td>
						<td>4月</td>
						<td>5月</td>
						<td>6月</td>
						<td>7月</td>
						<td>8月</td>
						<td>9月</td>
						<td>10月</td>
						<td>11月</td>
						<td>12月</td>
					</tr>
					<c:forEach items="${map}" var="obj" varStatus="parentStatus">
					<tr>
						<td>${obj.key }</td>
						<c:forEach begin="0" end="23" step="2" varStatus="status">
							<td
							<c:if test="${ obj.value[status.index+1] == '1'}" >class="background-yellow"</c:if>
							<c:if test="${ obj.value[status.index+1] == '2'}" >class="background-orange"</c:if>
							<c:if test="${ obj.value[status.index+1] == '3'}" >class="background-red"</c:if>

							>
							${ obj.value[status.index] }
							</td>
						</c:forEach>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<script>
		new Vue(
				{
					el : "#app",
					data : {
						url : location.href,
						selected : null
					},
					methods : {
						nextYear: function(){
							var selectedYear = Number(this.selected)
							this.selected ++
							this.changePage()
						},
						beforeYear: function(){
							var selectedYear = Number(this.selected)
							this.selected --
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
							console.log("changePage")
							window.location.href = '/web-time-card/OverTimeWorkList?target_year='
									+ this.selected
						},
						setSelected : function() {
							console.log(this.getPram('target_year'))
							if (this.getPram('target_year') === '' || this.getPram('target_year') === null) {
								this.selected = new Date().getFullYear() //プルダウンの初期値として現在年を指定
							} else {
								this.selected = this.getPram('target_year')
							}
						}
					},
					created: function(){
						this.setSelected()
					}

				})
	</script>
</body>
</html>