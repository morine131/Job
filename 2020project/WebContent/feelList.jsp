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
		<h2>気分一覧</h2>
		<form class="home-btn" method="get"
			action="${pageContext.request.contextPath}/Home">
			<input class="btn" type="submit" value="戻る">
		</form>
			<input class="yajirushi-btn" type="submit" value="＜" v-on:click="beforeYear">
		<select name="example" v-model="selected" v-on:change="changePage" class="inline-block">
			<option>2020</option>
			<option>2021</option>
		</select>
			<input class="yajirushi-btn" type="submit" value="＞" v-on:click="nextYear">

		<c:forEach items="${list}" var="map" varStatus="parentStatus">
			<table class="feel-table table-bordered">
				<tbody>
					<tr>
						<td></td>
						<td class="feel-table-gatunichisquares">月＼日</td>
						<c:forEach begin="1" end="31" step="1" varStatus="status">
							<td><c:out value="${status.count}" /></td>
						</c:forEach>
					</tr>
					<tr>
						<td rowspan="12" class="feel-table-name">${map.user_name}</td>
						<td>1</td>
						<c:forEach begin="0" end="30" step="1" varStatus="status">
							<td
								<c:if test="${list[parentStatus.index].feelYearList[0][status.index] == '0'}">
								class="feel-table-daysquares background-green"
							</c:if>
								<c:if test="${list[parentStatus.index].feelYearList[0][status.index] == '1'}">
								class="feel-table-daysquares background-yellow"
							</c:if>
								<c:if test="${list[parentStatus.index].feelYearList[0][status.index] == '2'}">
								class="feel-table-daysquares background-red"
							</c:if>>
							</td>
						</c:forEach>
					</tr>
					<tr>
						<td>2</td>
						<c:forEach begin="0" end="27" step="1" varStatus="status">
							<td
								<c:if test="${list[parentStatus.index].feelYearList[1][status.index] == '0'}">
								class="feel-table-daysquares background-green"
							</c:if>
								<c:if test="${list[parentStatus.index].feelYearList[1][status.index] == '1'}">
								class="feel-table-daysquares background-yellow"
							</c:if>
								<c:if test="${list[parentStatus.index].feelYearList[1][status.index] == '2'}">
								class="feel-table-daysquares background-red"
							</c:if>>
							</td>
						</c:forEach>
					</tr>
					<tr>
						<td>3</td>
						<c:forEach begin="0" end="30" step="1" varStatus="status">
							<td
								<c:if test="${list[parentStatus.index].feelYearList[2][status.index] == '0'}">
								class="feel-table-daysquares background-green"
							</c:if>
								<c:if test="${list[parentStatus.index].feelYearList[2][status.index] == '1'}">
								class="feel-table-daysquares background-yellow"
							</c:if>
								<c:if test="${list[parentStatus.index].feelYearList[2][status.index] == '2'}">
								class="feel-table-daysquares background-red"
							</c:if>>
							</td>
						</c:forEach>
					</tr>
					<tr>
						<td>4</td>
						<c:forEach begin="0" end="29" step="1" varStatus="status">
							<td
								<c:if test="${list[parentStatus.index].feelYearList[3][status.index] == '0'}">
								class="feel-table-daysquares background-green"
							</c:if>
								<c:if test="${list[parentStatus.index].feelYearList[3][status.index] == '1'}">
								class="feel-table-daysquares background-yellow"
							</c:if>
								<c:if test="${list[parentStatus.index].feelYearList[3][status.index] == '2'}">
								class="feel-table-daysquares background-red"
							</c:if>>
							</td>
						</c:forEach>
					</tr>
					<tr>
						<td>5</td>
						<c:forEach begin="0" end="30" step="1" varStatus="status">
							<td
								<c:if test="${list[parentStatus.index].feelYearList[4][status.index] == '0'}">
								class="feel-table-daysquares background-green"
							</c:if>
								<c:if test="${list[parentStatus.index].feelYearList[4][status.index] == '1'}">
								class="feel-table-daysquares background-yellow"
							</c:if>
								<c:if test="${list[parentStatus.index].feelYearList[4][status.index] == '2'}">
								class="feel-table-daysquares background-red"
							</c:if>>
							</td>
						</c:forEach>
					</tr>
					<tr>
						<td>6</td>
						<c:forEach begin="0" end="29" step="1" varStatus="status">
							<td
								<c:if test="${list[parentStatus.index].feelYearList[5][status.index] == '0'}">
								class="feel-table-daysquares background-green"
							</c:if>
								<c:if test="${list[parentStatus.index].feelYearList[5][status.index] == '1'}">
								class="feel-table-daysquares background-yellow"
							</c:if>
								<c:if test="${list[parentStatus.index].feelYearList[5][status.index] == '2'}">
								class="feel-table-daysquares background-red"
							</c:if>>
							</td>
						</c:forEach>
					</tr>
					<tr>
						<td>7</td>
						<c:forEach begin="0" end="30" step="1" varStatus="status">
							<td
								<c:if test="${list[parentStatus.index].feelYearList[6][status.index] == '0'}">
								class="feel-table-daysquares background-green"
							</c:if>
								<c:if test="${list[parentStatus.index].feelYearList[6][status.index] == '1'}">
								class="feel-table-daysquares background-yellow"
							</c:if>
								<c:if test="${list[parentStatus.index].feelYearList[6][status.index] == '2'}">
								class="feel-table-daysquares background-red"
							</c:if>>
							</td>
						</c:forEach>
					</tr>
					<tr>
						<td>8</td>
						<c:forEach begin="0" end="30" step="1" varStatus="status">
							<td
								<c:if test="${list[parentStatus.index].feelYearList[7][status.index] == '0'}">
								class="feel-table-daysquares background-green"
							</c:if>
								<c:if test="${list[parentStatus.index].feelYearList[7][status.index] == '1'}">
								class="feel-table-daysquares background-yellow"
							</c:if>
								<c:if test="${list[parentStatus.index].feelYearList[7][status.index] == '2'}">
								class="feel-table-daysquares background-red"
							</c:if>>
							</td>
						</c:forEach>
					</tr>
					<tr>
						<td>9</td>
						<c:forEach begin="0" end="29" step="1" varStatus="status">
							<td
								<c:if test="${list[parentStatus.index].feelYearList[8][status.index] == '0'}">
								class="feel-table-daysquares background-green"
							</c:if>
								<c:if test="${list[parentStatus.index].feelYearList[8][status.index] == '1'}">
								class="feel-table-daysquares background-yellow"
							</c:if>
								<c:if test="${list[parentStatus.index].feelYearList[8][status.index] == '2'}">
								class="feel-table-daysquares background-red"
							</c:if>>
							</td>
						</c:forEach>
					</tr>
					<tr>
						<td>10</td>
						<c:forEach begin="0" end="30" step="1" varStatus="status">
							<td
								<c:if test="${list[parentStatus.index].feelYearList[9][status.index] == '0'}">
								class="feel-table-daysquares background-green"
							</c:if>
								<c:if test="${list[parentStatus.index].feelYearList[9][status.index] == '1'}">
								class="feel-table-daysquares background-yellow"
							</c:if>
								<c:if test="${list[parentStatus.index].feelYearList[9][status.index] == '2'}">
								class="feel-table-daysquares background-red"
							</c:if>>
							</td>
						</c:forEach>
					</tr>
					<tr>
						<td>11</td>
						<c:forEach begin="0" end="29" step="1" varStatus="status">
							<td
								<c:if test="${list[parentStatus.index].feelYearList[10][status.index] == '0'}">
								class="feel-table-daysquares background-green"
							</c:if>
								<c:if test="${list[parentStatus.index].feelYearList[10][status.index] == '1'}">
								class="feel-table-daysquares background-yellow"
							</c:if>
								<c:if test="${list[parentStatus.index].feelYearList[10][status.index] == '2'}">
								class="feel-table-daysquares background-red"
							</c:if>>
							</td>
						</c:forEach>
					</tr>
					<tr>
						<td>12</td>
						<c:forEach begin="0" end="30" step="1" varStatus="status">
							<td
								<c:if test="${list[parentStatus.index].feelYearList[11][status.index] == '0'}">
								class="feel-table-daysquares background-green"
							</c:if>
								<c:if test="${list[parentStatus.index].feelYearList[11][status.index] == '1'}">
								class="feel-table-daysquares background-yellow"
							</c:if>
								<c:if test="${list[parentStatus.index].feelYearList[11][status.index] == '2'}">
								class="feel-table-daysquares background-red"
							</c:if>>
							</td>
						</c:forEach>
					</tr>
				</tbody>
			</table>
			<br>
		</c:forEach>
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
							selectedYear ++
							console.log("selectedYear:" + selectedYear)
							window.location.href = 'http://localhost:8080/2020project/FeelList?target_year='
									+ String(selectedYear)
						},
						beforeYear: function(){
							var selectedYear = Number(this.selected)
							selectedYear --
							console.log("selectedYear:" + selectedYear)
							window.location.href = 'http://localhost:8080/2020project/FeelList?target_year='
								+ String(selectedYear)	},
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
							window.location.href = 'http://localhost:8080/2020project/FeelList?target_year='
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
						console.log("初期のselectedは"+this.selected)
					}

				})
	</script>
</body>
</html>