package entity;

public class Teacher {
	String tea_id;
	String tea_name;
	String tea_password;
	String tea_email;
	String tea_phone;
	String topic_num;
	String guideNum_need;

	public String getTopic_num() {
		return topic_num;
	}

	public void setTopic_num(String topic_num) {
		this.topic_num = topic_num;
	}

	public String getGuideNum_need() {
		return guideNum_need;
	}

	public void setGuideNum_need(String guideNum_need) {
		this.guideNum_need = guideNum_need;
	}

	public Teacher() {
		this.tea_id = "";
		this.tea_name = "";
		this.tea_password = "";
		this.tea_email = "";
		this.tea_phone = "";
	}

	public Teacher(String tea_id, String tea_name, String tea_password, String tea_email, String tea_phone) {
		super();
		this.tea_id = tea_id;
		this.tea_name = tea_name;
		this.tea_password = tea_password;
		this.tea_email = tea_email;
		this.tea_phone = tea_phone;
	}

	@Override
	public String toString() {
		return "Teacher [tea_id=" + tea_id + ", tea_name=" + tea_name + ", tea_password=" + tea_password
				+ ", tea_email=" + tea_email + ", tea_phone=" + tea_phone + "]";
	}

	public String getTea_id() {
		return tea_id;
	}

	public void setTea_id(String tea_id) {
		this.tea_id = tea_id;
	}

	public String getTea_name() {
		return tea_name;
	}

	public void setTea_name(String tea_name) {
		this.tea_name = tea_name;
	}

	public String getTea_password() {
		return tea_password;
	}

	public void setTea_password(String tea_password) {
		this.tea_password = tea_password;
	}

	public String getTea_email() {
		return tea_email;
	}

	public void setTea_email(String tea_email) {
		this.tea_email = tea_email;
	}

	public String getTea_phone() {
		return tea_phone;
	}

	public void setTea_phone(String tea_phone) {
		this.tea_phone = tea_phone;
	}

}
