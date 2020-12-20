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

public class TeaSubmitTopicServlet extends HttpServlet {
	SQLiteManager manager = new SQLiteManager();
	boolean res = false;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("Servlet�����ɹ�");
	}

	public synchronized void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", " Origin, X-Requested-With, Content-Type, Accept");
		response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
		response.setHeader("X-Powered-By", " 3.2.1");
		// �������ͣ������post�������ָ���������
		response.setHeader("Content-Type", "application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		String str = getRequestPayload(request);
		if (str != null && str.length() != 0) {
			JSONObject jsonObject = JSON.parseObject(str);
			System.out.println(str);
			String top_name = jsonObject.getString("top_name");
			String top_intro = jsonObject.getString("top_intro");
			String top_qq = jsonObject.getString("top_qq");
			String top_teacher = jsonObject.getString("top_teacher");
			res = manager.insertTopic(top_name, top_intro, top_teacher, top_qq);
			PrintWriter out = response.getWriter();
			if (res) {
				out.write("������Ŀ�ɹ�");
			} else {
				out.write("������Ŀʧ��");
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
