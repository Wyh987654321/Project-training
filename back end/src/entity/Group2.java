package entity;

import java.util.ArrayList;
import java.util.List;

public class Group2 {
	String gro_id;
	String gro_name;
	String gro_pro;
	int gro_num;
	String gro_topic;
	String gro_teacher;
	String gro_sub;
	List<String> members = new ArrayList<>();

	public Group2() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Group2(String gro_id, int gro_num, String gro_topic, String gro_teacher, String gro_sub) {
		super();
		this.gro_id = gro_id;
		this.gro_num = gro_num;
		this.gro_topic = gro_topic;
		this.gro_teacher = gro_teacher;
		this.gro_sub = gro_sub;
	}

	public String getGro_id() {
		return gro_id;
	}

	public void setGro_id(String gro_id) {
		this.gro_id = gro_id;
	}

	public String getGro_name() {
		return gro_name;
	}

	public void setGro_name(String gro_name) {
		this.gro_name = gro_name;
	}

	public String getGro_pro() {
		return gro_pro;
	}

	public void setGro_pro(String gro_pro) {
		this.gro_pro = gro_pro;
	}

	public int getGro_num() {
		return gro_num;
	}

	public void setGro_num(int gro_num) {
		this.gro_num = gro_num;
	}

	public String getGro_topic() {
		return gro_topic;
	}

	public void setGro_topic(String gro_topic) {
		this.gro_topic = gro_topic;
	}

	public String getGro_teacher() {
		return gro_teacher;
	}

	public void setGro_teacher(String gro_teacher) {
		this.gro_teacher = gro_teacher;
	}

	public String getGro_sub() {
		return gro_sub;
	}

	public void setGro_sub(String gro_sub) {
		this.gro_sub = gro_sub;
	}

	public List<String> getMembers() {
		return members;
	}

	public void setMembers(List<String> members) {
		this.members = members;
	}

	public void addMembers(String menbers) {
		this.members.add(menbers);
	}
}
