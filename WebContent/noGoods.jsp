<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>购物车为空</title>
<link href="dist/bootstrap.min.css" rel="stylesheet" />
<script src="dist/jquery.min.js"></script>
<script src="dist/bootstrap.min.js"></script>
<link href="signin.css" rel="stylesheet" />
</head>
<body>
	<img src="image/title.png" class="img-responsive" width="280px"
		style="padding: 0 0 0 30px;">
	<div style="position: absolute; right: 50px; top: 50px;">
		<a href="index.jsp"><img src="image/addto.png"> </a>
	</div>
	<div style="margin-left: 30px;">
		<button class="btn btn-info" value="查看购物历史"
			onClick="location.href='history.jsp'">查看购物历史</button>
	</div>
	<img src="image/cartnull.png" class="container img-responsive"
		style="padding: 0 auto; width: 800px; margin-top: 50px;">

</body>
</html>