<%@page import="java.sql.*" contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="myservlet.DBConnection"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>购物车首页</title>
<link href="dist/bootstrap.min.css" rel="stylesheet" />
<script src="dist/jquery.min.js"></script>
<script src="dist/bootstrap.min.js"></script>
<link href="signin.css" rel="stylesheet" />

<script language=javascript>
	//判断输入格式，不能输入字母
	function isNumber(e, obj, withFraction) {
		if (!e) {
			e = window.event;
		}
		if (e.which) {
			key = e.which;
		} else {
			key = e.keyCode;
		}
		validNumber = false;
		if (key == 8 //Backspace
				|| key == 46 //Delete
				|| key >= 35 && key <= 37 //End, Home, Left
				|| key == 39 //Right
		) {
			validNumber = true;
		}
		if (!e.shiftKey) {
			//only check shift is not pressed
			if (withFraction && obj.value.length > 0 && key == 190 //period
					&& obj.value.indexOf(".") == -1) {
				validNumber = true;
			}
			if (key == 48 && obj.value.length > 0) { // 0
				validNumber = true;
			}
			if (key >= 49 && key <= 57) { // 1~9
				validNumber = true;
			}
		}
		if (!validNumber) {
			if (e.preventDefault) {
				e.preventDefault();
			} else {
				e.returnValue = false;
			}
		}
	}
</script>
<script type="text/javascript" language="javascript">
	//增加 和减少选择商品的数量
	function show(flag, numberId) {
		var num = parseInt(document.getElementById(numberId).value);
		if ("1" == flag) {
			num -= 1;
			if (num < 0)
				num = 0;
			document.getElementById(numberId).value = num;
		} else if ("2" == flag) {
			num += 1;
			document.getElementById(numberId).value = num;
		}
	}
</script>

</head>
<body>
	<%
		int i = 1;
		String username = (String) session.getAttribute("username");
	%>
	<nav class="navbar navbar-default" role="navigation">
	<div class="navbar-header">
		<a class="navbar-brand" href="index.jsp"
			style="font-size: 30px; color: #0066FF;">购物商城</a>
	</div>
	<div class="navbar-header" style="margin-top: 3px;">
		<a class="navbar-brand" href="index.jsp" style="font-size: 15px;"><%=username%>,欢迎您！</a>
		<button type="button" class="btn btn-link"
			onClick="location.href='login.jsp'"
			style="color: #FF3333; font-size: 12px; margin: 10px 50px auto 0px">切换用户</button>
	</div>
	<div>
		<form class="navbar-form navbar-left" role="search" name="form1">
			<div class="form-group">
				<input type="text" class="form-control" placeholder="Search"
					style="width: 360px; margin-right: 20px;" name="search_content">
			</div>
			<button type="submit" class="btn btn-default" name="search">搜索</button>
		</form>
		<%
			if (username.equals("zkm")) {//判断登录用户是否为卖家
		%>
		<button type="button" class="btn btn-link"
			onClick="location.href='seller.jsp'"
			style="color: #FF3333; font-size: 12px; margin: 11px 50px auto auto">继续添加商品</button>
		<a href="isSellerProcess.jsp"><img src="image/addfinal.png"
			class="navbar-right" style="margin-right: 20px;"><img
			class="navbar-right" src="image/seller-log.png"
			style="width: 25px; margin-top: 15px;" title="卖家查看"> </a>
		<%
			} else {
		%>
		<a href="isSellerProcess.jsp"><img src="image/addfinal.png"
			class="navbar-right" style="margin-right: 20px;"><img
			class="navbar-right" src="image/buyer-log.png"
			style="width: 25px; margin-top: 15px;" title="买家查看"> </a>
		<%
			}
		%>
	</div>
	</nav>

	<%
		String name, address, price, introduce, inventory;
		try {
			DBConnection dbc = new DBConnection();
			Connection con = dbc.Connection();
			Statement stmt = dbc.stmt(con);
			String sql = "select * from goods";
			ResultSet rs = dbc.queryInDB(sql);
			if (rs != null) {
				while (rs.next()) {
					name = rs.getString("name");
					address = rs.getString("path");
					price = rs.getString("price");
					introduce = rs.getString("introduce");
					inventory = rs.getString("inventory");
	%>
	<div class="container goods-div">
		<div>
			<h3><%=name%></h3>
		</div>
		<div>
			<img src="<%=request.getContextPath() + address%>"
				style="width: 250px; overflow: hidden;">
		</div>
		<div class="info-div">
			价格:<%=price%></div>
		<div class="info-div">
			库存：<%=inventory%></div>

		<div class="info-div">
			<%
				if (Integer.parseInt(inventory) == 0) {//如果库存没了，则商品简介为相关信息
			%>
			<button type="button" class="btn btn-warning" data-toggle="collapse"
				data-target="#demo<%=i%>">商品简介</button>
			<div id="demo<%=i%>" class="collapse">抱歉~没库存了，看看其他商品吧！</div>
			<%
				} else {//如果库存还有，则商品简介正常
			%>
			<button type="button" class="btn btn-success" data-toggle="collapse"
				data-target="#demo<%=i%>">商品简介</button>
			<div id="demo<%=i%>" class="collapse"><%=introduce%></div>
			<%
				}
			%>
		</div>
		<%
			String id = String.valueOf(i);
						session.setAttribute("username", username);
		%>
		<%
			if (!username.equals("zkm")) {//买家选择购买的商品数量
		%>
		<form action="addToCart" method="post" name="form2">
			<div class="col-lg-6" style="width: 150px; float: left;">
				<div class="input-group info-div">
					<div class="input-group-btn">
						<button type="button" class="btn btn-default" id="reduce"
							onclick="show('1', 'number<%=i%>')">-</button>
					</div>

					<input type="text" class="form-control" value="0" id="number<%=i%>"
						name="number<%=i%>" onkeydown="isNumber(event,this,true);">
					<div class="input-group-btn">
						<button type="button" class="btn btn-default" id="add"
							onclick="show('2', 'number<%=i%>')">+</button>
					</div>
				</div>
			</div>
			<input type="hidden" name="id" value=<%=id%>> <input
				type="hidden" name="inventory" value=<%=inventory%>> <input
				type="hidden" name="username" value=<%=username%>>
			<!--利用隐藏域传递id  -->
			<div style="width: 100px; float: left; margin-bottom: 20px;"
				class="info-div">
				<button class="btn btn-primary" type="submit">加入购物车</button>
			</div>

		</form>
		<%
			} else {//卖家选择添加的商品数量
		%>
		<form action="addToCart" method="post" name="form2">
			<div class="col-lg-6" style="width: 150px; float: left;">
				<div class="input-group info-div">
					<div class="input-group-btn">
						<button type="button" class="btn btn-default" id="reduce"
							onclick="show('1', 'number<%=i%>')">-</button>
					</div>

					<input type="text" class="form-control" value="0" id="number<%=i%>"
						name="number<%=i%>" onkeydown="isNumber(event,this,true);">
					<div class="input-group-btn">
						<button type="button" class="btn btn-default" id="add"
							onclick="show('2', 'number<%=i%>')">+</button>
					</div>
				</div>
			</div>
			<input type="hidden" name="id" value=<%=id%>> <input
				type="hidden" name="inventory" value=<%=inventory%>> <input
				type="hidden" name="username" value=<%=username%>>
			<div style="width: 100px; float: left; margin-bottom: 20px;"
				class="info-div">
				<button class="btn btn-primary" type="submit">添加商品数</button>
			</div>

		</form>
		<%
			}
		%>
	</div>

	<%
		i++;
				}
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	%>
</body>
</html>