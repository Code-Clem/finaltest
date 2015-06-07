package myservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class sellerProcess
 */
@WebServlet("/SellerProcess")
public class SellerProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SellerProcess() {
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

		String name = "zkm";
		String pwd = "12345";
		String username = request.getParameter("sellername");
		String input_pwd = request.getParameter("pwd");
		PrintWriter out = response.getWriter();
		if (username == null || username == "") {
			out.println("<script language = javascript>alert('帐号不能为空!');");
			out.println("location.href='seller_login.jsp'</script>");
		} else if (input_pwd == null || input_pwd == "") {
			out.println("<script language = javascript>alert('密码不能为空!');");
			out.println("location.href='seller_login.jsp'</script>");
		}
		if (username.equals(name) && input_pwd.equals(pwd)) {
			session.setAttribute("username", username);
			session.setAttribute("isValued", "1");
			out.println("<script language = javascript>alert('登录成功!');");
			out.println("location.href='seller.jsp'</script>");
		} else {
			out.println("<script language = javascript>alert('登录失败,请重新输入!');");
			out.println("location.href='seller_login.jsp'</script>");
		}
	}

}
