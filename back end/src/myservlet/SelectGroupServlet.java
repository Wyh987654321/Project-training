package myservlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.SQLiteManager;

public class SelectGroupServlet extends HttpServlet {
	private SQLiteManager manager = new SQLiteManager();
	boolean res = false;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("Servlet建立成功");
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
		String str = getRequestPayload(request);
		System.out.println(str);
		if (str != null && str.length() != 0) {
			String newStr = str.replaceAll("[[^0-9,]]", "");
			String data[] = newStr.split(",");
			boolean isStutent = manager.isStudent(data[0]);
			boolean isMaster = manager.isStudent(data[1]);
			if (isMaster && isStutent) {
				res = manager.insertGroup(data[1], data[0]);
			}
		}

		PrintWriter out = response.getWriter();
		if (res) {
			out.write("分组成功");
		} else {
			out.write("分组失败");
		}

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
