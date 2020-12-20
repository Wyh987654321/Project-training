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
import entity.Student;
import entity.Student2;

public class TeaSearchGroupServlet extends HttpServlet{
	SQLiteManager manager = new SQLiteManager();
	public void init(ServletConfig config)throws ServletException {
		super.init(config);
		System.out.println("Servlet�����ɹ�");
	}
	public synchronized void service(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", " Origin, X-Requested-With, Content-Type, Accept");
		response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
		response.setHeader("X-Powered-By", " 3.2.1");
		//�������ͣ������post�������ָ���������
		response.setHeader("Content-Type", "application/json;charset=utf-8");	
		response.setCharacterEncoding("utf-8");
		List<List<Student2>> resList = new ArrayList<>(); 
//		List<List<Student>> resList = new ArrayList<>(); 
		String str = getRequestPayload(request);
		System.out.println(str);
		/*
		 * 1.ͨ��teacher �ҵ�topid��list��1
		 * 2.ͨ��topid ��С��id (list)
		 * 3.����С��id ȥ��ѧ�� 1���������������ѧ�� ���� ������ѧ����1��С�飩��ѧ��������
		 * list<Object> Object=
		 */
		if (str!=null&&str.length()!=0) {
			JSONObject jsonObject = JSON.parseObject(str);
			String tea_id = jsonObject.getString("teacher");
			//����ʦ������С��
			List<String> groups = manager.getGroupByTeacher(tea_id);
			for (int i = 0; i < groups.size(); i++) {
				System.out.println(groups.get(i));
			}

			for (int i = 0; i < groups.size(); i++) {
				String group = groups.get(i);
				List<Student2>a_group = manager.selectStuByGroup(group);
				resList.add(a_group);
			}
			PrintWriter out=response.getWriter(); 
			String resStr = JSON.toJSONString(resList);
			out.print(resStr);	
//			for (int i = 0; i < groups.size(); i++) {
//			String group = groups.get(i);
//			List<Student>a_group = manager.selectStuByGroup(group);
//			resList.add(a_group);
//		}
			
		}	
	}
	private String getRequestPayload(HttpServletRequest req) {
	     StringBuilder sb = new StringBuilder();
	     try(BufferedReader reader = req.getReader();) {
	          char[]buff = new char[1024];
	          int len;
	          while((len = reader.read(buff)) != -1) {
	              sb.append(buff,0, len);
	          }
	     }catch (IOException e) {
	          e.printStackTrace();
	     }
	     return sb.toString();
	}

}
