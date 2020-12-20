package entity;

public class Student {
	String stu_id;
	String stu_name;
	String stu_email;
	String stu_phone;
	String stu_profession;
	String stu_password;
	String stu_group;
	int stu_grade1;
	int stu_grade2;
	
	public Student(String stu_id, String stu_name, String stu_email, String stu_phone, String stu_profession,
			String stu_password, String stu_group, int stu_grade1, int stu_grade2) {
		super();
		this.stu_id = stu_id;
		this.stu_name = stu_name;
		this.stu_email = stu_email;
		this.stu_phone = stu_phone;
		this.stu_profession = stu_profession;
		this.stu_password = stu_password;
		this.stu_group = stu_group;
		this.stu_grade1 = stu_grade1;
		this.stu_grade2 = stu_grade2;
	}

	public Student() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getStu_email() {
		return stu_email;
	}

	public void setStu_email(String stu_email) {
		this.stu_email = stu_email;
	}

	public String getStu_phone() {
		return stu_phone;
	}

	public void setStu_phone(String stu_phone) {
		this.stu_phone = stu_phone;
	}

	public String getStu_profession() {
		return stu_profession;
	}

	public void setStu_profession(String stu_profession) {
		this.stu_profession = stu_profession;
	}

	public String getStu_password() {
		return stu_password;
	}

	public void setStu_password(String stu_password) {
		this.stu_password = stu_password;
	}

	public String getStu_group() {
		return stu_group;
	}

	public void setStu_group(String stu_group) {
		this.stu_group = stu_group;
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
	
	
	
}
