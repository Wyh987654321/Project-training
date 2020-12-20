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
import entity.Topic;

public class SelectTopicServlet extends HttpServlet {
	boolean res = false;
	private SQLiteManager manager = new SQLiteManager();

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("Servlet建立成功");
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", " Origin, X-Requested-With, Content-Type, Accept");
		response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
		response.setHeader("X-Powered-By", " 3.2.1");
		// 内容类型：如果是post请求必须指定这个属性
		response.setHeader("Content-Type", "application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		if (request.getMethod().equals("POST")) {
			String str = getRequestPayload(request);
			if (str != null && str.length() != 0) {
				if (!str.contains("id123456")) {
					JSONObject jsonObject = JSON.parseObject(str);
					String stu_id = jsonObject.getString("leader_id");
					String topic = jsonObject.getString("topics");
					String info[] = topic.split(" ");
					Boolean isStudent = manager.isStudent(stu_id);
					if (isStudent) {
						res = manager.selectGroup_topic(stu_id, info[0]);
					}
					PrintWriter out = response.getWriter();
					if (res) {
						out.write("选题成功");
					} else {
						out.write("选题失败");
					}
				} else {
					JSONObject jsonObject = JSON.parseObject(str);
					String id = jsonObject.getString("id123456");
					boolean res = manager.changeStatus(id);
					PrintWriter out = response.getWriter();
					if (res) {
						out.write("修改成功");
					} else {
						out.write("修改失败");
					}
				}

			}
		} else {
			List<Topic> list = manager.selectAllTopic();
			String str = JSON.toJSONString(list);
			PrintWriter out = response.getWriter();

			out.write(str);
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
