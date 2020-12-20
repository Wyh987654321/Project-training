package entity;

public class Student2 extends Student {
	String topic;

	public Student2() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Student2(String stu_id, String stu_name, String stu_email, String stu_phone, String stu_profession,
			String stu_password, String stu_group, int stu_grade1, int stu_grade2,String topic) {
		super(stu_id, stu_name, stu_email, stu_phone, stu_profession, stu_password, stu_group, stu_grade1, stu_grade2);
		this.topic =topic;
		// TODO Auto-generated constructor stub
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	
}
