package timeTable.model;

import java.util.ArrayList;
import java.util.List;

//by 강병현, 박규채
public class TimeTableInfoDto {
	private String info_no;
	private String info_title;
	private String info_title_kor;
	private String info_index;

	public TimeTableInfoDto(String info_title) {
		this.info_title = info_title;
	}
	
	public TimeTableInfoDto(String info_no, String info_title) {
		this.info_no = info_no;
		this.info_title = info_title;
	}
	
	public TimeTableInfoDto(String info_no, String info_title, String info_title_kor) {
		this.info_no = info_no;
		this.info_title = info_title;
		this.info_title_kor = info_title_kor;
	}
	
	public TimeTableInfoDto(String info_no, String info_title, String info_title_kor, String info_index) {
		this.info_no = info_no;
		this.info_title = info_title;
		this.info_title_kor = info_title_kor;
		this.info_index = info_index;
	}
	
	public TimeTableInfoDto() {

	}

	public String getInfo_no() {
		return info_no;
	}

	public void setInfo_no(String info_no) {
		this.info_no = info_no;
	}

	public String getInfo_title() {
		return info_title;
	}

	public void setInfo_title(String info_title) {
		this.info_title = info_title;
	}

	public String getInfo_title_kor() {
		return info_title_kor;
	}

	public void setInfo_title_kor(String info_title_kor) {
		this.info_title_kor = info_title_kor;
	}

	public String getInfo_index() {
		return info_index;
	}

	public void setInfo_index(String info_index) {
		this.info_index = info_index;
	}

	@Override
	public String toString() {
		return "TimeTableInfoDto [info_no=" + info_no + ", info_title=" + info_title + ", info_title_kor="
				+ info_title_kor + ", info_index=" + info_index + "]";
	}
}
