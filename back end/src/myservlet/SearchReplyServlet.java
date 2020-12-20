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
import entity.Reply;

public class SearchReplyServlet extends HttpServlet{
	SQLiteManager manager = new SQLiteManager();
	public void init(ServletConfig config)throws ServletException {
		super.init(config);
		System.out.println("Servlet�����ɹ�");
	}
	@Override
	public synchronized void service(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", " Origin, X-Requested-With, Content-Type, Accept");
		response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
		response.setHeader("X-Powered-By", " 3.2.1");
		//�������ͣ������post�������ָ���������
		response.setHeader("Content-Type", "application/json;charset=utf-8");	
		response.setCharacterEncoding("utf-8");  
		String str = getRequestPayload(request);
		if (str!=null&&str.length()!=0) {
			
			JSONObject jsonObject = JSON.parseObject(str);
			String stu_id =jsonObject.getString("user");
			System.out.println(stu_id);
			List<Reply> list =manager.getReplybyGro_id(stu_id);
			String resStr = JSON.toJSONString(list);
			PrintWriter out=response.getWriter(); 
			System.out.println(resStr);
			out.print(resStr);		
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
