<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="header.jsp" />
<title>報告書編集</title>
</head>
<body>
	<jsp:include page="nav.jsp" />
	<div class="container" id="app">
		<h2>業務報告</h2>
		{{ target_year }}/{{target_month}}/{{target_day}}
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
			<input type="submit" value="送信" class="btn btn-primary">
		</form>
	</div>
	<script>
	new Vue(
			{
				el : "#app",
				data : {
					target_year: null,
					target_month: null,
					target_day: null
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
						if (this.getPram('target_year') === '' || this.getPram('target_year') === null) {
							this.target_year = new Date().getFullYear() //プルダウンの初期値として現在年を指定
						} else {
							this.selected = this.getPram('target_year')
						}

						if (this.getPram('target_month') === '' || this.getPram('target_month') === null) {
							this.target_month = new Date().getMonth() +1 //プルダウンの初期値として現在年を指定
						} else {
							this.target_month = this.getPram('target_month')
						}

						if (this.getPram('target_day') === '' || this.getPram('target_day') === null) {
							this.target_day = new Date().getDate() //プルダウンの初期値として現在年を指定
						} else {
							this.target_day = this.getPram('target_day')
						}
					}
				},
				created: function(){
					this.setTarget()
				}

			})
		</script>
</body>
</html>