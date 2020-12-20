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

public class AdmTopicServlet extends HttpServlet {
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
			if (str != null && str.length() != 0) {
				JSONObject jsonObject = JSON.parseObject(str);
				String command = jsonObject.getString("command");
				// 移除小组
				if (command.equals("1")) {
					String id = jsonObject.getString("info").split(" ")[0];
					boolean res = manager.removeGroupTopic(id);
					if (res) {
						out.write("移除成功");
					} else {
						out.write("移除失败");
					}
				} else if (command.equals("2")) {
					String id = jsonObject.getString("id");
					boolean res = manager.removeTopic(id);
					if (res) {
						out.write("移除成功");
					} else {
						out.write("移除失败");
					}
				}
			}
		} else {

			List<Topic> list = manager.selectAllTopic();
			for (int i = 0; i < list.size(); i++) {
				String top_id = list.get(i).getTop_id();
				List<String> infoList = manager.getTopicInfo(top_id);
				list.get(i).setGroups(infoList);
				list.get(i).setGroup_num(infoList.size() + "");
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
