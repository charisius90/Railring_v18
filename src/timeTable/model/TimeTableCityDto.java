package timeTable.model;

import java.util.ArrayList;
import java.util.List;

//by 강병현, 박규채
public class TimeTableCityDto {
	private String city_no;
	private String city_title;
	private String city_title_kor;
	private String city_index;
	private List<TimeTableInfoDto> infos = new ArrayList<>();
	
	public String getCity_no() {
		return city_no;
	}
	public void setCity_no(String city_no) {
		this.city_no = city_no;
	}
	public String getCity_title() {
		return city_title;
	}
	public void setCity_title(String city_title) {
		this.city_title = city_title;
	}
	public String getCity_title_kor() {
		return city_title_kor;
	}
	public void setCity_title_kor(String city_title_kor) {
		this.city_title_kor = city_title_kor;
	}
	public String getCity_index() {
		return city_index;
	}
	public void setCity_index(String city_index) {
		this.city_index = city_index;
	}
	public List<TimeTableInfoDto> getInfos() {
		return infos;
	}
	public void setInfos(List<TimeTableInfoDto> infos) {
		this.infos = infos;
	}
	@Override
	public String toString() {
		return "TimeTableCityDto [city_no=" + city_no + ", city_title=" + city_title + ", city_title_kor="
				+ city_title_kor + ", city_index=" + city_index + ", infos=" + infos + "]";
	}
}
