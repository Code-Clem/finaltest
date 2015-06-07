package myservlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public UploadServlet() {
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
		String adjunctname = null;
		String path = null;
		FileItem formitem = null;
		File saveFile = null;
		String[] info = new String[4];
		int i = 0;
		String fileDir = getServletContext().getRealPath("/upload");
		System.out.println("fileDir: " + fileDir);
		// ָ���ϴ��ļ��ı����ַ
		String address = "";
		ArrayList<Object> uploadFiles = new ArrayList<Object>();

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		if (ServletFileUpload.isMultipartContent(request)) { // �ж��Ƿ����ϴ��ļ�
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(20 * 1024); // �����ڴ�������洢���ֽ���
			factory.setRepository(factory.getRepository()); // ���ô����ʱ�ļ���Ŀ¼
			ServletFileUpload upload = new ServletFileUpload(factory); // �����µ��ϴ��ļ����

			int size = 10 * 1024 * 1024; // ָ���ϴ��ļ��Ĵ�С
			List<?> formlists = null; // ���������ϴ��ļ��ļ��϶���
			try {
				formlists = upload.parseRequest(request); // ��ȡ�ϴ��ļ�����
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			Iterator<?> iter = formlists.iterator(); // ��ȡ�ϴ��ļ�������
			while (iter.hasNext()) {
				formitem = (FileItem) iter.next(); // ��ȡÿ���ϴ��ļ�
				if (!formitem.isFormField()) { // ���Բ����ϴ��ļ��ı���
					String name = formitem.getName().toString(); // ��ȡ�ϴ��ļ�������
					if (formitem.getSize() > size) { // ����ϴ��ļ����ڹ涨���ϴ��ļ��Ĵ�С
						break; // �˳�����
					}
					String adjunctsize = new Long(formitem.getSize())
							.toString(); // ��ȡ�ϴ��ļ��Ĵ�С
					if ((name == null) || (name.equals(""))
							&& (adjunctsize.equals("0"))) // ����ϴ��ļ�Ϊ��
						continue; // �˳�����
					adjunctname = name.substring(name.lastIndexOf("\\") + 1,
							name.length());
					address = fileDir + "\\" + adjunctname; // �����ϴ��ļ��ı����ַ

					saveFile = new File(address); // �����ļ������ַ�������ļ�
					try {
						formitem.write(saveFile);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // ���ļ�д����
					uploadFiles.add("upload/" + adjunctname);
				} else if (formitem.isFormField()) {
					String value = formitem.getString("UTF-8");
					info[i] = value;
					i++;
				}
			}
			DBConnection dbc = new DBConnection();
			Connection con = null;
			Statement stmt = null;
			try {
				path = "/" + "upload" + "/" + adjunctname;
				String goods_name = info[0];
				String price = info[1];
				int inventory = Integer.parseInt(info[2]);
				String introduce = info[3];
				con = dbc.Connection();
				stmt = dbc.stmt(con);
				String sql = "INSERT INTO goods(name,path,price,introduce,inventory)Values('"
						+ goods_name
						+ "','"
						+ path
						+ "','"
						+ price
						+ "','"
						+ introduce
						+ "','"
						+ inventory + "')";
				int count = dbc.insertInDB(sql);
				PrintWriter out = response.getWriter();
				if (count > 0) {
					out.println("<script language = javascript>if(confirm('��Ʒ�ϴ������ɹ����Ƿ�����ϴ���')){");
					out.print("location.href='seller.jsp'}");
					out.print("else{location.href='index.jsp'}</script>");
				} else {
					out.println("<script language = javascript>alert('��Ʒ�ϴ�ʧ�ܣ�');");
					out.println("location.href='seller.jsp'</script>");
				}
			} catch (Exception e) {
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
