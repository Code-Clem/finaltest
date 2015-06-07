<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>卖家管理</title>
<link href="dist/bootstrap.min.css" rel="stylesheet" />
<script src="dist/jquery.min.js"></script>
<script src="dist/bootstrap.min.js"></script>
<link href="signin.css" rel="stylesheet" />
</head>
<body>
	<script type="text/javascript">
		function validate() {
			if (form1.file.value == "") {
				alert("请选择要上传的商品！");
				return false;
			}
			if (form1.goods_name.value == "") {
				alert("请填写商品名称！");
				return false;
			}
			if (form1.price.value == "") {
				alert("请填写商品价格！");
				return false;
			}
			if (form1.inventory.value == "") {
				alert("请填写商品的库存！");
				return false;
			}
			if (form1.introduce.value == "") {
				alert("请填写商品的简介！");
				return false;
			}
		}
	</script>
	<img src="image/title.png" class="img-responsive" width="280px"
		style="padding: 0 0 0 30px;">
	<div class="container" style="padding: 0px 30% 0px 30%;">
		<form class="form-signin" action="UploadServlet" method="post"
			enctype="multipart/form-data" role="form" name="form1" id="form1"
			onsubmit="return validate()">
			<h2 class="form-signin-heading">请添加商品：</h2>
			<div style="margin-top: 36px;">
				<input type="file" name="file" placeholder="picture">
			</div>
			<br>
			<div class="input-group">
				<span class="input-group-addon">商品名称：</span> <input type="text"
					class="form-control" placeholder="name" name="goods_name">
			</div>
			<br>
			<div class="input-group">
				<span class="input-group-addon">定价：</span> <input type="text"
					class="form-control" placeholder="price" name="price">
			</div>
			<br>
			<div class="input-group">
				<span class="input-group-addon">库存：</span> <input type="text"
					class="form-control" placeholder="inventory" name="inventory">
			</div>
			<br>
			<div class="input-group">
				<span class="input-group-addon">介绍：</span> <input type="text"
					class="form-control" placeholder="brief introduction"
					name="introduce">
			</div>
			<br>
			<div style="padding: 0px 24% 0px 24%;">
				<button class="btn btn-lg btn-primary" type="submit" value="上传">上传</button>
				<button class="btn btn-lg btn-primary" style="margin-left: 30px;"
					type="reset" value="重置">重置</button>
			</div>
		</form>
		<div>
			<button type="button" class="btn btn-link"
				onClick="location.href='index.jsp'">查看首页</button>
		</div>
	</div>
</body>
</html>