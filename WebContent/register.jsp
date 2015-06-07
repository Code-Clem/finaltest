<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册您的购物车</title>
<link href="dist/bootstrap.min.css" rel="stylesheet" />
<script src="dist/jquery.min.js"></script>
<script src="dist/bootstrap.min.js"></script>
<link href="signin.css" rel="stylesheet" />
</head>

<body>
	<img src="image/title.png" width="280px" style="padding: 0 0 0 30px;">
	<div class="container" style="padding: 5px 30% 0px 30%;">
		<form class="form-signin" action="InfoProcess" method="post"
			role="form">
			<h2 class="form-signin-heading">欢迎注册</h2>
			<div class="input-group" style="margin-top: 36px;">
				<span class="input-group-addon">用户名：</span> <input type="text"
					class="form-control" width="500px" placeholder="Shopping car"
					name="username">
			</div>
			<br>
			<div class="input-group">
				<span class="input-group-addon">密 码：</span> <input type="password"
					class="form-control" placeholder="Password" name="password1">
			</div>
			<br>
			<div class="input-group">
				<span class="input-group-addon">确认密码：</span> <input type="password"
					class="form-control" placeholder="Password" name="password2">
			</div>
			<br>
			<div class="input-group">
				<span class="input-group-addon">收货地址：</span> <input type="text"
					class="form-control" placeholder="address" name="address">
			</div>
			<br>
			<div class="input-group">
				<span class="input-group-addon">联系方式：</span> <input type="text"
					class="form-control" placeholder="phonenumber" name="phone">
			</div>
			<br>
			<div style="padding: 0px 25% 0px 25%;">
				<button class="btn btn-lg btn-primary" type="submit" value="注册">注册</button>
				<button class="btn btn-lg btn-info" style="margin-left: 30px;"
					type="reset" value="重置">重置</button>
			</div>
			<button type="button" class="btn btn-link"
				onClick="location.href='login.jsp'">已有账户</button>
		</form>
	</div>
</body>
</html>