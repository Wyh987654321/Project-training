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

public class ImportServlet extends HttpServlet {
	SQLiteManager manager = new SQLiteManager();
	Boolean res = false;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("导入Servlet建立成功");
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
		PrintWriter out = response.getWriter();
		String str = getRequestPayload(request);
		if (str != null && str.length() != 0) {
			JSONObject jsonObject = JSON.parseObject(str);
			Object stu = jsonObject.get("student");
			Object tea = jsonObject.get("teacher");
			Object reply = jsonObject.get("reply");
			if (stu != null) {
				JSONObject object = JSON.parseObject(stu.toString());
				String stu_id = object.getString("stu_id");
				String stu_name = object.getString("stu_name");
				String stu_profession = object.getString("stu_profession");
				String stu_password = object.getString("stu_password");
				String stu_email = object.getString("stu_email");
				String stu_phone = object.getString("stu_phone");

				res = manager.addStudent(stu_id, stu_name, stu_profession, stu_email, stu_phone, stu_password);
				if (res) {
					out.write("导入成功");
					return;
				} else {
					out.write("导入失败");
				}
			}
			if (tea != null) {
				JSONObject object = JSON.parseObject(tea.toString());
				String tea_id = object.getString("tea_id");
				String tea_name = object.getString("tea_name");
				String tea_password = object.getString("tea_password");
				String tea_email = object.getString("tea_email");
				String tea_phone = object.getString("tea_phone");

				res = manager.addTeacher(tea_id, tea_name, tea_email, tea_phone, tea_password);
				if (res) {
					out.write("导入成功");
					return;
				} else {
					out.write("导入失败");
				}
			}
			if (reply != null) {
				System.out.println("我进来了");
				JSONObject object = JSON.parseObject(reply.toString());
				String rep_time = object.getString("rep_time");
				String rep_address = object.getString("rep_address");
				String rep_teacher[] = object.getString("rep_teacher").split(" ");
				String rep_group = object.getString("rep_group");
				// 把老师名字换成id
				for (int i = 0; i < rep_teacher.length; i++) {
					rep_teacher[i] = manager.getTeacherID(rep_teacher[i]);
					System.out.println(rep_teacher[i]);
				}
				res = manager.manageReply(rep_time, rep_address, rep_teacher, rep_group);
				if (res) {
					out.write("导入成功");
					return;
				} else {
					out.write("导入失败");
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
