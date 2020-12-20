package entity;

import java.util.ArrayList;
import java.util.List;

public class Topic {
	private String top_id;
	private String top_name;
	private String top_intro;
	private String top_teacher;
	private String top_qq;
	private String group_num;
	private List<String> groups = new ArrayList<>();

	public String getGroup_num() {
		return group_num;
	}

	public void setGroup_num(String group_num) {
		this.group_num = group_num;
	}

	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

	public Topic(String top_id, String top_name, String top_intro, String top_teacher, String top_qq) {
		super();
		this.top_id = top_id;
		this.top_name = top_name;
		this.top_intro = top_intro;
		this.top_teacher = top_teacher;
		this.top_qq = top_qq;
	}

	public Topic() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Topic [top_id=" + top_id + ", top_name=" + top_name + ", top_intro=" + top_intro + ", top_teacher="
				+ top_teacher + ", top_qq=" + top_qq + "]";
	}

	public String getTop_id() {
		return top_id;
	}

	public void setTop_id(String top_id) {
		this.top_id = top_id;
	}

	public String getTop_name() {
		return top_name;
	}

	public void setTop_name(String top_name) {
		this.top_name = top_name;
	}

	public String getTop_intro() {
		return top_intro;
	}

	public void setTop_intro(String top_intro) {
		this.top_intro = top_intro;
	}

	public String getTop_teacher() {
		return top_teacher;
	}

	public void setTop_teacher(String top_teacher) {
		this.top_teacher = top_teacher;
	}

	public String getTop_qq() {
		return top_qq;
	}

	public void setTop_qq(String top_qq) {
		this.top_qq = top_qq;
	}

}
