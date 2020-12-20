package myservlet;

import java.io.BufferedReader;
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
import entity.Reply;

public class TeaReplyGradeServlet extends HttpServlet {
	SQLiteManager manager = new SQLiteManager();
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
		if (str != null && str.length() != 0) {
			System.out.println(str);
			JSONObject jsonObject = JSON.parseObject(str);
			PrintWriter out = response.getWriter();
			String command = jsonObject.getString("command");
			if (request.getMethod().equals("POST")) {
				if (command.equals("2")) {
					Object info = jsonObject.get("grade_reply");
					JSONObject object = JSON.parseObject(info.toString());
					String gro_id = object.getString("groups");
					String tea_id = object.getString("teacher");
					int reply_grade = object.getIntValue("reply_grade");
					res = manager.writeReplyGrade(tea_id, gro_id, reply_grade);
					if (res) {
						out.write("填写成功");
					} else {
						out.write("填写失败");
					}
				} else if (command.equals("1")) {
					String tea_id = jsonObject.getString("teacher");
					List<Reply> list = manager.getReplyByTeacher(tea_id);
					List<String> list2 = new ArrayList<>();
					for (int i = 0; i < list.size(); i++) {
						list2.add(list.get(i).getRep_group());
					}
					String res = JSON.toJSONString(list2);
					out.write(res);
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
