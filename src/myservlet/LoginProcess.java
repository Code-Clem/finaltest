package myservlet;

import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import myservlet.DBConnection;

/**
 * Servlet implementation class InfoProcess
 */
@WebServlet("/LoginProcess")
public class LoginProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginProcess() throws ClassNotFoundException, SQLException {
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
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		String input_pwd = request.getParameter("password");

		if (username == null || username == "") {
			out.println("<script language = javascript>alert('帐号不能为空!');");
			out.println("location.href='login.jsp'</script>");
		} else if (input_pwd == null || input_pwd == "") {
			out.println("<script language = javascript>alert('密码不能为空!');");
			out.println("location.href='login.jsp'</script>");
		}
		DBConnection dbc = new DBConnection();
		Connection con = dbc.Connection();
		try {
			@SuppressWarnings("unused")
			Statement stmt = dbc.stmt(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String sql = "select * from  buyer where username='" + username + "';";
		ResultSet rs = null;
		try {
			rs = dbc.queryInDB(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (rs.next()) {
				String password = new String(rs.getString("password"));
				if (password.equals(input_pwd)) {
					session.setAttribute("username", username);
					session.setAttribute("isValued", "1");
					out.println("<script language = javascript>alert('登录成功!');");
					out.println("location.href='index.jsp'</script>");
				} else {
					out.println("<script language = javascript>alert('密码错误，请重新登录!');");
					out.println("location.href='login.jsp'</script>");
				}
			} else {
				out.println("<script language = javascript>alert('该用户不存在，请重新登录!');");
				out.println("location.href='login.jsp'</script>");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
