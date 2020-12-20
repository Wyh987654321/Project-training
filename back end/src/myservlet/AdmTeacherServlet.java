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
import entity.Teacher;

public class AdmTeacherServlet extends HttpServlet {
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
		SQLiteManager manager = new SQLiteManager();
		PrintWriter out = response.getWriter();
		if (request.getMethod().equals("POST")) {
			System.out.println("这是POST方法");
			String str = getRequestPayload(request);
			JSONObject jsonObject = JSON.parseObject(str);
			Object tea = jsonObject.get("teacher");
			JSONObject object = JSON.parseObject(tea.toString());
			String tea_id = object.getString("tea_id");
			String tea_name = object.getString("tea_name");
			String tea_password = object.getString("tea_password");
			String tea_email = object.getString("tea_email");
			String tea_phone = object.getString("tea_phone");
			Boolean res = manager.updateTeacher(tea_id, tea_name, tea_password, tea_phone, tea_email);
			if (res) {
				out.write("修改成功");
			} else {
				out.write("修改失败");
			}
		} else {
			List<Teacher> list = manager.selectAllTeacher();
			for (int i = 0; i < list.size(); i++) {
				String tea_id = list.get(i).getTea_id();
				list.get(i).setTopic_num(manager.countTopic(tea_id));
				list.get(i).setGuideNum_need(manager.countGuideInfo(tea_id));
			}
			String res = JSON.toJSONString(list);
			out.write(res);

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
