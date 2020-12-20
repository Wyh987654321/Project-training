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
import entity.Group;

public class AdmGroupGradeServlet extends HttpServlet {
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
		SQLiteManager manager = new SQLiteManager();
		PrintWriter out = response.getWriter();
		boolean res = false;
		if (request.getMethod().equals("POST")) {
			String str = getRequestPayload(request);
			if (str != null && str.length() != 0) {
				JSONObject jsonObject = JSON.parseObject(str);
				String command = jsonObject.getString("command");
				if (command.equals("���ñ���")) {
					float rate = jsonObject.getFloatValue("rate");
					res = manager.setRate(rate);
					manager.getFinalGrade();
					manager.updateStuGrade();
					System.out.println("�ҽ�����");
					if (res) {
						out.write("�޸ĳɹ�");
					} else {
						out.write("�޸�ʧ��");
					}
				} else if (command.equals("��ȡ����")) {
					System.out.println("������ȡ������");
					float rate = manager.getRate();
					out.write(rate + "");
				}
			}
		} else {
			List<Group> list = manager.selectAllGroup2();
			for (int i = 0; i < list.size(); i++) {
				String stu_id = list.get(i).getGro_id();
				String tea_id = list.get(i).getGro_teacher();
				list.get(i).setGro_teacher(manager.getTeacherName(tea_id));
				list.get(i).setLeader(manager.getStudentName(stu_id));
			}
			String str = JSON.toJSONString(list);
			System.out.println(str);
			out.print(str);
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
