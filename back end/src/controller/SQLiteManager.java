package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Grade;
import entity.Group;
import entity.Group2;
import entity.Reply;
import entity.Reply2;
import entity.Student;
import entity.Student2;
import entity.Teacher;
import entity.Topic;

public class SQLiteManager {
	private Connection con = null;
	private Statement stmt = null;
	private String sql = null;
	private ResultSet rs = null;

	/*
	 * 连接数据库 执行查询操作
	 */
	private ResultSet selectkDB(String sql) {

		try {
			Class.forName("org.sqlite.JDBC");
			// con = DriverManager.getConnection("jdbc:sqlite:train.db"); // 连接数据库train.db
//			con = DriverManager.getConnection("jdbc:sqlite:E:\\eclipse\\train.db"); // 连接数据库train.db
			con = DriverManager.getConnection("jdbc:sqlite:train.db"); // 连接数据库train.db
//			System.out.println("Opened database successfully"); 
			stmt = con.createStatement(); // 创建连接对象，是Java的一个操作数据库的重要接口
			rs = stmt.executeQuery(sql); // 返回结果集
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return rs;
	}

	/*
	 * 连接数据库 执行插入更新操作
	 */
	private boolean insertkDB(String sql) {
		boolean res = true;
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:train.db"); // 连接数据库train.db
//			con = DriverManager.getConnection("jdbc:sqlite:train.db"); // 连接数据库train.db
//			System.out.println("Opened database successfully");  
			stmt = con.createStatement(); // 创建连接对象，是Java的一个操作数据库的重要接口
			stmt.executeUpdate(sql); // 返回结果集
		} catch (Exception e) {
			res = false;
			if (e.getMessage().equals("column stu_id is not unique")) {
				res = true;
				System.out.println("我进来了");
			}

		}
		return res;
	}

	/*
	 * 管理员登陆
	 */
	public boolean adminLogin(String id, String password) {
		boolean ressult = false;
		try {
			sql = "select * from administrator where adm_id = '" + id + "' and adm_password = '" + password + "'"; // Sql语句
			rs = selectkDB(sql);
			if (rs.next()) {
				ressult = true;
			}
			// 关闭连接
			rs.close();
			stmt.close();
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			System.out.println("查询结束");
		}
		return ressult;
	}

	/*
	 * 查询所有学生
	 */
	public List<Student> selectAllStudent() {
		List<Student> list = new ArrayList<>();
		try {
			sql = "select * from student"; // Sql语句

			rs = selectkDB(sql);
			// 如果有数据，打印输出
			while (rs.next()) {
				String stu_id = rs.getString("stu_id");
				String stu_name = rs.getString("stu_name");
				String stu_profession = rs.getString("stu_profession");
				String stu_phone = rs.getString("stu_phone");
				String stu_email = rs.getString("stu_email");
				String stu_password = rs.getString("stu_password");
				String stu_group = rs.getString("stu_group");
				int stu_grade1 = rs.getInt("stu_grade1");
				int stu_grade2 = rs.getInt("stu_grade2");
				Student student = new Student(stu_id, stu_name, stu_email, stu_phone, stu_profession, stu_password,
						stu_group, stu_grade1, stu_grade2);
				list.add(student);
			}
			// 关闭连接
			rs.close();
			stmt.close();
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}

	/*
	 * 通过学号查询学生
	 */
	public void selectStuByNum(String number) {
		try {
			sql = "select * from student where stu_id = '" + number + "'"; // Sql语句
			rs = selectkDB(sql);
			// 如果存在该学生
			if (rs.getString("stu_id") != null) {
				// 打印输出信息
				while (rs.next()) {
					String stu_id = rs.getString("stu_id");
					String stu_name = rs.getString("stu_name");
					String stu_profession = rs.getString("stu_profession");
					String stu_phone = rs.getString("stu_phone");
					String stu_email = rs.getString("stu_email");
					String stu_password = rs.getString("stu_password");
					int stu_grade1 = rs.getInt("stu_grade1");
					int stu_grade2 = rs.getInt("stu_grade2");
					System.out.println(
							"stu_id = " + stu_id + " stu_name = " + stu_name + " stu_profession = " + stu_profession
									+ " stu_phone = " + stu_phone + " stu_email = " + stu_email + " stu_password = "
									+ stu_password + " stu_grade1 = " + stu_grade1 + " stu_grade2 = " + stu_grade2);
					System.out.println();
				}
			}
			// 如果不存在
			else {
				System.out.println("查无此人");
			}

			// 关闭连接
			rs.close();
			stmt.close();
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			System.out.println("查询结束");
		}
	}

	/*
	 * 通过小组号查找学生
	 */
	public List<Student2> selectStuByGroup(String stu_group) {
		List<Student2> list = new ArrayList<>();
		try {
			// 获取该小组的课题名称
			String topic_name = "";
			sql = "select top_name from topic where top_id =(select gro_topic from train_group where gro_id = '"
					+ stu_group + "')";
			rs = selectkDB(sql); // ִ执行sql语句，返回结果集
			System.out.println(sql);
			while (rs.next()) {
				topic_name = rs.getString("top_name");
			}
			// 关闭连接
			rs.close();
			stmt.close();
			con.close();
			sql = "select * from student where stu_group ='" + stu_group + "'";
			rs = selectkDB(sql); // ִ执行sql语句，返回结果集
			// 如果有该小组
			if (rs.getString("stu_group") != null) {
				// 打印输出信息
				while (rs.next()) {
					String stu_id = rs.getString("stu_id");
					String stu_name = rs.getString("stu_name");
					String stu_profession = rs.getString("stu_profession");
					String stu_phone = rs.getString("stu_phone");
					String stu_email = rs.getString("stu_email");
					String stu_group1 = rs.getString("stu_group");
					String stu_password = rs.getString("stu_password");
					String topic = "";
					int stu_grade1 = rs.getInt("stu_grade1");
					int stu_grade2 = rs.getInt("stu_grade2");
					if (stu_id.equals(stu_group)) {
						topic = topic_name;
					}
					list.add(new Student2(stu_id, stu_name, stu_email, stu_phone, stu_profession, stu_password,
							stu_group1, stu_grade1, stu_grade2, topic));
				}

			}
			// 如果没有
			else {
				System.out.println("查无该组");
			}

			// 把组长放到列表第一行
			for (int j = 0; j < list.size(); j++) {
				String stu_id = list.get(j).getStu_id();
				// 找到组长
				if (stu_id.equals(stu_group)) {
					if (j == 0) {
						break;
					} else {
						Student2 tmp = list.get(0);
						list.set(0, list.get(j));
						list.set(j, tmp);
					}
				}
			}

			// 关闭连接
			rs.close();
			stmt.close();
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}

	/*
	 * 通过专业查找学生 X
	 */
	public void selectStuByPro(String stu_pro) {
		try {
			sql = "select * from student where stu_profession = '" + stu_pro + "'"; // Sql语句
			rs = selectkDB(sql); // ִ执行Sql语句返回结果集
			// 如果有数据
			if (rs.getString("stu_group") != null) {
				// 打印输出
				while (rs.next()) {
					String stu_id = rs.getString("stu_id");
					String stu_name = rs.getString("stu_name");
					String stu_profession = rs.getString("stu_profession");
					String stu_phone = rs.getString("stu_phone");
					String stu_email = rs.getString("stu_email");
					String stu_password = rs.getString("stu_password");
					int stu_grade1 = rs.getInt("stu_grade1");
					int stu_grade2 = rs.getInt("stu_grade2");
					System.out.println(
							"stu_id = " + stu_id + " stu_name = " + stu_name + " stu_profession = " + stu_profession
									+ " stu_phone = " + stu_phone + " stu_email = " + stu_email + " stu_password = "
									+ stu_password + " stu_grade1 = " + stu_grade1 + " stu_grade2 = " + stu_grade2);
					System.out.println();
				}

			}
			// 如果没有
			else {
				System.out.println("查无此专业");
			}
			// 关闭连接
			rs.close();
			stmt.close();
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			System.out.println("查询结束");
		}
	}

	/*
	 * 学生登陆
	 */
	public boolean studentLogin(String id, String password) {
		boolean ressult = false;
		try {
			sql = "select * from student where stu_id = '" + id + "' and stu_password = '" + password + "'"; // Sql语句
			rs = selectkDB(sql); // ִ执行sql语句，返回结果集
			if (rs.next()) {
				ressult = true;
			}
			// 关闭连接
			rs.close();
			stmt.close();
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			System.out.println("查询结束");
		}
		return ressult;
	}

	/*
	 * 查看所有题目
	 */
	public List<Topic> selectAllTopic() {
		List<Topic> list = new ArrayList<>();
		try {
			sql = "select * from topic"; // Sql语句
			rs = selectkDB(sql);
			// 如果有数据，打印输出
			while (rs.next()) {
				String top_id = rs.getString("top_id");
				String top_name = rs.getString("top_name");
				String top_introduction = rs.getString("top_introduction");
				String top_teacher = rs.getString("top_teacher");
				String top_qq = rs.getString("top_qq");
				list.add(new Topic(top_id, top_name, top_introduction, top_teacher, top_qq));
			}
			// 关闭连接
			rs.close();
			stmt.close();
			con.close();
			for (int i = 0; i < list.size(); i++) {
				String tea_id = list.get(i).getTop_teacher();
				sql = "select tea_name from teacher where tea_id = '" + tea_id + "'";
				rs = selectkDB(sql);
				while (rs.next()) {
					list.get(i).setTop_teacher(rs.getString("tea_name"));
				}
				// 关闭连接
				rs.close();
				stmt.close();
				con.close();
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}

	/*
	 * 通过老师编号查询题目
	 */
	public void selectTopicByTeacher(String tea_id) {
		try {
			sql = "select * from topic where top_teacher= '" + tea_id + "'"; // Sql语句
			rs = selectkDB(sql); // ִ执行sql语句，返回结果集
			// 如果有数据
			if (rs.getString("top_teacher") != null) {
				// 打印输出
				while (rs.next()) {
					String top_name = rs.getString("top_name");
					String top_introduction = rs.getString("top_introduction");
					String top_teacher = rs.getString("top_teacher");
					String top_qq = rs.getString("top_qq");

					System.out.println("top_name = " + top_name + " top_introduction = " + top_introduction
							+ " top_teacher = " + top_teacher + " top_qq = " + top_qq);
					System.out.println();
				}
			} else {
				System.out.println("请输入正确的老师ID");
			}
			// 关闭连接
			rs.close();
			stmt.close();
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			System.out.println("查询结束");
		}
	}

	/*
	 * 查询所有老师
	 */
	public List<Teacher> selectAllTeacher() {
		List<Teacher> list = new ArrayList<>();
		try {
			sql = "select * from teacher"; // Sql语句
			rs = selectkDB(sql); // ִ执行sql语句，返回结果集
			// 如果有数据，打印输出
			while (rs.next()) {
				String tea_id = rs.getString("tea_id");

				String tea_name = rs.getString("tea_name");
				String tea_phone = rs.getString("tea_phone");
				String tea_email = rs.getString("tea_email");
				String tea_password = rs.getString("tea_password");
				Map<String, String> map = new HashMap<>();

				list.add(new Teacher(tea_id, tea_name, tea_password, tea_email, tea_phone));
			}
			// 关闭连接
			rs.close();
			stmt.close();
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}

	/*
	 * 老师登录 X
	 */
	public boolean teacherLogin(String id, String password) {
		boolean ressult = false;
		try {
			sql = "select * from teacher where tea_id = '" + id + "' and tea_password = '" + password + "'"; // Sql语句
			rs = selectkDB(sql); // ִ执行sql语句，返回结果集
			if (rs.next()) {
				ressult = true;
			}
			// 关闭连接
			rs.close();
			stmt.close();
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			System.out.println("查询结束");
		}
		return ressult;
	}

	/*
	 * 查询所有分组
	 */
	public List<Group> selectAllGroup() {
		List<Group> list = new ArrayList<>();
		try {
			sql = "select * from train_group"; // Sql语句
			rs = selectkDB(sql); // ִ执行sql语句，返回结果集
			// 如果有数据，打印输出
			while (rs.next()) {
				String gro_id = rs.getString("gro_id");
				int gro_number = rs.getInt("gro_number");
				String gro_teacher = rs.getString("gro_teacher");
				String gro_topic = rs.getString("gro_topic");
				int gro_grade1 = rs.getInt("gro_grade1");
				int gro_grade2 = rs.getInt("gro_grade2");
				int gro_grade3 = rs.getInt("gro_grade3");
				list.add(new Group(gro_id, gro_number, gro_teacher, gro_topic, gro_grade1, gro_grade2, gro_grade3));
			}
			// 关闭连接
			rs.close();
			stmt.close();
			con.close();
			// 添加学生姓名
			for (int i = 0; i < list.size(); i++) {
				String gro_id = list.get(i).getGro_id();
				StringBuffer names = new StringBuffer("");
				sql = "select stu_name from student where stu_group = '" + gro_id + "'";
				rs = selectkDB(sql);
				while (rs.next()) {
					names.append(rs.getString("stu_name") + " ");
				}
				// 关闭连接
				rs.close();
				stmt.close();
				con.close();
				list.get(i).setGro_members(names.toString());
			}
			// 替换选题编号为题目
			for (int i = 0; i < list.size(); i++) {
				String gro_topic = list.get(i).getGro_topic();
				sql = "select top_name from topic where top_id = '" + gro_topic + "'";
				rs = selectkDB(sql);
				while (rs.next()) {
					list.get(i).setGro_topic(rs.getString("top_name"));
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}

	/*
	 * 查询所有分组 为管理员页面小组成绩提供
	 */
	public List<Group> selectAllGroup2() {
		List<Group> list = new ArrayList<>();
		try {
			sql = "select * from train_group"; // Sql语句
			rs = selectkDB(sql); // ִ执行sql语句，返回结果集
			// 如果有数据，打印输出
			while (rs.next()) {
				String gro_id = rs.getString("gro_id");
				int gro_number = rs.getInt("gro_number");
				String gro_teacher = rs.getString("gro_teacher");
				String gro_topic = rs.getString("gro_topic");
				int gro_grade1 = rs.getInt("gro_grade1");
				int gro_grade2 = rs.getInt("gro_grade2");
				int gro_grade3 = rs.getInt("gro_grade3");
				list.add(new Group(gro_id, gro_number, gro_teacher, gro_topic, gro_grade1, gro_grade2, gro_grade3));
			}
			// 关闭连接
			rs.close();
			stmt.close();
			con.close();
			// 替换选题编号为题目
			for (int i = 0; i < list.size(); i++) {
				String gro_topic = list.get(i).getGro_topic();
				sql = "select top_name from topic where top_id = '" + gro_topic + "'";
				rs = selectkDB(sql);
				while (rs.next()) {
					list.get(i).setGro_topic(rs.getString("top_name"));
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}

	/*
	 * 查询所有的答辩信息
	 */

	public List<Reply> getAllReply() {
		List<Reply> list = new ArrayList<>();
		try {
			sql = "select * from reply"; // Sql语句
			rs = selectkDB(sql); // ִ执行sql语句，返回结果集
			// 如果有数据，打印输出
			while (rs.next()) {
				String rep_id = rs.getString("rep_id");
				String rep_time = rs.getString("rep_time");
				String rep_address = rs.getString("rep_address");
				String rep_teacher = rs.getString("rep_teacher");
				String rep_group = rs.getString("rep_group");
				int rep_grade = rs.getInt("rep_grade");
				list.add(new Reply(rep_id, rep_time, rep_address, rep_teacher, rep_group, rep_grade));
			}
			// 关闭连接
			rs.close();
			stmt.close();
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}

	/*
	 * 查询所有的答辩信息 不带成绩
	 */
	public List<Reply2> getAllReply2() {
		List<Reply2> list = new ArrayList<>();
		try {
			sql = "select * from reply"; // Sql语句
			rs = selectkDB(sql); // ִ执行sql语句，返回结果集
			// 如果有数据，打印输出
			while (rs.next()) {
				String rep_id = rs.getString("rep_id");
				String rep_time = rs.getString("rep_time");
				String rep_address = rs.getString("rep_address");
				String rep_teacher = rs.getString("rep_teacher");
				String rep_group = rs.getString("rep_group");
				list.add(new Reply2(rep_id, rep_time, rep_address, rep_teacher, rep_group));
			}
			// 关闭连接
			rs.close();
			stmt.close();
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}

	/*
	 * 根据小组号查询答辩消息
	 */
	public List<Reply> getReplybyGro_id(String stu_id) {
		List<Reply> list = new ArrayList<>();
		try {
			sql = "select * from reply where rep_group =(select stu_group from student where stu_id ='" + stu_id + "')"; // Sql语句
			rs = selectkDB(sql); // ִ执行sql语句，返回结果集
			// 如果有数据，打印输出
			while (rs.next()) {
				String rep_id = rs.getString("rep_id");
				String rep_time = rs.getString("rep_time");
				String rep_address = rs.getString("rep_address");
				String rep_teacher = rs.getString("rep_teacher");
				String rep_group = rs.getString("rep_group");
				int rep_grade = rs.getInt("rep_grade");
				list.add(new Reply(rep_id, rep_time, rep_address, rep_teacher, rep_group, rep_grade));
				break;
			}
			// 关闭连接
			rs.close();
			stmt.close();
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}

	/*
	 * 根据小组号查询答辩教师
	 */
	public String getReplyTeabyGro_id(String gro_id) {
		StringBuffer teachers = new StringBuffer("");
		try {
			sql = "select rep_teacher from reply where rep_group ='" + gro_id + "'"; // Sql语句
			rs = selectkDB(sql); // ִ执行sql语句，返回结果集
			// 如果有数据，打印输出
			while (rs.next()) {
				String rep_teacher = rs.getString("rep_teacher");
				teachers.append(rep_teacher + " ");
			}
			// 关闭连接
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return teachers.toString();
	}

	/*
	 * 根据小组号查询分组
	 */
	public void selectGroupByNumber(String number) {
		try {
			sql = "select * from train_group where gro_id = '" + number + "'"; // Sql语句
			rs = selectkDB(sql); // ִ执行sql语句，返回结果集
			// 如果有数据，打印输出
			while (rs.next()) {
				String gro_id = rs.getString("gro_id");
				int gro_number = rs.getInt("gro_number");
				String gro_teacher = rs.getString("gro_teacher");
				String gro_topic = rs.getString("gro_topic");
				int gro_grade1 = rs.getInt("gro_grade1");
				int gro_grade2 = rs.getInt("gro_grade2");
				int gro_grade3 = rs.getInt("gro_grade3");
				System.out.println("gro_id = " + gro_id + " gro_number = " + gro_number + " gro_teacher = "
						+ gro_teacher + " gro_topic = " + gro_topic + " gro_grade1 = " + gro_grade1 + " gro_grade2 = "
						+ gro_grade2 + " gro_grade3 = " + gro_grade3);
				System.out.println();
			}
			// 关闭连接
			rs.close();
			stmt.close();
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			System.out.println("查询结束");
		}
	}

	/*
	 * 添加题目
	 */
	public boolean insertTopic(String name, String intro, String teacher, String qq) {
		boolean res = false;
		try {
			sql = "insert into topic(top_id,top_name,top_introduction,top_teacher,top_qq)" + "values(null,'" + name
					+ "','" + intro + "','" + teacher + "','" + qq + "'" + ")";

			res = insertkDB(sql);
			// 关闭连接
			stmt.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return res;
	}

	/*
	 * 添加组队信息 参数1为学号 参数2为小组号
	 */
	public boolean insertGroup(String stu_id, String gro_id) {
		boolean res = true; // 返回结果

		try {
			// 添加小组号
			sql = "update student set stu_group = '" + gro_id + "' where stu_id = '" + stu_id + "'";
			System.out.println(sql);
			insertkDB(sql);
			// 关闭连接
			stmt.close();
			con.close();
			// 增加小组人数
			sql = "select gro_id,gro_number from train_group where gro_id = '" + gro_id + "'"; // Sql语句
			System.out.println(sql);
			rs = selectkDB(sql); // ִ执行sql语句，返回结果集
			if (rs.isBeforeFirst()) { // 如果存在该小组 获取小组并人数加1
				int num = rs.getInt("gro_number");
				num++;
				sql = "update train_group set gro_number =" + num + " where gro_id = '" + gro_id + "'";
				System.out.println(2);
			} else { // 不存在该小组，创建小组 人数为1
				sql = "insert into train_group(gro_id,gro_number,gro_teacher,gro_topic)" + "values('" + gro_id
						+ "',1,'暂无','暂无')";
				System.out.println(1);
			}
			// 关闭连接
			rs.close();
			stmt.close();
			con.close();
			insertkDB(sql);
			// 关闭连接

			stmt.close();
			con.close();
		} catch (Exception e) {
			res = false;
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	/*
	 * 管理分组情况
	 */
	public void manageGroup(String stu_id, String gro_id) {
		String pre_group = "";
		try {
			// 检查该同学是否已有分组
			sql = "select stu_group from student where stu_id ='" + stu_id + "'";
			rs = selectkDB(sql);
			while (rs.next()) {
				pre_group = rs.getString("stu_group");
			}
			// 关闭连接
			rs.close();
			stmt.close();
			con.close();
			// 说明该同学之前没分组
			if (pre_group == null || pre_group.length() == 0) {
				insertGroup(stu_id, gro_id);
			} else {
				// 原来的小组人数减一
				sql = "update train_group set gro_number =gro_number-1 where gro_id= '" + pre_group + "'";
				insertkDB(sql);
				stmt.close();
				con.close();
				// 加入新的小组
				insertGroup(stu_id, gro_id);
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	/*
	 * 学生选题 参数分别是 小组号（只有组长可以选题）题目编号
	 */
	public boolean selectGroup_topic(String gro_id, String top_name) {
//		String teacher="";
		boolean res = false;
		try {
			sql = "select gro_id from train_group where gro_id = '" + gro_id + "'"; // Sql语句
			rs = selectkDB(sql); // ִ执行sql语句，返回结果集
			if (rs.getString("gro_id") != null) {

//				sql ="update train_group set gro_topic ='"+top_id+"', gro_teacher = '"+teacher+"' where gro_id = '"+gro_id+"'";	
				sql = "update train_group set gro_topic = (select top_id from topic where top_name ='" + top_name
						+ "'), gro_teacher = (select top_teacher from topic where top_name = '" + top_name
						+ "') where gro_id = '" + gro_id + "'";

			} else {
				// 关闭连接
				rs.close();
				stmt.close();
				con.close();
				System.err.println("你不是组长，不能填写小组选题");
				return false;
			}
			// 关闭连接
			rs.close();
			stmt.close();
			con.close();
			System.out.println(sql);
			res = insertkDB(sql);
			// 关闭连接
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	/*
	 * 查看指导老师联系方式
	 */
	public String getTeacherPhone(String str_group) {
		String res = null;
		try {
			sql = "select teacher.tea_phone from train_group inner join teacher where teacher.tea_id =train_group.gro_teacher";
			rs = selectkDB(sql); // ִ执行sql语句，返回结果集
			if (rs.getString("tea_phone") != null) {
				res = rs.getString("tea_phone");
			} else {
				System.out.println("查询失败");
			}
			// 关闭连接
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	/*
	 * 查看同组同学的联系方式
	 */
	public void getTeammatePhone(String gro_id) {
		try {
			sql = "select stu_name,stu_profession,stu_phone,stu_email from student where stu_group = '" + gro_id + "'";
			rs = selectkDB(sql);
			while (rs.next()) {
				String stu_name = rs.getString("stu_name");
				String stu_profession = rs.getString("stu_profession");
				String stu_phone = rs.getString("stu_phone");
				String stu_email = rs.getString("stu_email");
				System.out.println("stu_name = " + stu_name + " stu_profession = " + stu_profession + " stu_phone = "
						+ stu_phone + " stu_email = " + stu_email + "\n");
			}
			// 关闭连接
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	/*
	 * 填写指导小组成绩
	 */
	public boolean writeGuideGrade(String tea_id, String gro_id, int grade1) {
		boolean res = false;
		try {
			sql = "update train_group set gro_grade1 =" + grade1 + " where gro_teacher ='" + tea_id + "' and gro_id ='"
					+ gro_id + "'";
			res = insertkDB(sql);
			// 关闭连接
			stmt.close();
			con.close();
			getFinalGrade(gro_id);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	/*
	 * 填写老师答辩成绩
	 */
	public boolean writeReplyGrade(String tea_id, String gro_id, int grade) {
		boolean res = true;
		int all = 0;
		float avg = 0, count = 0;
		try {
			// 填写单个老师的答辩成绩
			sql = "update reply set rep_grade = " + grade + " where rep_teacher ='" + tea_id + "' and rep_group ='"
					+ gro_id + "'";
			res = insertkDB(sql);
			// 关闭连接
			stmt.close();
			con.close();
			// 获取所有老师的答辩成绩 求平均 填写小组答辩成绩
			sql = "select rep_grade from reply where rep_group ='" + gro_id + "'";
			rs = selectkDB(sql);
			while (rs.next()) {
				all += rs.getInt("rep_grade");
				count++;
			}
			avg = all / count; // 得到平均成绩
			// 关闭连接
			rs.close();
			stmt.close();
			con.close();
			// 将平均成绩四舍五入取整后更新成小组表中的答辩成绩
			sql = "update train_group set gro_grade2 = " + Math.round(avg) + " where gro_id ='" + gro_id + "'";
			insertkDB(sql);
			// 关闭连接
			stmt.close();
			con.close();
			getFinalGrade(gro_id);

		} catch (Exception e) {
			res = false;
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	/*
	 * 计算最终成绩
	 * 
	 */
	private void getFinalGrade(String gro_id) {
		int gro_grade1 = 0, gro_grade2 = 0;
		// 获取该小组的答辩成绩和指导成绩
		try {
			sql = "select gro_grade1,gro_grade2 from train_group where gro_id ='" + gro_id + "'";
			rs = selectkDB(sql);
			while (rs.next()) {
				gro_grade1 = rs.getInt("gro_grade1");
				gro_grade2 = rs.getInt("gro_grade2");
			}
			// 关闭连接
			rs.close();
			stmt.close();
			con.close();
			// 获取成绩比例，rate为指导成绩占的比例
			float rate = getRate();
			sql = "update train_group set gro_grade3 =" + Math.round(gro_grade1 * rate + gro_grade2 * (1 - rate))
					+ " where gro_id ='" + gro_id + "'";
			insertkDB(sql);
			// 关闭连接
			stmt.close();
			con.close();
			// 将小组最终成绩写入小组每个组员中
			sql = "update student set stu_grade1 =" + Math.round(gro_grade1 * 0.4 + gro_grade2 * 0.6)
					+ " where stu_group= '" + gro_id + "'";
			insertkDB(sql);
			// 关闭连接
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	/*
	 * 计算所有小组的最终成绩
	 */

	public void getFinalGrade() {
		try {
			System.out.println("重新计算小组成绩");
			// 获取成绩比例，rate为指导成绩占的比例
			float rate = getRate();
			System.out.println("rate= " + rate);
//			Math.round(grade3)
			sql = "update train_group set gro_grade3 = round(gro_grade1*" + rate + " + gro_grade2*" + (1 - rate) + ")";
			System.out.println(sql);
			insertkDB(sql);
			// 关闭连接
			stmt.close();
			con.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	/*
	 * 更新学生表成绩
	 */
	public boolean updateStuGrade() {
		boolean res = false;
		try {
			sql = "update student set stu_grade1 = (select gro_grade3 from train_group where train_group.gro_id = student.stu_group)";
			System.out.println(sql);
			insertkDB(sql);
			// 关闭连接
			stmt.close();
			con.close();
			System.out.println("我运行了");
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	/*
	 * 管理题目
	 */
	public boolean manageTopic(String top_id, String top_name, String top_intro, String top_teacher, String top_qq) {
		// 用于保存该题目的所有信息
		String pre_name = "";
		String pre_intro = "";
		String pre_teacher = "";
		String pre_qq = "";
		boolean res = false;
		try {
			sql = "select * from topic where top_id = '" + top_id + "'";
			rs = selectkDB(sql);
			while (rs.next()) {
				pre_name = rs.getString("top_name");
				pre_intro = rs.getString("top_introduction");
				pre_teacher = rs.getString("top_teacher");
				pre_qq = rs.getString("top_qq");
			}
			// 关闭连接
			rs.close();
			stmt.close();
			con.close();
			// 判断更新的信息是否为空或长度为0，即不想更新这部分信息，就用原来的信息替换，确保信息不会变化
			if (top_name == null || top_name.length() == 0) {
				top_name = pre_name;
			}
			if (top_intro == null || top_intro.length() == 0) {
				top_intro = pre_intro;
			}
			if (top_teacher == null || top_teacher.length() == 0) {
				top_teacher = pre_teacher;
			}
			if (top_qq == null || top_qq.length() == 0) {
				top_qq = pre_qq;
			}
			// 开始更新
			sql = "update topic set top_name = '" + top_name + "' ,top_introduction = '" + top_intro
					+ "' ,top_teacher = '" + top_teacher + "' ,top_qq = '" + top_qq + "' where top_id = '" + top_id
					+ "'";
			res = insertkDB(sql);
			stmt.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return res;
	}

	/*
	 * 管理选题情况
	 */
	public void manageTopicChoose(String gro_id, String top_id) {
		try {
			sql = "update train_group set gro_topic = '" + top_id
					+ "' ,gro_teacher = (select top_teacher from topic where top_id ='" + top_id + "')"
					+ " where gro_id = '" + gro_id + "'";
			insertkDB(sql);
			// 关闭连接
			con.close();
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

	}

	/*
	 * 管理老师密码
	 */
	public void manageTeacherPass(String tea_id, String tea_pass) {
		try {
			sql = "update teacher set tea_password = '" + tea_pass + "' where tea_id = '" + tea_id + "'";
			System.out.println(sql);
			insertkDB(sql);
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	/*
	 * 答辩安排
	 */
	public boolean manageReply(String rep_time, String rep_address, String teachers[], String rep_group) {
		boolean res = true;
//		if (teachers.length == 1) {
//			return res;
//		}
		try {
			for (int i = 0; i < teachers.length; i++) {
				sql = "insert into reply (rep_id,rep_time,rep_address,rep_teacher,rep_group) " + "values(null,'"
						+ rep_time + "','" + rep_address + "','" + teachers[i] + "','" + rep_group + "')";
				insertkDB(sql);
				// 关闭连接
				con.close();
				stmt.close();
			}
		} catch (Exception e) {
			res = false;
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	/*
	 * 删除某个小组的答辩安排
	 */

	public boolean DeleteReply(String rep_group) {
		boolean res = false;
		try {
			sql = "delete from reply where rep_group = '" + rep_group + "'";
			res = insertkDB(sql);
			// 关闭连接
			con.close();
			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	/*
	 * 检查在学生表中是否存在
	 */
	public boolean isStudent(String stu_id) {
		boolean res = false;
		try {
			sql = "select * from student where stu_id = '" + stu_id + "'";
			rs = selectkDB(sql);
			if (rs.next()) {
				res = true;
			}
			// 关闭连接
			rs.close();
			con.close();
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	/*
	 * 通过学号查找队友
	 */
	public List<Student> getTemateByStu_id(String stu_id) {
		List<Student> list = new ArrayList<>();
		try {
			sql = "select * from student where stu_group = (select stu_group from student where stu_id ='" + stu_id
					+ "')";

			rs = selectkDB(sql);
			while (rs.next()) {
				String stu_id1 = rs.getString("stu_id");
				String stu_name = rs.getString("stu_name");
				String stu_profession = rs.getString("stu_profession");
				String stu_phone = rs.getString("stu_phone");
				String stu_email = rs.getString("stu_email");
				String stu_password = rs.getString("stu_password");
				String stu_group = rs.getString("stu_group");
				int stu_grade1 = rs.getInt("stu_grade1");
				int stu_grade2 = rs.getInt("stu_grade2");
				list.add(new Student(stu_id1, stu_name, stu_email, stu_phone, stu_profession, stu_password, stu_group,
						stu_grade1, stu_grade1));
			}
			// 关闭连接
			rs.close();
			con.close();
			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}

	/*
	 * 通过学号查找指导老师
	 */
	public Teacher getTeacherByStu_id(String stu_id) {
		Teacher teacher = null;
		try {
			sql = "select * from teacher where tea_id = (select gro_teacher from train_group where gro_id = (select stu_group from student where stu_id = '"
					+ stu_id + "'))";

			rs = selectkDB(sql);
			while (rs.next()) {
				String tea_id = rs.getString("tea_id");
				String tea_name = rs.getString("tea_name");
				String tea_phone = rs.getString("tea_phone");
				String tea_email = rs.getString("tea_email");
				String tea_password = rs.getString("tea_password");
				teacher = new Teacher(tea_id, tea_name, tea_password, tea_email, tea_phone);
			}
			// 关闭连接
			rs.close();
			con.close();
			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return teacher;
	}

	/*
	 * 通过学号查找小组题目
	 */
	public Topic getTopicByStu_id(String stu_id) {
		Topic topic = null;
		try {
			sql = "select * from topic where top_id = (select gro_topic from train_group where gro_id = (select stu_group from student where stu_id = '"
					+ stu_id + "'))";

			rs = selectkDB(sql);
			while (rs.next()) {
				String top_id = rs.getString("top_id");
				String top_name = rs.getString("top_name");
				String top_introduction = rs.getString("top_introduction");
				String top_teacher = rs.getString("top_teacher");
				String top_qq = rs.getString("top_qq");
				topic = new Topic(top_id, top_name, top_introduction, top_teacher, top_qq);
			}
			// 关闭连接
			rs.close();
			con.close();
			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return topic;
	}

	/*
	 * 通过学号查找成绩
	 */
	public Grade getGrade(String stu_id) {
		Grade grade = null;
		try {
			sql = "select * from student where stu_id = '" + stu_id + "'";
			rs = selectkDB(sql);
			while (rs.next()) {
				String stu_id1 = rs.getString("stu_id");
				String stu_name = rs.getString("stu_name");
				int stu_grade1 = rs.getInt("stu_grade1");
				int stu_grade2 = rs.getInt("stu_grade2");
				grade = new Grade(stu_id1, stu_name, stu_grade1, stu_grade2);
			}
			// 关闭连接
			rs.close();
			con.close();
			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return grade;

	}

	/*
	 * 查询老师的小组
	 */
	public List<String> getGroupByTeacher(String tea_id) {
		List<String> list = new ArrayList<>();
		try {
			sql = "select gro_id from train_group where gro_teacher = '" + tea_id + "' order by gro_topic ";

			rs = selectkDB(sql);
			while (rs.next()) {
				list.add(rs.getString("gro_id"));
			}
			// 关闭连接
			rs.close();
			con.close();
			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}

	/*
	 * 返回某个老师的答辩安排
	 */
	public List<Reply> getReplyByTeacher(String rep_teacher) {
		List<Reply> list = new ArrayList<>();
		try {
			sql = "select * from reply where rep_teacher = '" + rep_teacher + "'";
			rs = selectkDB(sql);
			while (rs.next()) {
				String rep_id = rs.getString("rep_id");
				String rep_time = rs.getString("rep_time");
				String rep_address = rs.getString("rep_address");
				String rep_teacher1 = rs.getString("rep_teacher");
				String rep_group = rs.getString("rep_group");
				int rep_grade = rs.getInt("rep_grade");
				list.add(new Reply(rep_id, rep_time, rep_address, rep_teacher1, rep_group, rep_grade));
			}
			// 关闭连接
			rs.close();
			con.close();
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}

	/*
	 * 获取所有的题目名称
	 */
	public List<String> getTopicName() {
		List<String> list = new ArrayList<>();
		try {
			sql = "select top_name from topic ";
			rs = selectkDB(sql);
			while (rs.next()) {
				list.add(rs.getString("top_name"));
			}
			// 关闭连接
			rs.close();
			con.close();
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}

	/*
	 * 添加学生
	 */
	public boolean addStudent(String stu_id, String stu_name, String stu_profession, String stu_email, String stu_phone,
			String stu_password) {
		Boolean res = false;
		try {
			sql = "insert into student values('" + stu_id + "' ,'" + stu_name + "' ,'" + stu_profession + "' ,'"
					+ stu_phone + "' ,'" + stu_email + "' ,'" + stu_password + "','未分组',0,0)";
			System.out.println(sql);
			res = insertkDB(sql);
			// 关闭连接
			con.close();
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception

			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	/*
	 * 添加老师
	 */
	public boolean addTeacher(String tea_id, String tea_name, String tea_email, String tea_phone, String tea_password) {
		Boolean res = false;
		try {
			sql = "insert into teacher values('" + tea_id + "' ,'" + tea_name + "' ,'" + tea_password + "' ,'"
					+ tea_phone + "' ,'" + tea_email + "' )";
			System.out.println(sql);
			res = insertkDB(sql);
			// 关闭连接
			con.close();
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception

			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	/*
	 * 修改学生信息
	 */
	public boolean updateStudent(String stu_id, String stu_name, String stu_profession, String stu_password,
			String stu_phone, String stu_email) {
		Boolean res = false;
		try {
			sql = "update student set stu_name = '" + stu_name + "', stu_profession ='" + stu_profession
					+ "', stu_password = '" + stu_password + "', stu_phone = '" + stu_phone + "', stu_email = '"
					+ stu_email + "' where stu_id = '" + stu_id + "'";
			System.out.println(sql);
			res = insertkDB(sql);
			// 关闭连接
			con.close();
			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	/*
	 * 修改老师信息
	 */
	public boolean updateTeacher(String tea_id, String tea_name, String tea_password, String tea_phone,
			String tea_email) {
		Boolean res = false;
		try {
			sql = "update teacher set tea_name = '" + tea_name + "', tea_password = '" + tea_password
					+ "', tea_phone = '" + tea_phone + "', tea_email = '" + tea_email + "' where tea_id = '" + tea_id
					+ "'";
			System.out.println(sql);
			res = insertkDB(sql);
			// 关闭连接
			con.close();
			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	/*
	 * 为小组页面获取信息
	 */
	public List<Group2> getGroup2All() {
		List<Group2> list = new ArrayList<>();
		try {
			sql = "select * from train_group";
			rs = selectkDB(sql);
			while (rs.next()) {
				String gro_id = rs.getString("gro_id");
				int gro_number = rs.getInt("gro_number");
				String gro_teacher = rs.getString("gro_teacher");
				String gro_topic = rs.getString("gro_topic");
				String gro_sub = rs.getString("gro_sub");
				Group2 group = new Group2(gro_id, gro_number, gro_topic, gro_teacher, gro_sub);
				list.add(group);
			}
			// 关闭连接
			rs.close();
			con.close();
			stmt.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}

	/*
	 * 为小组页面获取信息 第二步
	 */
	public List<Group2> getGroup2All2(List<Group2> list1) {
		List<Group2> list = list1;
		try {
			for (int i = 0; i < list.size(); i++) {
				String gro_id = list.get(i).getGro_id();
				sql = "select stu_id,stu_name,stu_profession from student where stu_group = '" + gro_id + "'";
				rs = selectkDB(sql);
				while (rs.next()) {
					String stu_id = rs.getString("stu_id");
					if (stu_id.equals(gro_id)) {
						list.get(i).setGro_name(rs.getString("stu_name"));
						list.get(i).setGro_pro(rs.getString("stu_profession"));
					} else {
						String stu_name = rs.getString("stu_name");
						String stu_profession = rs.getString("stu_profession");
						String stu_info = stu_id + " " + stu_name + " " + stu_profession;
						list.get(i).addMembers(stu_info);
					}
				}
				// 关闭连接
				rs.close();
				con.close();
				stmt.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}

	/*
	 * 为小组页面获取信息 第三步
	 */
	public List<Group2> getGroup2All3(List<Group2> list1) {
		List<Group2> list = list1;
		try {
			for (int i = 0; i < list.size(); i++) {
				String gro_id = list.get(i).getGro_id();
				sql = "select top_name from topic where top_id =(select gro_topic from train_group where gro_id = '"
						+ gro_id + "')";
				rs = selectkDB(sql);
				while (rs.next()) {
					String top_name = rs.getString("top_name");
					list.get(i).setGro_topic(top_name);
				}
				// 关闭连接
				rs.close();
				con.close();
				stmt.close();

				sql = "select tea_name from teacher where tea_id =(select gro_teacher from train_group where gro_id = '"
						+ gro_id + "')";
				rs = selectkDB(sql);
				while (rs.next()) {
					String tea_name = rs.getString("tea_name");
					list.get(i).setGro_teacher(tea_name);
				}
				// 关闭连接
				rs.close();
				con.close();
				stmt.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}

	/*
	 * 获取未分组学生的简单信息 学号 姓名 专业
	 */
	public List<String> getUngroupedStudent() {
		List<String> list = new ArrayList<>();
		try {
			sql = "select stu_id,stu_name,stu_profession from student where stu_group = '未分组' ";
			rs = selectkDB(sql);
			while (rs.next()) {
				String stu_id = rs.getString("stu_id");
				String stu_name = rs.getString("stu_name");
				String stu_profession = rs.getString("stu_profession");
				String stu_info = stu_id + " " + stu_name + " " + stu_profession;
				list.add(stu_info);
			}
			// 关闭连接
			rs.close();
			con.close();
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}

	/*
	 * 移除学生
	 */
	public Boolean removeStu(String stu_id) {
		Boolean res = false;
		try {
			sql = "update train_group set gro_number = gro_number-1 where gro_id =(select stu_group from student where stu_id = '"
					+ stu_id + "')";
			res = insertkDB(sql);
			// 关闭连接
			con.close();
			stmt.close();

			sql = "update student set stu_group ='未分组' where stu_id = '" + stu_id + "'";
			res = insertkDB(sql);
			if (!res) {
				return res;
			}
			// 关闭连接
			con.close();
			stmt.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	/*
	 * 删除小组
	 */
	public Boolean deleteGroup(String gro_id) {
		Boolean res = false;
		try {
			sql = "delete from train_group where gro_id = '" + gro_id + "'";
			res = insertkDB(sql);
			// 关闭连接
			con.close();
			stmt.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;

	}

	/*
	 * 统计老师发布题目的个数
	 */
	public String countTopic(String tea_id) {
		String count = "0";
		try {
			sql = "select count(*) as num from topic where top_teacher = '" + tea_id + "'";

			rs = selectkDB(sql);
			while (rs.next()) {
				count = rs.getString("num");
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return count;
	}

	/*
	 * 获取某老师的指导成绩填写情况
	 */
	public String countGuideInfo(String tea_id) {
		int first = 0; // 老师填写指导成绩小组个数
		int second = 0; // 老师总小组个数
		try {
			sql = "select gro_grade1 from train_group where gro_teacher = '" + tea_id + "'";
			rs = selectkDB(sql);
			while (rs.next()) {
				second++;
				// 说明老师填写了成绩
				if (rs.getInt("gro_grade1") != 0) {
					first++;
				}
			}
			// 关闭连接
			rs.close();
			con.close();
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return first + "/" + second;
	}

	/*
	 * 根据课题号 获取选取该题的小组个数和细节
	 */
	public List<String> getTopicInfo(String top_id) {
		List<String> list = new ArrayList<>();
		try {
			sql = "select gro_id from train_group where gro_topic = '" + top_id + "'";
			List<String> groups = new ArrayList<>();
			rs = selectkDB(sql);
			while (rs.next()) {
				groups.add(rs.getString("gro_id"));
			}
			// 关闭连接
			rs.close();
			con.close();
			stmt.close();
			for (int j = 0; j < groups.size(); j++) {
				sql = "select stu_id,stu_name,stu_profession from student where stu_id = '" + groups.get(j) + "'";
				rs = selectkDB(sql);
				while (rs.next()) {
					String info = rs.getString("stu_id") + " " + rs.getString("stu_name") + " "
							+ rs.getString("stu_profession");
					list.add(info);
				}
				// 关闭连接
				rs.close();
				con.close();
				stmt.close();
			}

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}

	/*
	 * 移除小组选题
	 */
	public Boolean removeGroupTopic(String gro_id) {
		Boolean res = false;
		try {
			sql = "update train_group set gro_topic = '暂无', gro_teacher ='暂无' where gro_id = '" + gro_id + "'";
			res = insertkDB(sql);
			con.close();
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	/*
	 * 移除课题
	 */
	public boolean removeTopic(String id) {
		Boolean res = false;
		try {
			sql = "update train_group set gro_topic = '暂无' ,gro_teacher = '暂无' where gro_topic = '" + id + "'";
			res = insertkDB(sql);
			con.close();
			stmt.close();
			if (res) {
				sql = "delete from topic where top_id ='" + id + "'";
				res = insertkDB(sql);
				con.close();
				stmt.close();
			} else {
				return res;
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	/*
	 * 根据教师号找老师名字
	 */
	public String getTeacherName(String tea_id) {
		String name = "";
		try {
			sql = "select tea_name from teacher where tea_id ='" + tea_id + "'";
			rs = selectkDB(sql);
			while (rs.next()) {
				name = rs.getString("tea_name");
			}
			rs.close();
			con.close();
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return name;
	}

	/*
	 * 根据教师名字找教师号
	 */

	public String getTeacherID(String tea_name) {
		String id = "";
		try {
			sql = "select tea_id from teacher where tea_name ='" + tea_name + "'";
			System.out.println(sql);
			rs = selectkDB(sql);
			while (rs.next()) {
				id = rs.getString("tea_id");
			}
			rs.close();
			con.close();
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return id;
	}

	/*
	 * 判断是否是组长
	 */
	public boolean isLeader(String stu_id) {
		boolean res = false;
		try {
			sql = "select stu_group from student where stu_id = '" + stu_id + "'";
			rs = selectkDB(sql);
			while (rs.next()) {
				if (rs.getString("stu_group").equals(stu_id)) {
					res = true;
				}
			}
			rs.close();
			con.close();
			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	/*
	 * 查询学生名字
	 */
	public String getStudentName(String stu_id) {
		String name = "";
		try {
			sql = "select stu_name from student where stu_id = '" + stu_id + "'";
			rs = selectkDB(sql);
			while (rs.next()) {
				name = rs.getString("stu_name");
			}
			rs.close();
			con.close();
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return name;
	}

	/*
	 * 修改小组的提交状态
	 */

	public boolean changeStatus(String gro_id) {
		boolean res = false;
		try {
			sql = "update train_group set gro_sub = '已提交' where gro_id ='" + gro_id + "'";
			res = insertkDB(sql);

			con.close();
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	/*
	 * 判断是否选过题
	 */
	public boolean isSelectTopic(String stu_id) {
		boolean res = true;
		try {
			sql = "select gro_topic from train_group where gro_id = (select stu_group from student where stu_id ='"
					+ stu_id + "')";
			System.out.println(sql);
			rs = selectkDB(sql);
			while (rs.next()) {
				System.out.println(rs.getString("gro_topic"));
				System.out.println(rs.getString("gro_topic").length());
				if (rs.getString("gro_topic").equals("暂无")) {
					res = false;
				}
			}
			rs.close();
			con.close();
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		System.out.println("res=" + res);
		return res;
	}

	/*
	 * 判断是否提交过项目文件
	 */
	public boolean isSubmitTopic(String gro_id) {
		boolean res = true;
		try {
			sql = "select gro_sub from train_group where gro_id = '" + gro_id + "'";

			System.out.println(sql);
			rs = selectkDB(sql);
			while (rs.next()) {
				if (rs.getString("gro_sub").equals("未提交")) {
					res = false;
				}
			}
			rs.close();
			con.close();
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return res;
	}

	/*
	 * 判断是否提交过项目文件
	 */
	public boolean isSubmitGroup(String stu_id) {
		boolean res = true;
		try {
			sql = "select stu_group from student where stu_id = '" + stu_id + "'";

			System.out.println(sql);
			rs = selectkDB(sql);
			while (rs.next()) {
				if (rs.getString("stu_group").equals("未分组")) {
					res = false;
				}
			}
			rs.close();
			con.close();
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return res;
	}

	/*
	 * 设置成绩比例
	 */
	public boolean setRate(float rate) {
		boolean res = false;
		try {
			sql = "update administrator set guide_rate = " + rate;
			res = insertkDB(sql);
			con.close();
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	/*
	 * 获取比例
	 */
	public float getRate() {
		float rate = 0;
		try {
			sql = "select guide_rate from administrator";
			rs = selectkDB(sql);
			if (rs.isBeforeFirst()) { // 有数据
				rate = rs.getFloat("guide_rate");
			}
			rs.close();
			con.close();
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return rate;
	}
}
