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

public class TeaGuideGradeServlet extends HttpServlet {
	SQLiteManager manager = new SQLiteManager();

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
		if (str != null && str.length() != 0) {
			System.out.println(str);
			JSONObject jsonObject = JSON.parseObject(str);
			PrintWriter out = response.getWriter();
			if (jsonObject.size() == 1) {
				String tea_id = jsonObject.getString("teacher");
				List<String> list = manager.getGroupByTeacher(tea_id);
				String res = JSON.toJSONString(list);
				out.write(res);
			} else {
				String tea_id = jsonObject.getString("teacher");
				String gro_id = jsonObject.getString("groups");
				int grade = jsonObject.getIntValue("guide_grade");
				boolean res = manager.writeGuideGrade(tea_id, gro_id, grade);
				if (res) {
					out.write("填写成功");
				} else {
					out.write("填写失败");
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
