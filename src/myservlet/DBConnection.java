package myservlet;

import java.sql.*;

public class DBConnection {
	private Connection conn;
	private String url = "jdbc:mysql://localhost:3306/finaltest?useUnicode=true&characterEncoding=UTF-8"; // ָ���������ݿ��URL
	private String user = "root"; // ָ���������ݿ���û���
	private String passWord = ""; // ָ���������ݿ������
	private ResultSet rs;
	private Statement sta;

	public java.sql.Connection Connection() {
		try {
			// �������ݿ�����
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, passWord);// �������ݿ�����
			if (conn != null) { // ���Connectionʵ����Ϊ��
				System.out.println("���ݿ����ӳɹ�"); // ��ʾ��Ϣ
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
