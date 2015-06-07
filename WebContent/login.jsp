<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>购物车系统</title>
<link href="dist/bootstrap.min.css" rel="stylesheet" />
<script src="dist/jquery.min.js"></script>
<script src="dist/bootstrap.min.js"></script>
<link href="signin.css" rel="stylesheet" />
</head>
<body>
	<img src="image/title.png" class="img-responsive" width="280px"
		style="padding: 0 0 0 30px;">
	<div class="container" style="padding: 5px 30% 0px 30%;">
		<form class="form-signin" action="LoginProcess" method="post"
			role="form">
			<h2 class="form-signin-heading">欢迎登录</h2>
			<div class="input-group" style="margin-top: 36px;">
				<span class="input-group-addon">购物车帐号：</span> <input type="text"
					class="form-control" placeholder="Username" name="username">
			</div>
			<br>
			<div class="input-group">
				<span class="input-group-addon">密 码：</span> <input type="password"
					class="form-control" placeholder="Password" name="password">
			</div>
			<br>
			<div style="padding: 0px 24% 0px 24%;">
				<button class="btn btn-lg btn-primary" type="submit" value="登录">登录</button>
				<button class="btn btn-lg btn-primary" style="margin-left: 30px;"
					type="button" value="注册" onClick="location.href='register.jsp'">注册</button>
			</div>
		</form>
		<div>
			<button type="button" class="btn btn-link" title="只有卖家帐号可以进入"
				onClick="location.href='seller_login.jsp'">卖家登录</button>
		</div>
	</div>

</body>
</html>