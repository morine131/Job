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
		<c:if test="${ user_type == '1' || user_type == '2' }"><jsp:include page="nav.jsp" /></c:if>
	<div class="container" id="app">
		<div class="manage-top">
		<form class="admin-home-btn" method="get" action="${pageContext.request.contextPath}/Home">
			<input class="btn btn-sm btn-outline-secondary back-btn" type="submit" value="戻る">
		</form>
		<h2 class="inline-block">パスワード修正</h2>
		</div>
		<div>
			<span class="user-name">社員名： ${ user_name} さん</span>
		</div>
		<form class="newPass" method="post"
			action="${pageContext.request.contextPath}/UpdatePassword">
			<p>
				 <input type="password" id="pass" name="newPass" required placeholder="新しいパスワード" class="newpass">
			<br><span class="pass-message">※半角英数字8文字以上</span>
			</p>

			<p>
				 <input type="password" id="confirm" class="confirm-pass" required placeholder="パスワードの確認">
			</p>
			<p>
				<input class="btn btn-primary update-pass" type="submit" value="修正する" onclick="return passCheck()">
			</p>
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