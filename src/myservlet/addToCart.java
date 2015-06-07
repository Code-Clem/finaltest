package myservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class addToCart
 */
@WebServlet("/addToCart")
public class addToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public addToCart() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		String username = request.getParameter("username");// 用户名称
		int inventory = Integer.parseInt(request.getParameter("inventory"));// 商品库存数量
		String goodsId = request.getParameter("id");
		int goodsIdNum = Integer.parseInt(goodsId);// 商品id
		String str = "number" + goodsId;
		String str_num = request.getParameter(str);
		int goods_number = Integer.parseInt(str_num);// 商品数量
		String goods_name = null, state = "false";// 商品名称及购买的状态
		int goods_price = 0;
		if (!username.equals("zkm")) {// 如果是买家，则添加购物数量并进行数据库操作
			if (goods_number <= 0) {
				out.println("<script language = javascript>alert('添加失败,请输入正确购买数量!');");
				out.println("location.href='index.jsp'</script>");
			} else if (goods_number > inventory) {
				out.println("<script language = javascript>alert('您选择的数量超出库存,请重新输入!');");
				out.println("location.href='index.jsp'</script>");
			} else {
				DBConnection dbc = new DBConnection();
				Connection con = null;
				Statement stmt = null;
				try {
					con = dbc.Connection();
					stmt = dbc.stmt(con);
					String sql1 = "select name,price from goods where id = "
							+ goodsIdNum + "";// 查找对应id的商品名SQL语句
					ResultSet rs1 = dbc.queryInDB(sql1);
					if (rs1 != null) {
						while (rs1.next()) {
							goods_name = rs1.getString("name");
							goods_price = rs1.getInt("price");
						}
					}
					int price = goods_price * goods_number;// 当前选择商品的总价
					String sql2 = "select name from relation where goodsid = "
							+ goodsIdNum + " and username = '" + username
							+ "' and state = 'false'";// 查找对应id的商品名SQL语句,用于判断数据库信息

					String sql3 = "INSERT INTO relation(goodsid,username,name,number,price,state)Values("
							+ goodsIdNum
							+ ",'"
							+ username
							+ "','"
							+ goods_name
							+ "',"
							+ goods_number
							+ ","
							+ price
							+ ",'"
							+ state
							+ "')";// 插入数据SQL语句

					String sql4 = "UPDATE relation SET number = number + "
							+ goods_number + ",price=price+" + price
							+ " where goodsid = " + goodsIdNum + "";// 更新表relation数据SQL语句

					String sql5 = "UPDATE goods SET inventory = inventory - "
							+ goods_number + " where id = " + goodsIdNum + "";// 更新表relation中库存的数据SQL语句

					ResultSet rs2 = dbc.queryInDB(sql2);
					if (rs2 != null) { // 如果数据库中没有该商品的信息，则插入该数据
						if (rs2.next() == false) {
							int count1 = dbc.insertInDB(sql3);
							if (count1 > 0) {
								dbc.updateInDB(sql5);// 对库存数量进行更新
								out.println("<script language = javascript>alert('添加商品成功!');");
								out.println("location.href='index.jsp'</script>");
							} else {
								out.println("<script language = javascript>alert('添加商品失败!');");
								out.println("location.href='index.jsp'</script>");
							}
						} else { // 如果数据库中已有该商品的信息，则更新该数据
							int count2 = dbc.updateInDB(sql4);
							if (count2 > 0) {
								dbc.updateInDB(sql5);// 对库存数量进行更新
								out.println("<script language = javascript>alert('更新商品数量成功!');");
								out.println("location.href='index.jsp'</script>");
							} else {
								out.println("<script language = javascript>alert('更新商品数量失败!');");
								out.println("location.href='index.jsp'</script>");
							}
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (stmt != null)
					try {
						stmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if (con != null)
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			}
		} else {// 如果是卖家，则添加商品数量并进行数据库操作
			if (goods_number <= 0) {
				out.println("<script language = javascript>alert('添加失败,请输入正确数量!');");
				out.println("location.href='index.jsp'</script>");
			} else {
				DBConnection dbc = new DBConnection();
				Connection con = null;
				Statement stmt = null;
				try {
					con = dbc.Connection();
					stmt = dbc.stmt(con);
					String sql = "UPDATE goods SET inventory = inventory + "
							+ goods_number + " where id = " + goodsIdNum + "";// 更新表relation中库存的数据SQL语句
					int count = dbc.updateInDB(sql);
					if (count > 0) {
						out.println("<script language = javascript>alert('更新商品库存成功!');");
						out.println("location.href='index.jsp'</script>");
					} else {
						out.println("<script language = javascript>alert('更新商品库存失败!');");
						out.println("location.href='index.jsp'</script>");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (stmt != null)
					try {
						stmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if (con != null)
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
	}
}
