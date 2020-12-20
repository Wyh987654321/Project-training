package myservlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import controller.SQLiteManager;
import entity.Student;

public class AdmStudentServlet extends HttpServlet {
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("123Servlet建立成功");
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
		SQLiteManager manager = new SQLiteManager();
		if (request.getMethod().equals("POST")) {
			String str = getRequestPayload(request);
			JSONObject jsonObject = JSON.parseObject(str);
			Object stu = jsonObject.get("student");
			JSONObject object = JSON.parseObject(stu.toString());
			String stu_id = object.getString("stu_id");
			String stu_name = object.getString("stu_name");
			String stu_profession = object.getString("stu_profession");
			String stu_password = object.getString("stu_password");
			String stu_email = object.getString("stu_email");
			String stu_phone = object.getString("stu_phone");
			Boolean res = manager.updateStudent(stu_id, stu_name, stu_profession, stu_password, stu_phone, stu_email);
			if (res) {
				out.write("修改成功");
			} else {
				out.write("修改失败");
			}

		} else {
			List<Student> list = manager.selectAllStudent();
			String reStr = JSON.toJSONString(list);

			out.write(reStr);
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
