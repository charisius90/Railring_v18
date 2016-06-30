package matereply.model;

import java.io.Serializable;

public class MateReplyDto implements Serializable {
	private String mat_re_no;
	private String mat_re_content;
	private String mat_re_date;
	private String mat_re_pos;
	private String mat_re_depth;
	private String mat_no;
	private String mem_no;
	private String mat_group;
	private String mem_name;
	
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getMat_re_no() {
		return mat_re_no;
	}
	public void setMat_re_no(String mat_re_no) {
		this.mat_re_no = mat_re_no;
	}
	public String getMat_re_content() {
		return mat_re_content;
	}
	public void setMat_re_content(String mat_re_content) {
		this.mat_re_content = mat_re_content;
	}
	public String getMat_re_date() {
		return mat_re_date;
	}
	public void setMat_re_date(String mat_re_date) {
		this.mat_re_date = mat_re_date;
	}
	public String getMat_re_pos() {
		return mat_re_pos;
	}
	public void setMat_re_pos(String mat_re_pos) {
		this.mat_re_pos = mat_re_pos;
	}
	public String getMat_re_depth() {
		return mat_re_depth;
	}
	public void setMat_re_depth(String mat_re_depth) {
		this.mat_re_depth = mat_re_depth;
	}
	public String getMat_no() {
		return mat_no;
	}
	public void setMat_no(String mat_no) {
		this.mat_no = mat_no;
	}
	public String getMem_no() {
		return mem_no;
	}
	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}
	public String getMat_group() {
		return mat_group;
	}
	public void setMat_group(String mat_group) {
		this.mat_group = mat_group;
	}
}
