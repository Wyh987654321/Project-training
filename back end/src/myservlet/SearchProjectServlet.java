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

import controller.SQLiteManager;
import entity.Student;
import entity.Teacher;
import entity.Topic;

public class SearchProjectServlet extends HttpServlet {
	SQLiteManager manager = new SQLiteManager();

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("Servlet建立成功");
	}

	@Override
	public synchronized void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		List<Student> list = new ArrayList<>();
		List<Teacher> list2 = new ArrayList<>();
		String resStr = null;
		List<Object> reslist = new ArrayList<>();
		Topic topic = new Topic();
		Teacher teacher = new Teacher();
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", " Origin, X-Requested-With, Content-Type, Accept");
		response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
		response.setHeader("X-Powered-By", " 3.2.1");
		// 内容类型：如果是post请求必须指定这个属性
		response.setHeader("Content-Type", "application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		String str = getRequestPayload(request);
		if (str != null && str.length() != 0) {
			String user = str.replaceAll("[[^0-9,]]", "");
			System.out.println(user);
			list = manager.getTemateByStu_id(user);
			teacher = manager.getTeacherByStu_id(user);
			System.out.println(teacher);
			if (teacher != null) {
				list2.add(teacher);
				topic = manager.getTopicByStu_id(user);
				topic.setTop_teacher(manager.getTeacherName(topic.getTop_teacher()));
				reslist.add(list);
				reslist.add(list2);
				reslist.add(topic);
			} else {
				reslist.add(list);
				reslist.add(list2.add(new Teacher()));
				reslist.add(null);
			}

			resStr = JSON.toJSONString(reslist);
			PrintWriter out = response.getWriter();
			out.write(resStr);
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
