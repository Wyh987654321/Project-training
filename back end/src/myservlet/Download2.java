package myservlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import controller.SQLiteManager;

public class Download2 extends HttpServlet {
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("下载Servlet建立成功");
	}

	public synchronized void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", " Origin, X-Requested-With, Content-Type, Accept");
		response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
		response.setHeader("X-Powered-By", " 3.2.1");
		// 内容类型：如果是post请求必须指定这个属性
		response.setHeader("Content-Type", "application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/x-msdownload");

		PrintWriter out = response.getWriter();
		String str = getRequestPayload(request);
		SQLiteManager manager = new SQLiteManager();
		if (str != null && str.length() != 0) {
			System.out.println(str);
			JSONObject jsonObject = JSON.parseObject(str);
			String user = jsonObject.getString("user");
			System.out.println(user);
			// 老师页面
			if (!user.equals("student")) {
				String folder = "upload";
				String name = manager.getTeacherName(user);
				System.out.println(name);
				try {
//					String filepath = request.getSession().getServletContext().getRealPath("") + "\\upload";
					String filepath = "/usr/local/tomcat/apache-tomcat-7.0.107/webapps/Test1/upload/";
					System.out.println(filepath);
//					List<String> list = readfile(filepath + File.separator + folder, name);
					List<String> list = readfile(filepath, name);
					String res = JSON.toJSONString(list);
					out.write(res);
					out.close();
				} catch (Exception e) {

					// TODO: handle exception
					System.out.println(e.getClass().getName() + ": " + e.getMessage());
				}
			} else {
				System.out.println("我是学生");
				String folder = "download";
				String name = "";
				System.out.println(name);
				try {
//					String filepath = request.getSession().getServletContext().getRealPath("") + "\\download";
					String filepath = "/usr/local/tomcat/apache-tomcat-7.0.107/webapps/Test1/download/";
					System.out.println(filepath);
					System.out.println(filepath + File.separator + folder);
//					List<String> list = readfile(filepath + File.separator + folder, name);
					List<String> list = readfile(filepath, name);

					String res = JSON.toJSONString(list);
					System.out.println(res);
					out.write(res);
					out.close();
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e.getClass().getName() + ": " + e.getMessage());
				}
			}
		}

	}

	public List<String> readfile(String filepath, String name) throws FileNotFoundException, IOException {
		List<String> list = new ArrayList<>();
		String encoding = System.getProperty("file.encoding");
		try {
			File file = new File(filepath);
			if (!file.isDirectory()) {
				System.out.println("文件");
				System.out.println("path=" + file.getPath());
				System.out.println("absolutepath=" + file.getAbsolutePath());
				System.out.println("name=" + file.getName());

			} else if (file.isDirectory()) {
				System.out.println("文件夹");
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + "/" + filelist[i]);
//					File readfile = new File(filepath + "\\" + filelist[i]);
					if (!readfile.isDirectory()) {
						if (readfile.getName().contains(name)) {
							// list.add(readfile.getName());
							String filename = readfile.getName();
							list.add(filename);
							System.out.println(readfile.getName());
						}
					} else if (readfile.isDirectory()) {
						readfile(filepath + "/" + filelist[i], "");
						readfile(filepath + "\\" + filelist[i], "");
					}

				}

			}

		} catch (FileNotFoundException e) {
			System.out.println("readfile()   Exception:" + e.getMessage());
		}
		return list;
	}

	private String getRequestPayload(HttpServletRequest req) {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = req.getReader();) {
			char[] buff = new char[1024];
			int len;
			while ((len = reader.read(buff)) != -1) {
				sb.append(buff, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
