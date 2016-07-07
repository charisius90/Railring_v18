package schedule.model;

import java.util.ArrayList;
import java.util.List;

//by °­º´Çö
public class CityDto {
	private String city_no;
	private String city_title;
	private String city_index;
	private String city_lat;
	private String city_lng;

	public CityDto(String city_title, String city_index) {
		this.city_title = city_title;
		this.city_index = city_index;
	}
	
	public CityDto(String city_no, String city_title, String city_index) {
		this.city_no = city_no;
		this.city_title = city_title;
		this.city_index = city_index;
	}
	
	public CityDto(String city_no, String city_title, String city_index, String lat, String lng) {
		this.city_no = city_no;
		this.city_title = city_title;
		this.city_index = city_index;
		this.city_lat = lat;
		this.city_lng = lng;
	}
	
	public CityDto() {

	}

	public String getCity_no() {
		return city_no;
	}

	public void setCity_no(String city_no) {
		this.city_no = city_no;
	}

	public String getCity_index() {
		return city_index;
	}

	public void setCity_index(String city_index) {
		this.city_index = city_index;
	}

	public String getCity_title() {
		return city_title;
	}

	public void setCity_title(String city_title) {
		this.city_title = city_title;
	}

	public String getCity_lat() {
		return city_lat;
	}

	public void setCity_lat(String city_lat) {
		this.city_lat = city_lat;
	}

	public String getCity_lng() {
		return city_lng;
	}

	public void setCity_lng(String city_lng) {
		this.city_lng = city_lng;
	}

	@Override
	public String toString() {
		return "CityDto [city_no=" + city_no + ", city_title=" + city_title + ", city_index=" + city_index
				+ ", city_lat=" + city_lat + ", city_lng=" + city_lng + "]";
	}
}
