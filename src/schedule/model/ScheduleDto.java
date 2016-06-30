package schedule.model;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDto {
	private String schedule_no;
	private String mem_no;
	private List<CityDto> citys = new ArrayList<>();

	public String getSchedule_no() {
		return schedule_no;
	}

	public void setSchedule_no(String schedule_no) {
		this.schedule_no = schedule_no;
	}

	public String getMem_no() {
		return mem_no;
	}

	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}

	public List<CityDto> getCitys() {
		return citys;
	}

	public void setCitys(List<CityDto> citys) {
		this.citys = citys;
	}

	@Override
	public String toString() {
		return "ScheduleDto [schedule_no=" + schedule_no + ", mem_no=" + mem_no + ", citys=" + citys + "]";
	}
}
