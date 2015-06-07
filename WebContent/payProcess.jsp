<%@page import="java.sql.*" contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="myservlet.DBConnection"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		String username = (String) session.getAttribute("username");
		try {
			DBConnection dbc = new DBConnection();
			Connection con = dbc.Connection();
			Statement stmt = dbc.stmt(con);
			String sql = "update relation set state = 'true' where username = '"
					+ username + "'";
			int count = dbc.updateInDB(sql);
			if (count > 0) {
				out.println("<script language = javascript>alert('付款成功!');");
				out.println("location.href='lookup_buyer.jsp'</script>");
			} else {
				out.println("<script language = javascript>alert('付款失败!');");
				out.println("location.href='lookup_buyer.jsp'</script>");
			}
			if (stmt != null)
				stmt.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	%>
</body>
</html>