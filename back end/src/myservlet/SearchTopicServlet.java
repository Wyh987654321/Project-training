package myservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import controller.SQLiteManager;
import entity.Topic;

public class SearchTopicServlet extends HttpServlet{
	boolean res=false;
	private SQLiteManager manager = new SQLiteManager();
	public void init(ServletConfig config)throws ServletException {
		super.init(config);
		System.out.println("Servlet建立成功");
	}
	@Override
	public synchronized void service(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", " Origin, X-Requested-With, Content-Type, Accept");
		response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
		response.setHeader("X-Powered-By", " 3.2.1");
		//内容类型：如果是post请求必须指定这个属性
		response.setHeader("Content-Type", "application/json;charset=utf-8");	
		response.setCharacterEncoding("utf-8");  
		List<Topic> list =manager.selectAllTopic();
		String str = JSON.toJSONString(list);
		PrintWriter out=response.getWriter(); 
		System.out.println(str);
		out.print(str);		
	}
}
