package detail.model;

import java.util.ArrayList;
import java.util.List;

public class InfoDto {
	private String info_no;
	private String info_title;
	private String info_title_kor;
	private String info_index;
	private String info_lat;
	private String info_lng;

	public InfoDto(String info_title, String info_index) {
		this.info_title = info_title;
		this.info_index = info_index;
	}
	
	public InfoDto(String info_no, String info_title, String info_index) {
		this.info_no = info_no;
		this.info_title = info_title;
		this.info_index = info_index;
	}
	
	public InfoDto(String info_no, String info_title, String info_index, String lat, String lng) {
		this.info_no = info_no;
		this.info_title = info_title;
		this.info_index = info_index;
		this.info_lat = lat;
		this.info_lng = lng;
	}
	
	public InfoDto(String info_no, String info_title, String info_title_kor, String info_index, String lat, String lng) {
		this.info_no = info_no;
		this.info_title = info_title;
		this.info_title_kor = info_title_kor;
		this.info_index = info_index;
		this.info_lat = lat;
		this.info_lng = lng;
	}
	public InfoDto() {

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

	public String getInfo_index() {
		return info_index;
	}

	public void setInfo_index(String info_index) {
		this.info_index = info_index;
	}

	public String getInfo_lat() {
		return info_lat;
	}

	public void setInfo_lat(String info_lat) {
		this.info_lat = info_lat;
	}

	public String getInfo_lng() {
		return info_lng;
	}

	public void setInfo_lng(String info_lng) {
		this.info_lng = info_lng;
	}

	public String getInfo_title_kor() {
		return info_title_kor;
	}

	public void setInfo_title_kor(String info_title_kor) {
		this.info_title_kor = info_title_kor;
	}

	@Override
	public String toString() {
		return "InfoDto [info_no=" + info_no + ", info_title=" + info_title + ", info_title_kor=" + info_title_kor
				+ ", info_index=" + info_index + ", info_lat=" + info_lat + ", info_lng=" + info_lng + "]";
	}

	
}
