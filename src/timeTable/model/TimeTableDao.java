package timeTable.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dbcp.DBConnectionMgr;

//by ������, �ڱ�ä
public class TimeTableDao {
	private DBConnectionMgr pool;
	private Connection con;
	private PreparedStatement stmt;
	private PreparedStatement stmt2;
	private ResultSet rs;
	private ResultSet rs2;
	
	public TimeTableDao() {
		this.pool = DBConnectionMgr.getInstance();
	}
	
	public String getMemNo(String sch_no){
		System.out.println("�Լ���");
		String sql = "select mem_no from schedule where sch_no="+sch_no;
		String mem_no="";
		try {
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				mem_no = rs.getString("mem_no");
			}
			System.out.println("������ ������ ��ȣ:" + sch_no);
		} catch (Exception err) {
			System.out.println("getMate : " + err);
		} finally {
			pool.freeConnection(con, stmt, rs);
		}
		return mem_no;
	}
	
	public List<TimeTableCityDto> getTimeTable(String sch_no, String mem_no){
		System.out.println("dao �Լ���");
		System.out.println(sch_no+","+mem_no);
		List<TimeTableCityDto> tableCityList = new ArrayList<>();
		
		
		String getCity = "select city.city_no, city.city_title, city.city_title_kor, maincity.city_index"
				+ " from city city, maincity maincity, schedule schedule"
				+ " where schedule.mem_no="+mem_no+" and schedule.sch_no=maincity.sch_no and maincity.sch_no="+sch_no
				+ " and city.city_no=maincity.city_no order by maincity.city_index";
		
		try {
			con = pool.getConnection();
			stmt = con.prepareStatement(getCity);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				TimeTableCityDto cityDto = new TimeTableCityDto();
				
				cityDto.setCity_no(rs.getString("city_no"));
				cityDto.setCity_title(rs.getString("city_title"));
				cityDto.setCity_title_kor(rs.getString("city_title_kor"));
				cityDto.setCity_index(rs.getString("city_index"));
				
				String getInfo ="select info.info_no, info.info_title, info.info_title_kor, detail.info_index"
						+ " from info info, detail detail where detail.sch_no="+sch_no+" and info.info_no=detail.info_no"
						+ " and detail.city_no ="+cityDto.getCity_no()
						+ " order by detail.info_index";
				
				stmt2 = con.prepareStatement(getInfo);
				rs2 = stmt2.executeQuery();
				
				List<TimeTableInfoDto> tInfoDtos = new ArrayList<>();
				
				while(rs2.next()){
					tInfoDtos.add(new TimeTableInfoDto(rs2.getString("info_no"), rs2.getString("info_title"), rs2.getString("info_title_kor"), rs2.getString("info_index")));
				}
				System.out.println("tInfoDtos�Ϸ�");
				cityDto.setInfos(tInfoDtos);
				
				tableCityList.add(cityDto);
				System.out.println("tableCityList�Ϸ�");
			}
			return tableCityList;
		} catch (Exception err) {
			System.out.println("getTimeTable : " + err);
		} finally {
			pool.freeConnection(con, stmt, rs);
			pool.freeConnection(con, stmt2, rs2);
		}
		return null;
	}
}

