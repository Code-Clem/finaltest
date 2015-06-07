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
		// 指定上传文件的保存地址
		String address = "";
		ArrayList<Object> uploadFiles = new ArrayList<Object>();

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		if (ServletFileUpload.isMultipartContent(request)) { // 判断是否是上传文件
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(20 * 1024); // 设置内存中允许存储的字节数
			factory.setRepository(factory.getRepository()); // 设置存放临时文件的目录
			ServletFileUpload upload = new ServletFileUpload(factory); // 创建新的上传文件句柄

			int size = 10 * 1024 * 1024; // 指定上传文件的大小
			List<?> formlists = null; // 创建保存上传文件的集合对象
			try {
				formlists = upload.parseRequest(request); // 获取上传文件集合
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			Iterator<?> iter = formlists.iterator(); // 获取上传文件迭代器
			while (iter.hasNext()) {
				formitem = (FileItem) iter.next(); // 获取每个上传文件
				if (!formitem.isFormField()) { // 忽略不是上传文件的表单域
					String name = formitem.getName().toString(); // 获取上传文件的名称
					if (formitem.getSize() > size) { // 如果上传文件大于规定的上传文件的大小
						break; // 退出程序
					}
					String adjunctsize = new Long(formitem.getSize())
							.toString(); // 获取上传文件的大小
					if ((name == null) || (name.equals(""))
							&& (adjunctsize.equals("0"))) // 如果上传文件为空
						continue; // 退出程序
					adjunctname = name.substring(name.lastIndexOf("\\") + 1,
							name.length());
					address = fileDir + "\\" + adjunctname; // 创建上传文件的保存地址

					saveFile = new File(address); // 根据文件保存地址，创建文件
					try {
						formitem.write(saveFile);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // 向文件写数据
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
					out.println("<script language = javascript>if(confirm('商品上传发布成功！是否继续上传？')){");
					out.print("location.href='seller.jsp'}");
					out.print("else{location.href='index.jsp'}</script>");
				} else {
					out.println("<script language = javascript>alert('商品上传失败！');");
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
