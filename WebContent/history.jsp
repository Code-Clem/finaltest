<%@page import="java.sql.*" contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="myservlet.DBConnection"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看我的购物车</title>
<link href="dist/bootstrap.min.css" rel="stylesheet" />
<script src="dist/jquery.min.js"></script>
<script src="dist/bootstrap.min.js"></script>
<link href="signin.css" rel="stylesheet" />

<script type="text/javascript" language="javascript">
	//删除
	function firm(goodsid) {
		//利用对话框返回的值 （true 或者 false）
		var goodsidNum = parseInt(goodsid);
		if (confirm("您确定要删除该商品？")) {
			//如果是true ，那么就把页面转向delete.jsp
			location.href = "deleteProcess.jsp?id=" + goodsidNum + "";
		}
	}
</script>

<script type="text/javascript" language="javascript">
	//付款
	function pay() {
		if (confirm("您确定要付款？")) {
			location.href = "payProcess.jsp?id=" + goodsidNum + "";
		}
	}
</script>

</head>
<body>
	<img src="image/title.png" class="img-responsive" width="280px"
		style="padding: 0 0 0 30px;">
	<div style="position: absolute; right: 50px; top: 50px;">
		<a href="index.jsp"><img src="image/addto.png"> </a>
	</div>
	<div style="margin-left: 30px;">
		<button class="btn btn-info" value="返回我的购物车"
			onClick="location.href='lookup_buyer.jsp'">返回我的购物车</button>
	</div>

	<div class="container" style="margin-top: 10px;">
		<h2>顾客购物详细情况</h2>
		<table class="table table-striped">
			<thead>
				<tr>
					<th><h4>添加的商品</h4></th>
					<th><h4>数量(个)</h4></th>
					<th><h4>价格（元）</h4></th>
					<th><h4>状态</h4>
					</th>
				</tr>
				<%
					String goodsid, user, goodsname, number, price, state;
					int priceNum, goodsidNum;
					int total = 0;
					String username = (String) session.getAttribute("username");
					try {
						DBConnection dbc = new DBConnection();
						Connection con = dbc.Connection();
						Statement stmt = dbc.stmt(con);
						String sql1 = "select * from relation where username = '"
								+ username + "' and state = 'false'";
						String sql2 = "select * from relation where username = '"
								+ username + "'";
						ResultSet rs1 = dbc.queryInDB(sql1);
						if (rs1 != null) {
							while (rs1.next()) {
								String price1 = rs1.getString("price");
								int priceNum1 = Integer.parseInt(price1);
								total += priceNum1;
							}
						}
						ResultSet rs2 = dbc.queryInDB(sql2);
						if (rs2 != null) {
							if (rs2.next() == true) {
								rs2.beforeFirst();
								while (rs2.next()) {
									goodsid = rs2.getString("goodsid");
									user = rs2.getString("username");
									goodsname = rs2.getString("name");
									number = rs2.getString("number");
									price = rs2.getString("price");
									state = rs2.getString("state");
									priceNum = Integer.parseInt(price);
									goodsidNum = Integer.parseInt(goodsid);
				%>
			</thead>
			<tr>
				<td><%=goodsname%></td>
				<td><%=number%></td>
				<td><%=price%></td>
				<%
					if (state.equals("false")) {
				%>
				<td><button class="btn btn-danger disabled" value="未付款">未付款</button>
				</td>
				<%
					} else {
				%>
				<td><button class="btn btn-success disabled" value="已付款">已付款</button>
				</td>
				<%
					}
				%>
			</tr>
			<%
				}
						}
			%>
			<tr>
				<td></td>
				<td></td>
				<td>总计：<%=total%></td>
				<td><button class="btn btn-warning disabled" value="待付款">待付款</button>
				</td>
			</tr>
		</table>
	</div>
	<%
		if (rs1 != null)
					rs1.close();
				if (rs2 != null)
					rs2.close();
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