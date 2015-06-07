package myservlet;

import java.sql.*;

public class DBConnection {
	private Connection conn;
	private String url = "jdbc:mysql://localhost:3306/finaltest?useUnicode=true&characterEncoding=UTF-8"; // 指定连接数据库的URL
	private String user = "root"; // 指定连接数据库的用户名
	private String passWord = ""; // 指定连接数据库的密码
	private ResultSet rs;
	private Statement sta;

	public java.sql.Connection Connection() {
		try {
			// 加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, passWord);// 创建数据库连接
			if (conn != null) { // 如果Connection实例不为空
				System.out.println("数据库连接成功"); // 提示信息
			}
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		return conn;
	}

	public Statement stmt(Connection conn) throws SQLException {
		sta = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		return sta;
	}

	public ResultSet queryInDB(String sql) throws SQLException {
		rs = sta.executeQuery(sql);
		return rs;
	}

	public int insertInDB(String sqlStr) throws SQLException {
		int i = sta.executeUpdate(sqlStr);
//		System.out.print(i);
		return i;
	}

	public int updateInDB(String sqlStr) throws SQLException {
		int i = sta.executeUpdate(sqlStr);
//		System.out.print(i);
		return i;
	}
	
	public int deleteInDB(String sqlStr) throws SQLException {
		int i = sta.executeUpdate(sqlStr);
//		System.out.print(i);
		return i;
	}
	
	public void closeConnection() throws SQLException {
		if (rs != null)
			rs.close();
		if (sta != null)
			sta.close();
		if (conn != null)
			conn.close();
	}

}
