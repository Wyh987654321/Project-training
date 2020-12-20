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
import entity.Group2;

public class AdmGroupServlet extends HttpServlet {
	SQLiteManager manager = new SQLiteManager();
	Boolean res = false;

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
		if (request.getMethod().equals("POST")) {
			System.out.println("����POST����");
			String str = getRequestPayload(request);
			if (str != null && str.length() != 0) {
				JSONObject jsonObject = JSON.parseObject(str);
				String command = jsonObject.getString("command");
				// ��ȡû�з����ѧ��
				if (command.equals("1")) {
					List<String> list = manager.getUngroupedStudent();
					String resStr = JSON.toJSONString(list);
					out.print(resStr);
				}
				// �½�С��
				else if (command.equals("2")) {
					String leader = jsonObject.getString("leader");
					String members_list = jsonObject.getString("members");
					String members[] = members_list.replaceAll("[[^0-9,]]", "").split(",");
					// ��¼����ʧ�ܵĳ�Ա
					List<String> failure = new ArrayList<>();
					// �Ȳ����鳤
					Boolean res = manager.insertGroup(leader, leader);
					if (res) {
						// ������Ա
						for (int i = 0; i < members.length; i++) {
							res = manager.insertGroup(members[i], leader);
							// �������ʧ�ܾͼ�¼
							if (!res) {
								failure.add(members[i]);
							}
						}
					} else {
						// ��ʾ�鳤����ʧ�ܣ�����С��ȡ������
						out.write("0");
						;
					}
					if (failure.size() != 0) {
						out.print(failure);
					} else {
						out.print("�½��ɹ�");
					}

				}
				// �Ƴ�С���Ա
				else if (command.equals("3")) {
					String stu_id = jsonObject.getString("stu_info");
					Boolean res = manager.removeStu(stu_id);
					if (res) {
						out.write("�Ƴ��ɹ�");
					} else {
						out.write("�Ƴ�ʧ��");
					}
				}
				// ���С���Ա
				else if (command.equals("4")) {
					System.out.println("�ҽ���4��");
					String leader = jsonObject.getString("leader");
					String member = jsonObject.getString("member").split(" ")[0];
					Boolean res = manager.insertGroup(member, leader);
					if (res) {
						out.write("��ӳɹ�");
					} else {
						out.write("���ʧ��");
					}
				}
				// ɾ��С��
				else if (command.equals("5")) {
					System.out.println("�ҽ���5��");
					String leader = jsonObject.getString("leader");
					String members = jsonObject.getString("members");
					String member_id[] = members.replaceAll("[[^0-9,]]", "").split(",");
					// ��¼ɾ��ʧ�ܵĳ�Ա
					List<String> failure = new ArrayList<>();
					// ɾ��С��
					res = manager.deleteGroup(leader);

					if (res) {
						// ɾ���鳤
						res = manager.removeStu(leader);
						if (!res) {
							failure.add(leader);
						}
						// ɾ����Ա
						for (int i = 0; i < member_id.length; i++) {
							res = manager.removeStu(member_id[i]);
							// ���ɾ��ʧ�ܾͼ�¼
							if (!res) {
								failure.add(member_id[i]);
							}
						}
					} else {
						// ��ʾС��ɾ��ʧ�ܣ�����С��ȡ��ɾ��
						out.write("0");

					}
					if (failure.size() != 0) {
						out.print(failure);
					} else {
						out.print("ɾ���ɹ�");
					}
				}
			}
		} else {
			List<Group2> list = manager.getGroup2All();
			list = manager.getGroup2All2(list);
			list = manager.getGroup2All3(list);
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
