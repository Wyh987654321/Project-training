package entity;

public class Group {
	String gro_id;
	int gro_number;
	String leader;
	String gro_teacher;
	String gro_topic;
	String gro_members = "";
	int grade1;
	int grade2;
	int grade3;

	public Group() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Group(String gro_id, int gro_number, String gro_teacher, String gro_topic, int grade1, int grade2,
			int grade3) {

		this.gro_id = gro_id;
		this.gro_number = gro_number;
		this.gro_teacher = gro_teacher;
		this.gro_topic = gro_topic;
		this.grade1 = grade1;
		this.grade2 = grade2;
		this.grade3 = grade3;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	@Override
	public String toString() {
		return "Group [gro_id=" + gro_id + ", gro_number=" + gro_number + ", gro_teacher=" + gro_teacher
				+ ", gro_topic=" + gro_topic + ", gro_menmbers=" + gro_members + ", grade1=" + grade1 + ", grade2="
				+ grade2 + ", grade3=" + grade3 + "]";
	}

	public String getGro_id() {
		return gro_id;
	}

	public void setGro_id(String gro_id) {
		this.gro_id = gro_id;
	}

	public int getGro_number() {
		return gro_number;
	}

	public void setGro_number(int gro_number) {
		this.gro_number = gro_number;
	}

	public String getGro_teacher() {
		return gro_teacher;
	}

	public void setGro_teacher(String gro_teacher) {
		this.gro_teacher = gro_teacher;
	}

	public String getGro_topic() {
		return gro_topic;
	}

	public void setGro_topic(String gro_topic) {
		this.gro_topic = gro_topic;
	}

	public String getGro_members() {
		return gro_members;
	}

	public void setGro_members(String gro_members) {
		this.gro_members = gro_members;
	}

	public int getGrade1() {
		return grade1;
	}

	public void setGrade1(int grade1) {
		this.grade1 = grade1;
	}

	public int getGrade2() {
		return grade2;
	}

	public void setGrade2(int grade2) {
		this.grade2 = grade2;
	}

	public int getGrade3() {
		return grade3;
	}

	public void setGrade3(int grade3) {
		this.grade3 = grade3;
	}

}
