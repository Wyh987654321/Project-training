package entity;

public class Reply2 {
	String rep_id;
	String rep_time;
	String rep_address;
	String rep_teacher;
	String rep_group;

	public Reply2() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Reply2(String rep_id, String rep_time, String rep_address, String rep_teacher, String rep_group) {
		super();
		this.rep_id = rep_id;
		this.rep_time = rep_time;
		this.rep_address = rep_address;
		this.rep_teacher = rep_teacher;
		this.rep_group = rep_group;
	}

	public String getRep_id() {
		return rep_id;
	}

	public void setRep_id(String rep_id) {
		this.rep_id = rep_id;
	}

	public String getRep_time() {
		return rep_time;
	}

	public void setRep_time(String rep_time) {
		this.rep_time = rep_time;
	}

	public String getRep_address() {
		return rep_address;
	}

	public void setRep_address(String rep_address) {
		this.rep_address = rep_address;
	}

	public String getRep_teacher() {
		return rep_teacher;
	}

	public void setRep_teacher(String rep_teacher) {
		this.rep_teacher = rep_teacher;
	}

	public String getRep_group() {
		return rep_group;
	}

	public void setRep_group(String rep_group) {
		this.rep_group = rep_group;
	}

}
