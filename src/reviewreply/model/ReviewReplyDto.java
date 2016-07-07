package reviewreply.model;

import java.io.Serializable;

//by 손승한, 강병현
public class ReviewReplyDto implements Serializable {
	private String rev_re_no;
	private String rev_re_content;
	private String rev_re_date;
	private String rev_re_pos;
	private String rev_re_depth;
	private String rev_no;
	private String mem_no;
	private String rev_group;
	private String mem_name;
	
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getRev_re_no() {
		return rev_re_no;
	}
	public void setRev_re_no(String rev_re_no) {
		this.rev_re_no = rev_re_no;
	}
	public String getRev_re_content() {
		return rev_re_content;
	}
	public void setRev_re_content(String rev_re_content) {
		this.rev_re_content = rev_re_content;
	}
	public String getRev_re_date() {
		return rev_re_date;
	}
	public void setRev_re_date(String rev_re_date) {
		this.rev_re_date = rev_re_date;
	}
	public String getRev_re_pos() {
		return rev_re_pos;
	}
	public void setRev_re_pos(String rev_re_pos) {
		this.rev_re_pos = rev_re_pos;
	}
	public String getRev_re_depth() {
		return rev_re_depth;
	}
	public void setRev_re_depth(String rev_re_depth) {
		this.rev_re_depth = rev_re_depth;
	}
	public String getRev_no() {
		return rev_no;
	}
	public void setRev_no(String rev_no) {
		this.rev_no = rev_no;
	}
	public String getMem_no() {
		return mem_no;
	}
	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}
	public String getRev_group() {
		return rev_group;
	}
	public void setRev_group(String rev_group) {
		this.rev_group = rev_group;
	}
}
