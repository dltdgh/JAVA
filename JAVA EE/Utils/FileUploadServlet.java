import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.omg.CORBA.portable.ValueBase;


public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public TestServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//获取磁盘条目工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//获取服务器绝对路径
		String path = request.getSession().getServletContext().getRealPath("/upload");  
		//System.out.println(path);
		
		//目录不存在则新建目录
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
		//设置暂时存放文件的存储室，这个存储室可以和最终存储文件的文件夹不同。因为当文件很大的话会占用过多内存所以设置存储室。
		factory.setRepository(file);
		//设置缓存的大小，当上传文件的容量超过缓存时，就放到暂时存储室。
		factory.setSizeThreshold(1024*1024);
		//生成上传工具类
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			//获取表单信息条目集合
			List<FileItem> list = (List<FileItem>)upload.parseRequest(request);
			for(FileItem item : list){
				String name = item.getFieldName();
				System.out.println(name);
				if (item.isFormField()) {    //普通表单信息
					String value = item.getString();
					System.out.println(value);
					request.setAttribute(name, value);
				}
				else {
					//filePath: 客户端文件绝对路径
					String filePath = item.getName();
					//获取文件名
					int index = filePath.lastIndexOf('\\');
					String fileName = filePath.substring(index+1);
					System.out.println(filePath);
					request.setAttribute(name, fileName);
					//写入文件
					item.write(new File(path, fileName));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
