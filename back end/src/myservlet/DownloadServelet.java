package myservlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

public class DownloadServelet extends HttpServlet {
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
		// response.setContentType("APPLICATION/OCTET-STREAM");
		PrintWriter out = response.getWriter();

		String filepath = request.getSession().getServletContext().getRealPath("");
//		String filepath = "/usr/local/tomcat/Test1_file";
		System.out.println(filepath);
		if (request.getMethod().equals("POST")) {
			String folder = "download";
			try {
				List<String> list = readfile(filepath + File.separator + folder);
				String res = JSON.toJSONString(list);
				out.write(res);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getClass().getName() + ": " + e.getMessage());
			}
		} else {
			request.setCharacterEncoding("utf-8");
			String filename = request.getParameter("file");

			if (filename != null && filename.length() != 0) {
				filename = new String(filename.getBytes("ISO8859-1"), "UTF-8");
				System.out.println(filename);
			}
			String folder = "download";
			System.out.println(filepath + File.separator + filename);
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"" + URLEncoder.encode(filename, "UTF-8") + "\"");
			try {
				FileInputStream fileInputStream = new FileInputStream(
						filepath + File.separator + folder + File.separator + filename);
				System.out.println(filepath + File.separator + folder + File.separator + filename);
				InputStreamReader fileInputStream2 = new InputStreamReader(fileInputStream, "utf-8");

				int i = 0;

				while ((i = fileInputStream2.read()) != -1) {
					out.write(i);
				}
				fileInputStream.close();
				fileInputStream2.close();
				out.close();
			} catch (Exception e) {
				System.out.println(e.getClass().getName() + ": " + e.getMessage());
			}

		}

	}

	public List<String> readfile(String filepath) throws FileNotFoundException, IOException {
		List<String> list = new ArrayList<>();
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
					File readfile = new File(filepath + "\\" + filelist[i]);
					if (!readfile.isDirectory()) {
						list.add(readfile.getName());
						System.out.println(readfile.getName());

					} else if (readfile.isDirectory()) {
						readfile(filepath + "\\" + filelist[i]);
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
