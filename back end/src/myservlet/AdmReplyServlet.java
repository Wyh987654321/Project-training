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
import entity.Reply2;

public class AdmReplyServlet extends HttpServlet {
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
			String str = getRequestPayload(request);
			if (str != null && str.length() != 0) {
				JSONObject jsonObject = JSON.parseObject(str);
				String command = jsonObject.getString("command");
				boolean res = false;
				// 修改答辩
				if (command.equals("1")) {
					Object info = jsonObject.get("info");
					JSONObject object = JSON.parseObject(info.toString());

					String rep_teacher[] = object.getString("rep_teacher").split(" ");

					System.out.println(rep_teacher.length);
					String rep_time = object.getString("rep_time").replaceAll("[^0-9:-]", " ").substring(0, 19);
					System.out.println(rep_time);
					String rep_group = object.getString("rep_group");
					String rep_address = object.getString("rep_address");
					// 把老师名字换成id
					if (rep_teacher[0].length() != 0) {
						for (int i = 0; i < rep_teacher.length; i++) {
							rep_teacher[i] = manager.getTeacherID(rep_teacher[i]);
							System.out.println(rep_teacher[i]);
						}
					}
					res = manager.DeleteReply(rep_group);
					if (res && rep_teacher[0].length() != 0) {
						res = manager.manageReply(rep_time, rep_address, rep_teacher, rep_group);
					} else {
						System.out.println("删除小组失败");
					}
					if (res) {
						out.write("修改成功");
					} else {
						out.write("修改失败");
					}
				}
				// 新增答辩
				else if (command.equals("2")) {
					Object info = jsonObject.get("object");
					JSONObject object = JSON.parseObject(info.toString());
					String rep_teacher[] = object.getString("rep_teacher").split(" ");
					String rep_time = object.getString("rep_time").replaceAll("[^0-9:-]", " ").substring(0, 19);
					System.out.println(object.getString("rep_time"));
					System.out.println(rep_time);
					String rep_group = object.getString("rep_group");
					String rep_address = object.getString("rep_address");
					for (int i = 0; i < rep_teacher.length; i++) {
						rep_teacher[i] = manager.getTeacherID(rep_teacher[i]);
						System.out.println(rep_teacher[i]);
					}
					res = manager.manageReply(rep_time, rep_address, rep_teacher, rep_group);
					if (res) {
						out.write("新建成功");
					} else {
						out.write("新建失败");
					}
				}
			}
		} else {
			List<Reply2> list = manager.getAllReply2();
			List<Reply2> list2 = new ArrayList<>(); // 用于去重后的小组
			List<String> strlList = new ArrayList<>();
			// 清洗列表多余数据
			for (int i = 0; i < list.size(); i++) {
				if (!strlList.contains(list.get(i).getRep_group())) {
					strlList.add(list.get(i).getRep_group());
					list2.add(list.get(i));
				} else {
				}
			}
			// 根据小组号获取所有的答辩老师id
			for (int i = 0; i < list2.size(); i++) {
				String gro_id = list2.get(i).getRep_group();
				list2.get(i).setRep_teacher(manager.getReplyTeabyGro_id(gro_id));
			}
			// 把教师id转换为老师姓名
			for (int i = 0; i < list2.size(); i++) {
				String teachers[] = list2.get(i).getRep_teacher().split(" ");
				StringBuffer ts = new StringBuffer("");
				for (int j = 0; j < teachers.length; j++) {
					ts.append(manager.getTeacherName(teachers[j]) + " ");
				}
				list2.get(i).setRep_teacher(ts.toString());
			}
			String res = JSON.toJSONString(list2);
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
