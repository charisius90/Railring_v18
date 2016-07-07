package schedule.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.mchange.net.SocketUtils;

import dbcp.DBConnectionMgr;
import mate.model.MateDto;
import member.model.MemberDto;

//by 강병현
public class ScheduleDao {
	private DBConnectionMgr pool;
	private Connection con;
	private PreparedStatement stmt;
	private ResultSet rs;

	public ScheduleDao() {
		this.pool = DBConnectionMgr.getInstance();
	}
	
	public String getSchNo(String mat_no){
		System.out.println("함수옴");
		String sql = "select sch_no from mate where mat_no="+mat_no;
		String sch_no="";
		try {
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				sch_no = rs.getString("sch_no");
			}
			System.out.println("보내줄 스케쥴 번호:" + sch_no);
		} catch (Exception err) {
			System.out.println("getMate : " + err);
		} finally {
			pool.freeConnection(con, stmt, rs);
		}
		return sch_no;
	}
	
	public String getSchNo2(String rev_no){
		System.out.println("함수옴");
		String sql = "select sch_no from review where rev_no="+rev_no;
		String sch_no="";
		try {
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				sch_no = rs.getString("sch_no");
			}
			System.out.println("보내줄 스케쥴 번호:" + sch_no);
		} catch (Exception err) {
			System.out.println("getMate : " + err);
		} finally {
			pool.freeConnection(con, stmt, rs);
		}
		return sch_no;
	}
	// 일단 스케쥴 테이블 생성
	public String newSchedule(String mem_no){
		String getSchNo = "select max(sch_no) from schedule";
		String sch_no = "";
		String insertSchedule = "insert into schedule(sch_no, mem_no) values(?, ?)";
		try {
			con = pool.getConnection();
			stmt = con.prepareStatement(getSchNo);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				String no = rs.getString("max(sch_no)");
				if(no != null){
					sch_no = Integer.toString(Integer.parseInt(no)+1);
				}else{
					sch_no = "1";
				}
			}
			System.out.println("스케쥴번호:"+sch_no);
			System.out.println("멤버번호:"+mem_no);
			stmt = con.prepareStatement(insertSchedule);
			stmt.setString(1, sch_no); // 회원 번호로 스케쥴 생성
			stmt.setString(2, mem_no);
			int insertResult = stmt.executeUpdate();

			if (insertResult > 0) {
				System.out.println("schedule 등록 성공");
			} else {
				System.out.println("schedule 등록 실패");
			}
		} catch (Exception err) {
			System.out.println("getMate : " + err);
		} finally {
			pool.freeConnection(con, stmt, rs);
		}
		return sch_no;
	}
	
	// 스케쥴이 등록인지 수정인지 확인
	public boolean checkSchedule(String mem_no, String sch_no, String[] cityArray){
		String sql = "select maincity_no from maincity where sch_no="+sch_no;
		//maincity_no가 없으면 등록, 있으면 수정
		try {
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();

			if (rs.next()) {
				System.out.println("업데이트해야함.");
				updateSchedule(mem_no, sch_no, cityArray);
			}else{
				System.out.println("최초등록");
				insertSchedule(mem_no, sch_no, cityArray);
			}
		} catch (Exception err) {
			System.out.println("getSearchMateList : " + err);
		} finally {
			pool.freeConnection(con, stmt, rs);
		}
		return true;
	}
	
	// 스케쥴 1개에 메인시티들 등록
	public boolean insertSchedule(String mem_no, String sch_no, String[] cityArray) {
			String findCityNoSql = "select city_no from city where city_title = ?";
			String insertMainCitys = "insert into maincity(maincity_no, sch_no, city_index, city_no) "
					+ "values(seq_main_city_no.nextVal, ?, ?, ?)";
			try {
				con = pool.getConnection();
	
				ScheduleDto scheduleDto = new ScheduleDto();
				scheduleDto.setMem_no(mem_no);
	
				List<CityDto> cityDtos = new ArrayList<>();
				for (int i = 0; i < cityArray.length; i++) {
					stmt = con.prepareStatement(findCityNoSql); //도시번호 꺼내오기
					stmt.setString(1, cityArray[i]); // 도시 순서대로 번호 꺼내오기
					rs = stmt.executeQuery();
					if (rs.next()) {
						cityDtos.add(new CityDto(rs.getString("city_no"), cityArray[i], String.valueOf(i + 1))); // 도시 번호, 타이틀, 순서
					} else {
						System.out.println("cityTitle로 no 검색 실패 : " + cityArray[i]);
					}
				}
	
				for (CityDto cityDto : cityDtos) { // 위에 넣은 dto들을 디비에 넣어주기
					stmt = con.prepareStatement(insertMainCitys);
					stmt.setString(1, sch_no);
					stmt.setString(2, cityDto.getCity_index());
					stmt.setString(3, cityDto.getCity_no());
					int cityInsertResult = stmt.executeUpdate();
					if (cityInsertResult < 1) {
						System.out.println("cityDto 등록실패  : " + cityDto);
					}
				}
				return true;
			} catch (Exception err) {
				System.out.println("Schedule.insertSchedule() : " + err);
				return false;
			} finally {
				pool.freeConnection(con, stmt, rs);
			}
	}
	
	public boolean updateSchedule(String mem_no, String sch_no, String[] cityArray) {
		System.out.println("업뎃 함수옴");
		String countMainCitys = "select count(maincity_no) from maincity where sch_no="+sch_no;
		String findCityNoSql = "select city_no from city where city_title = ?";
		String updateMainCitys = "update maincity set city_index=?, city_no=? where sch_no="+sch_no+"and city_index=?";
		String insertMainCitys = "insert into maincity(maincity_no, sch_no, city_index, city_no) "
				+ "values(seq_main_city_no.nextVal, ?, ?, ?)";
		int count = 0;
		
		try {
			con = pool.getConnection();
			stmt = con.prepareStatement(countMainCitys);
			rs = stmt.executeQuery();
			if(rs.next()){
				count = Integer.parseInt(rs.getString("count(maincity_no)"));
			}
			System.out.println("기존 도시 숫자: " + count);
			
			
			ScheduleDto scheduleDto = new ScheduleDto();
			scheduleDto.setMem_no(mem_no);

			List<CityDto> cityDtos = new ArrayList<>();
			for (int i = 0; i < cityArray.length; i++) {
				stmt = con.prepareStatement(findCityNoSql); //도시번호 꺼내오기
				stmt.setString(1, cityArray[i]); // 도시 순서대로 번호 꺼내오기
				rs = stmt.executeQuery();
				if (rs.next()) {
					cityDtos.add(new CityDto(rs.getString("city_no"), cityArray[i], String.valueOf(i + 1))); // 도시 번호, 타이틀, 순서
				} else {
					System.out.println("cityTitle로 no 검색 실패 : " + cityArray[i]);
				}
			}
			for(int i=0; i<cityDtos.size(); i++){
				System.out.println(cityDtos.get(i).getCity_title());;
			}
			System.out.println("수정시작");

			for (int i=0; i < cityDtos.size(); i++) {
				if(i < count){
					stmt = con.prepareStatement(updateMainCitys);
					stmt.setString(1, cityDtos.get(i).getCity_index());
					stmt.setString(2, cityDtos.get(i).getCity_no());
					stmt.setInt(3, i+1);
					stmt.executeUpdate();
					System.out.println(cityDtos.get(i).getCity_index()+"번째 " + cityDtos.get(i).getCity_title()+"로 업뎃");
				}else{
					stmt = con.prepareStatement(insertMainCitys);
					stmt.setString(1, sch_no);
					stmt.setString(2, cityDtos.get(i).getCity_index());
					stmt.setString(3, cityDtos.get(i).getCity_no());
					stmt.executeUpdate();
					System.out.println(cityDtos.get(i).getCity_index()+"번째 " + cityDtos.get(i).getCity_title()+"로 추가");
				}
			}
			System.out.println("수정완료");
			
			return true;
		} catch (Exception err) {
			System.out.println("updateSchedule : " + err);
			return false;
		} finally {
			pool.freeConnection(con, stmt);
		}
	}
	
	// 스케쥴 리스트 불러오기
	public Vector<ScheduleDto> getScheduleList(String num){
		Vector<ScheduleDto> list = new Vector<ScheduleDto>();
		
		String sql = "select * from schedule where mem_no="+num+" order by sch_no";
		
		try {
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next()) {
				ScheduleDto dto = new ScheduleDto();
				dto.setSchedule_no(rs.getString("sch_no"));
				dto.setMem_no(rs.getString("mem_no"));
				list.add(dto);
			}
		} catch (Exception err) {
			System.out.println("getSearchMateList : " + err);
		} finally {
			pool.freeConnection(con, stmt, rs);
		}
		return list;
	}
	
	// 스케쥴 불러오기
	public ScheduleDto getSchedule(String sch_no){
		ScheduleDto scheduleDto = new ScheduleDto();
		scheduleDto.setSchedule_no(sch_no);
		String sql = "select city.*, maincity.city_index from city city, maincity maincity "
				+ "where maincity.sch_no="+sch_no+" and city.city_no=maincity.city_no order by maincity.city_index";
	
		try {
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			List<CityDto> cityDtos = new ArrayList<>();
			
			while (rs.next()) {
				cityDtos.add(new CityDto(rs.getString("city_no"), rs.getString("city_title"), rs.getString("city_index"), rs.getString("city_lat"),rs.getString("city_lng")));
			}
			
			scheduleDto.setCitys(cityDtos);
			return scheduleDto;
		} catch (Exception err) {
			System.out.println("getSearchMateList : " + err);
		} finally {
			pool.freeConnection(con, stmt, rs);
		}
		return null;
	}
	
	// 스케쥴 삭제
	public boolean deleteSchedule(String sch_no) {
		String deleteDetail = "delete detail where sch_no="+sch_no;
		String deleteMaincity = "delete maincity where sch_no="+sch_no;
		String deleteSch = "delete schedule where sch_no="+sch_no;
		
		try {
			con = pool.getConnection();
			stmt = con.prepareStatement(deleteDetail);
			stmt.executeUpdate();
			
			stmt = con.prepareStatement(deleteMaincity);
			stmt.executeUpdate();
			
			stmt = con.prepareStatement(deleteSch);
			stmt.executeUpdate();
			
			return true;
		} catch (Exception err) {
			System.out.println("Schedule.insertSchedule() : " + err);
			return false;
		} finally {
			pool.freeConnection(con, stmt, rs);
		}
	}
}

