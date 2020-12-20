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
				// 获取没有分组的学生
				if (command.equals("1")) {
					List<String> list = manager.getUngroupedStudent();
					String resStr = JSON.toJSONString(list);
					out.print(resStr);
				}
				// 新建小组
				else if (command.equals("2")) {
					String leader = jsonObject.getString("leader");
					String members_list = jsonObject.getString("members");
					String members[] = members_list.replaceAll("[[^0-9,]]", "").split(",");
					// 记录插入失败的成员
					List<String> failure = new ArrayList<>();
					// 先插入组长
					Boolean res = manager.insertGroup(leader, leader);
					if (res) {
						// 插入组员
						for (int i = 0; i < members.length; i++) {
							res = manager.insertGroup(members[i], leader);
							// 如果插入失败就记录
							if (!res) {
								failure.add(members[i]);
							}
						}
					} else {
						// 表示组长插入失败，整个小组取消插入
						out.write("0");
						;
					}
					if (failure.size() != 0) {
						out.print(failure);
					} else {
						out.print("新建成功");
					}

				}
				// 移除小组成员
				else if (command.equals("3")) {
					String stu_id = jsonObject.getString("stu_info");
					Boolean res = manager.removeStu(stu_id);
					if (res) {
						out.write("移除成功");
					} else {
						out.write("移除失败");
					}
				}
				// 添加小组成员
				else if (command.equals("4")) {
					System.out.println("我进到4了");
					String leader = jsonObject.getString("leader");
					String member = jsonObject.getString("member").split(" ")[0];
					Boolean res = manager.insertGroup(member, leader);
					if (res) {
						out.write("添加成功");
					} else {
						out.write("添加失败");
					}
				}
				// 删除小组
				else if (command.equals("5")) {
					System.out.println("我进到5了");
					String leader = jsonObject.getString("leader");
					String members = jsonObject.getString("members");
					String member_id[] = members.replaceAll("[[^0-9,]]", "").split(",");
					// 记录删除失败的成员
					List<String> failure = new ArrayList<>();
					// 删除小组
					res = manager.deleteGroup(leader);

					if (res) {
						// 删除组长
						res = manager.removeStu(leader);
						if (!res) {
							failure.add(leader);
						}
						// 删除组员
						for (int i = 0; i < member_id.length; i++) {
							res = manager.removeStu(member_id[i]);
							// 如果删除失败就记录
							if (!res) {
								failure.add(member_id[i]);
							}
						}
					} else {
						// 表示小组删除失败，整个小组取消删除
						out.write("0");

					}
					if (failure.size() != 0) {
						out.print(failure);
					} else {
						out.print("删除成功");
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
