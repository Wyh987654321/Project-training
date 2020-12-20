package entity;

public class Grade {
	String stu_id;
	String stu_name;
	int stu_grade1;
	int stu_grade2;
	@Override
	public String toString() {
		return "Grade [stu_id=" + stu_id + ", stu_name=" + stu_name + ", stu_grade1=" + stu_grade1 + ", stu_grade2="
				+ stu_grade2 + "]";
	}
	public String getStu_id() {
		return stu_id;
	}
	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}
	public String getStu_name() {
		return stu_name;
	}
	public void setStu_name(String stu_name) {
		this.stu_name = stu_name;
	}
	public int getStu_grade1() {
		return stu_grade1;
	}
	public void setStu_grade1(int stu_grade1) {
		this.stu_grade1 = stu_grade1;
	}
	public int getStu_grade2() {
		return stu_grade2;
	}
	public void setStu_grade2(int stu_grade2) {
		this.stu_grade2 = stu_grade2;
	}
	public Grade(String stu_id, String stu_name, int stu_grade1, int stu_grade2) {
		super();
		this.stu_id = stu_id;
		this.stu_name = stu_name;
		this.stu_grade1 = stu_grade1;
		this.stu_grade2 = stu_grade2;
	}
	public Grade() {
		super();
		// TODO Auto-generated constructor stub
	}
}
