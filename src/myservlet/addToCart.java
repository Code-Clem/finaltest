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

		String username = request.getParameter("username");// �û�����
		int inventory = Integer.parseInt(request.getParameter("inventory"));// ��Ʒ�������
		String goodsId = request.getParameter("id");
		int goodsIdNum = Integer.parseInt(goodsId);// ��Ʒid
		String str = "number" + goodsId;
		String str_num = request.getParameter(str);
		int goods_number = Integer.parseInt(str_num);// ��Ʒ����
		String goods_name = null, state = "false";// ��Ʒ���Ƽ������״̬
		int goods_price = 0;
		if (!username.equals("zkm")) {// �������ң�����ӹ����������������ݿ����
			if (goods_number <= 0) {
				out.println("<script language = javascript>alert('���ʧ��,��������ȷ��������!');");
				out.println("location.href='index.jsp'</script>");
			} else if (goods_number > inventory) {
				out.println("<script language = javascript>alert('��ѡ��������������,����������!');");
				out.println("location.href='index.jsp'</script>");
			} else {
				DBConnection dbc = new DBConnection();
				Connection con = null;
				Statement stmt = null;
				try {
					con = dbc.Connection();
					stmt = dbc.stmt(con);
					String sql1 = "select name,price from goods where id = "
							+ goodsIdNum + "";// ���Ҷ�Ӧid����Ʒ��SQL���
					ResultSet rs1 = dbc.queryInDB(sql1);
					if (rs1 != null) {
						while (rs1.next()) {
							goods_name = rs1.getString("name");
							goods_price = rs1.getInt("price");
						}
					}
					int price = goods_price * goods_number;// ��ǰѡ����Ʒ���ܼ�
					String sql2 = "select name from relation where goodsid = "
							+ goodsIdNum + " and username = '" + username
							+ "' and state = 'false'";// ���Ҷ�Ӧid����Ʒ��SQL���,�����ж����ݿ���Ϣ

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
							+ "')";// ��������SQL���

					String sql4 = "UPDATE relation SET number = number + "
							+ goods_number + ",price=price+" + price
							+ " where goodsid = " + goodsIdNum + "";// ���±�relation����SQL���

					String sql5 = "UPDATE goods SET inventory = inventory - "
							+ goods_number + " where id = " + goodsIdNum + "";// ���±�relation�п�������SQL���

					ResultSet rs2 = dbc.queryInDB(sql2);
					if (rs2 != null) { // ������ݿ���û�и���Ʒ����Ϣ������������
						if (rs2.next() == false) {
							int count1 = dbc.insertInDB(sql3);
							if (count1 > 0) {
								dbc.updateInDB(sql5);// �Կ���������и���
								out.println("<script language = javascript>alert('�����Ʒ�ɹ�!');");
								out.println("location.href='index.jsp'</script>");
							} else {
								out.println("<script language = javascript>alert('�����Ʒʧ��!');");
								out.println("location.href='index.jsp'</script>");
							}
						} else { // ������ݿ������и���Ʒ����Ϣ������¸�����
							int count2 = dbc.updateInDB(sql4);
							if (count2 > 0) {
								dbc.updateInDB(sql5);// �Կ���������и���
								out.println("<script language = javascript>alert('������Ʒ�����ɹ�!');");
								out.println("location.href='index.jsp'</script>");
							} else {
								out.println("<script language = javascript>alert('������Ʒ����ʧ��!');");
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
		} else {// ��������ң��������Ʒ�������������ݿ����
			if (goods_number <= 0) {
				out.println("<script language = javascript>alert('���ʧ��,��������ȷ����!');");
				out.println("location.href='index.jsp'</script>");
			} else {
				DBConnection dbc = new DBConnection();
				Connection con = null;
				Statement stmt = null;
				try {
					con = dbc.Connection();
					stmt = dbc.stmt(con);
					String sql = "UPDATE goods SET inventory = inventory + "
							+ goods_number + " where id = " + goodsIdNum + "";// ���±�relation�п�������SQL���
					int count = dbc.updateInDB(sql);
					if (count > 0) {
						out.println("<script language = javascript>alert('������Ʒ���ɹ�!');");
						out.println("location.href='index.jsp'</script>");
					} else {
						out.println("<script language = javascript>alert('������Ʒ���ʧ��!');");
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
