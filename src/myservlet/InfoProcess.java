package myservlet;

import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import myservlet.DBConnection;

/**
 * Servlet implementation class InfoProcess
 */
@WebServlet("/InfoProcess")
public class InfoProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InfoProcess() throws ClassNotFoundException, SQLException {
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
		String carname = request.getParameter("username");
		String pwd1 = request.getParameter("password1");
		String pwd2 = request.getParameter("password2");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		PrintWriter out = response.getWriter();
		if (carname == null || carname == "") {
			out.println("<script language = javascript>alert('用户名不能为空!');");
			out.println("location.href='register.jsp'</script>");
		} else if (pwd1 == null || pwd1 == "") {
			out.println("<script language = javascript>alert('密码不能为空!');");
			out.println("location.href='register.jsp'</script>");
		} else if (!pwd1.equals(pwd2)) {
			out.println("<script language = javascript>alert('两次密码不一致!');");
			out.println("location.href='register.jsp'</script>");
		} else if (address == null || address == "") {
			out.println("<script language = javascript>alert('地址不能为空!');");
			out.println("location.href='register.jsp'</script>");
		} else if (phone == null || phone == "") {
			out.println("<script language = javascript>alert('号码不能为空!');");
			out.println("location.href='register.jsp'</script>");
		} else {

			DBConnection dbc = new DBConnection();
			Connection con = null;
			Statement stmt = null;
			try {
				con = dbc.Connection();
				stmt = dbc.stmt(con);
				String sql = "INSERT INTO buyer(username,password,address,phone)Values('"
						+ carname
						+ "','"
						+ pwd1
						+ "','"
						+ address
						+ "','"
						+ phone + "')";
				int count = dbc.insertInDB(sql);
				if (count > 0) {
					out.println("<script language = javascript>alert('注册成功，请登录!');");
					out.println("location.href='login.jsp'</script>");
				} else {
					out.println("<script language = javascript>alert('注册失败!');");
					out.println("location.href='register.jsp'</script>");
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
