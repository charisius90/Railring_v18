package member.model;

public class MemberDto {
	// 기본 셋
	private String mem_no; 
	private String mem_email; 
	private String mem_pw;
	private String mem_name; 
	private String mem_gender;
	private String mem_birth; 
	private String mem_date;
	private String mem_image;
	private String mem_background;
	
	// 추가 셋
	//private String mem_tel;
	//private String mem_zipcode;
	//private String mem_addr;
	
	public String getMem_no() {
		return mem_no;
	}
	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}
	public String getMem_image() {
		return mem_image;
	}
	public void setMem_image(String mem_image) {
		this.mem_image = mem_image;
	}
	public String getMem_background() {
		return mem_background;
	}
	public void setMem_background(String mem_background) {
		this.mem_background = mem_background;
	}
	public String getMem_email() {
		return mem_email;
	}
	public void setMem_email(String mem_email) {
		this.mem_email = mem_email;
	}
	public String getMem_pw() {
		return mem_pw;
	}
	public void setMem_pw(String mem_pw) {
		this.mem_pw = mem_pw;
	}
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getMem_gender() {
		return mem_gender;
	}
	public void setMem_gender(String mem_gender) {
		this.mem_gender = mem_gender;
	}
	public String getMem_birth() {
		return mem_birth;
	}
	public void setMem_birth(String mem_birth) {
		this.mem_birth = mem_birth;
	}
	public String getMem_date() {
		return mem_date;
	}
	public void setMem_date(String mem_date) {
		this.mem_date = mem_date;
	} 
}
