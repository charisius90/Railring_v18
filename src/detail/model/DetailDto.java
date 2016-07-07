package detail.model;

import java.util.ArrayList;
import java.util.List;

//by 강병현,박규채
public class DetailDto {
//	private String info_index;
//	private String info_no;
	private String city_no;
	private String sch_no;
	private List<InfoDto> infos = new ArrayList<>();
	
//	public String getInfo_index() {
//		return info_index;
//	}
//	public void setInfo_index(String info_index) {
//		this.info_index = info_index;
//	}
//	public String getInfo_no() {
//		return info_no;
//	}
//	public void setInfo_no(String info_no) {
//		this.info_no = info_no;
//	}
	public String getCity_no() {
		return city_no;
	}
	public void setCity_no(String city_no) {
		this.city_no = city_no;
	}
	public String getSch_no() {
		return sch_no;
	}
	public void setSch_no(String sch_no) {
		this.sch_no = sch_no;
	}
	public List<InfoDto> getInfos() {
		return infos;
	}
	public void setInfos(List<InfoDto> infos) {
		this.infos = infos;
	}
	@Override
	public String toString() {
		return "DetailDto [city_no=" + city_no + ", sch_no=" + sch_no + ", infos=" + infos + "]";
	}
	
}
