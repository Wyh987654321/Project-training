package myservlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import controller.SQLiteManager;

public class getStatusServlet extends HttpServlet {
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
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
		SQLiteManager manager = new SQLiteManager();
		boolean res = false;
		if (str != null && str.length() != 0) {
			JSONObject jsonObject = JSON.parseObject(str);
			String command = jsonObject.getString("command");
			if (command.equals("判断是否选过题")) {
				System.out.println("我进来了");
				String user = jsonObject.getString("user");
				res = manager.isSelectTopic(user);
				PrintWriter out = response.getWriter();
				if (res) {
					out.write("选过了");
				} else {
					out.write("0");
				}
			} else if (command.equals("判断是否提交过项目")) {
				System.out.println("我进来了1");
				String user = jsonObject.getString("user");
				res = manager.isSubmitTopic(user);
				PrintWriter out = response.getWriter();
				if (res) {
					out.write("提交过了");
				} else {
					out.write("0");
				}
			} else if (command.equals("判断是否选过组")) {
				System.out.println("我进来了2");
				String user = jsonObject.getString("user");
				res = manager.isSubmitGroup(user);
				PrintWriter out = response.getWriter();
				if (res) {
					out.write("选过了");
				} else {
					out.write("0");
				}
			}

			{

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
