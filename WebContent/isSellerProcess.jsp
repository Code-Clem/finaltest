<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		String username = (String) session.getAttribute("username");
		if(username.equals("zkm")){
			session.setAttribute("isValued", "1");
			response.sendRedirect("lookup_seller.jsp");
		}else{
			session.setAttribute("isValued", "1");
			response.sendRedirect("lookup_buyer.jsp");
		}
	%>
</body>
</html>