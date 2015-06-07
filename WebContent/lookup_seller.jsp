<%@page import="java.sql.*" contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="myservlet.DBConnection"%>
<%@ page import="org.jfree.chart.ChartFactory"%>
<%@ page import="org.jfree.chart.JFreeChart"%>
<%@ page import="org.jfree.data.category.DefaultCategoryDataset"%>
<%@ page import="org.jfree.chart.plot.PlotOrientation"%>
<%@ page import="org.jfree.chart.entity.StandardEntityCollection"%>
<%@ page import="org.jfree.chart.ChartRenderingInfo"%>
<%@ page import="org.jfree.chart.servlet.ServletUtilities"%>
<%@ page import="org.jfree.data.category.DefaultCategoryDataset"%>
<%@ page import="org.jfree.chart.StandardChartTheme"%>
<%@ page import="java.awt.Font"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	try {//循环显示买家购买商品的信息
		DBConnection dbc = new DBConnection();
		Connection con = dbc.Connection();
		Statement stmt = dbc.stmt(con);
		String user, goodsname, number, price, state;

		StandardChartTheme standardChartTheme = new StandardChartTheme(
				"CN"); //创建主题样式
		standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD,
				20)); //设置标题字体
		standardChartTheme.setRegularFont(new Font("微软雅黑", Font.PLAIN,
				15)); //设置图例的字体
		standardChartTheme
				.setLargeFont(new Font("微软雅黑", Font.PLAIN, 15)); //设置轴向的字体
		ChartFactory.setChartTheme(standardChartTheme); //设置主题样式
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		String sq2 = "select * from relation";
		ResultSet rs1 = dbc.queryInDB(sq2);
		if (rs1 != null) {
			while (rs1.next()) {
				String user1 = rs1.getString("username");
				String goodsname1 = rs1.getString("name");
				String number1 = rs1.getString("number");
				String price1 = rs1.getString("price");
				dataset.addValue(Integer.parseInt(number1), user1,
						goodsname1);
			}
		}

		//创建JFreeChart组件的图表对象
		JFreeChart chart = ChartFactory.createBarChart3D("商品销量图", //图表标题
				"商品", //x轴的显示标题
				"销量", //y轴的显示标题
				dataset, //数据集
				PlotOrientation.VERTICAL,//图表方向(垂直)
				true, //是否包含图例
				false, //是否包含提示
				false //是否包含URL
				);
		//设置图表的文件名
		// 固定用法
		ChartRenderingInfo info = new ChartRenderingInfo(
				new StandardEntityCollection());
		String fileName = ServletUtilities.saveChartAsPNG(chart, 600,
				360, info, session);
		String url = request.getContextPath()
				+ "/servlet/DisplayChart?filename=" + fileName;
%><!--以上内容为统计表的创建  -->

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看顾客购物车</title>
<link href="dist/bootstrap.min.css" rel="stylesheet" />
<script src="dist/jquery.min.js"></script>
<script src="dist/bootstrap.min.js"></script>
<link href="signin.css" rel="stylesheet" />
</head>
<body>
	<img src="image/title.png" class="img-responsive" width="280px"
		style="padding: 0 0 0 30px;">
	<div style="position: absolute; right: 50px; top: 60px;">
		<button type="button" class="btn btn-info"
			onClick="location.href='index.jsp'">返回首页</button>
	</div>

	<table class="container" width="100%" border="0" cellspacing="0"
		cellpadding="0">
		<tr>
			<td>&nbsp;<img src="<%=url%>"></td>
			<!--引用了统计图  -->
		</tr>
	</table>

	<div class="container" style="margin-top: 30px;">
		<h2>顾客购物详细情况</h2>
		<table class="table table-striped">
			<thead>
				<tr>
					<th><h4>用户</h4>
					</th>
					<th><h4>购买商品</h4>
					</th>
					<th><h4>数量(个)</h4>
					</th>
					<th><h4>价格（元）</h4>
					</th>
					<th><h4>付款情况</h4>
					</th>
				</tr>
				<%
					String username = (String) session.getAttribute("username");
						/* try { *///循环显示买家购买商品的信息
						String sql = "select * from relation ORDER BY CONVERT(username USING gbk)";//按照用户名排序
						ResultSet rs = dbc.queryInDB(sql);
						if (rs != null) {
							while (rs.next()) {
								user = rs.getString("username");
								goodsname = rs.getString("name");
								number = rs.getString("number");
								price = rs.getString("price");
								state = rs.getString("state");
								dataset.addValue(Integer.parseInt(number), user,
										goodsname);
				%>
			</thead>
			<tr>
				<td><%=user%></td>
				<td><%=goodsname%></td>
				<td><%=number%></td>
				<td><%=price%></td>

				<%
					if (state.equals("false")) {
				%>
				<td><button class="btn btn-danger disabled" value="买家未付款">买家未付款</button></td>
				<%
					} else {
				%>
				<td><button class="btn btn-success disabled" value="买家已付款">买家已付款</button>
				</td>
				<%
					}
				%>
			</tr>
			<%
				}
						if (rs != null)
							rs.close();
						if (rs1 != null)
							rs1.close();
						if (stmt != null)
							stmt.close();
						if (con != null)
							con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			%>
		</table>
	</div>
</body>
</html>