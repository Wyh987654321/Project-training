package myservlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import controller.SQLiteManager;

public class LoginServlet extends HttpServlet {
	private SQLiteManager manager = new SQLiteManager();
	private boolean res = false;
	private String id = null;
	private String password = null;
	int flag = -1; // 登陆者的身份 0:管理员 1:教师 2:学生

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("LoginServlet建立成功");
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
			if (!str.contains("command")) {
				if (str.contains("admin")) {
					password = str.replaceAll("[[^0-9]]", "");
					System.out.println(password);
					res = manager.adminLogin("admin", password);
					flag = 0;
				} else {
					String newStr = str.replaceAll("[[^0-9,]]", "");
					String data[] = newStr.split(",");
					if (data[0].substring(0, 1).equals("4")) {
						res = manager.studentLogin(data[0], data[1]);
						flag = 2;
					} else {
						res = manager.teacherLogin(data[0], data[1]);
						flag = 1;
					}
				}
				PrintWriter out = response.getWriter();
				if (res && flag == 0) {
					out.write("管理员登录成功");
				} else if (res && flag == 1) {
					out.write("教师登录成功");
				} else if (res && flag == 2) {
					out.write("学生登录成功");
				}
				if (!res) {
					out.write("登录失败");
				}
			} else {
				JSONObject jsonObject = JSON.parseObject(str);
				String id = jsonObject.getString("id");
				boolean res = manager.isLeader(id);
				PrintWriter out = response.getWriter();
				if (res) {
					out.write("是组长");
				} else {
					out.write("不是");
				}
			}

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
