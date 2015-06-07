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

<script type="text/javascript" language="javascript">//删除
	function firm(goodsid) {
		//利用对话框返回的值 （true 或者 false）
		var goodsidNum = parseInt(goodsid);
		if (confirm("您确定要删除该商品？"))
		{
			//如果是true ，那么就把页面转向delete.jsp
			location.href = "deleteProcess.jsp?id=" + goodsidNum + "";
		}
	}
</script>

<script type="text/javascript" language="javascript">//付款
	function pay() {
		if (confirm("您确定要付款？"))
		{
			location.href = "payProcess.jsp";
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
		<button class="btn btn-info" value="查看购物历史"
			onClick="location.href='history.jsp'">查看购物历史</button>
	</div>
	<div class="container" style="margin-top: 10px;">
		<h2>顾客购物详细情况</h2>
		<table id="table" class="table table-striped">
			<thead>
				<tr>
					<th><h4>添加的商品</h4>
					</th>
					<th><h4>数量(个)</h4>
					</th>
					<th><h4>价格（元）</h4>
					</th>
					<th><h4>编辑</h4></th>
				</tr>
				<%
					String goodsid, user, goodsname, number, price, state;
					int priceNum, goodsidNum;
					int total = 0;
					String username = (String) session.getAttribute("username");
					session.setAttribute("username", username);
					try {
						DBConnection dbc = new DBConnection();
						Connection con = dbc.Connection();
						Statement stmt = dbc.stmt(con);
						String sql = "select * from relation where username = '"
								+ username + "' and state = 'false'";
						ResultSet rs = dbc.queryInDB(sql);
						if (rs != null) {
							if (rs.next() == true) {
								rs.beforeFirst();
								while (rs.next()) {
									goodsid = rs.getString("goodsid");
									user = rs.getString("username");
									goodsname = rs.getString("name");
									number = rs.getString("number");
									price = rs.getString("price");
									state = rs.getString("state");
									priceNum = Integer.parseInt(price);
									goodsidNum = Integer.parseInt(goodsid);
									total += priceNum;
				%>
			</thead>
			<tr>
				<td><%=goodsname%></td>
				<td><%=number%></td>
				<td><%=price%></td>
				<td><button class="btn btn-danger" value="删除"
						onClick="firm(<%=goodsidNum%>)">删&nbsp;&nbsp;除</button>
				</td>
			</tr>
			<%
				}
						} else if (rs.next() == false) {
							response.sendRedirect("noGoods.jsp");
						}
			%>
			
			<%
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
			<tr>
				<td></td>
				<td></td>
				<td>总计：<%=total%></td>
				<td><button class="btn btn-success" value="付款" onClick="pay()">付&nbsp;&nbsp;款</button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>