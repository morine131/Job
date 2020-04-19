<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="header.jsp" />
<title>パスワード修正画面</title>
</head>
<body>
	<div class="container" id="app">
		<h2>パスワード修正</h2>
		<div>
			<span class="user-name">社員名： ${ user_name} さん</span>
			<form class="logout-btn" method="post"
				action="${pageContext.request.contextPath}/Logout">
				<input class="btn btn-secondary" type="submit" value="ログアウト">
			</form>
		</div>
		<form class="newPass" method="post"
			action="${pageContext.request.contextPath}/UpdatePassword">
			<p>
				新しいパスワード ※半角英数字8文字以上 <input type="password" id="pass" name="newPass"required>
			</p>
			<p>
				パスワードの確認 <input type="password" id="confirm" required>
			</p>
			<p>
				<input class="btn" type="submit" value="修正する" onclick="return passCheck()">
			</p>
		</form>
		<form class="start-btn" method="get"
			action="${pageContext.request.contextPath}/Home">
			<input class="btn" type="submit" value="戻る">
		</form>
	</div>
	<script type="text/javascript">
			console.log("javascript効いてます")
            function passCheck(){
            	const regexp =  new RegExp(/^[a-z\d]{8,100}$/i) //正規表現: 半角英数字8文字以上100文字以下の正規表現
            	const passStr = document.getElementById("pass").value
            	console.log(regexp.test(passStr))
                if (regexp.test(passStr)){
                	if(passConfirm()){
                    	return true
                	}else{
                		alert("確認用のパスワードが一致しません");
                		return false
                	}
                }else if(passStr === ""){
                	return true;
                }else{
                    alert("パスワードは半角英数字8文字以上で入力してください");
                    return false;
                }
            }

            function passConfirm(){
            	const passStr = document.getElementById("pass").value
            	const confirmStr = document.getElementById("confirm").value
            	if(passStr === confirmStr){
            		return true
            	}else{
            		return false
            	}
            }
        </script>
</body>
</html>