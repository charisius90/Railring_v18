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

//by ������
public class ScheduleDao {
	private DBConnectionMgr pool;
	private Connection con;
	private PreparedStatement stmt;
	private ResultSet rs;

	public ScheduleDao() {
		this.pool = DBConnectionMgr.getInstance();
	}
	
	public String getSchNo(String mat_no){
		System.out.println("�Լ���");
		String sql = "select sch_no from mate where mat_no="+mat_no;
		String sch_no="";
		try {
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				sch_no = rs.getString("sch_no");
			}
			System.out.println("������ ������ ��ȣ:" + sch_no);
		} catch (Exception err) {
			System.out.println("getMate : " + err);
		} finally {
			pool.freeConnection(con, stmt, rs);
		}
		return sch_no;
	}
	
	public String getSchNo2(String rev_no){
		System.out.println("�Լ���");
		String sql = "select sch_no from review where rev_no="+rev_no;
		String sch_no="";
		try {
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				sch_no = rs.getString("sch_no");
			}
			System.out.println("������ ������ ��ȣ:" + sch_no);
		} catch (Exception err) {
			System.out.println("getMate : " + err);
		} finally {
			pool.freeConnection(con, stmt, rs);
		}
		return sch_no;
	}
	// �ϴ� ������ ���̺� ����
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
			System.out.println("�������ȣ:"+sch_no);
			System.out.println("�����ȣ:"+mem_no);
			stmt = con.prepareStatement(insertSchedule);
			stmt.setString(1, sch_no); // ȸ�� ��ȣ�� ������ ����
			stmt.setString(2, mem_no);
			int insertResult = stmt.executeUpdate();

			if (insertResult > 0) {
				System.out.println("schedule ��� ����");
			} else {
				System.out.println("schedule ��� ����");
			}
		} catch (Exception err) {
			System.out.println("getMate : " + err);
		} finally {
			pool.freeConnection(con, stmt, rs);
		}
		return sch_no;
	}
	
	// �������� ������� �������� Ȯ��
	public boolean checkSchedule(String mem_no, String sch_no, String[] cityArray){
		String sql = "select maincity_no from maincity where sch_no="+sch_no;
		//maincity_no�� ������ ���, ������ ����
		try {
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();

			if (rs.next()) {
				System.out.println("������Ʈ�ؾ���.");
				updateSchedule(mem_no, sch_no, cityArray);
			}else{
				System.out.println("���ʵ��");
				insertSchedule(mem_no, sch_no, cityArray);
			}
		} catch (Exception err) {
			System.out.println("getSearchMateList : " + err);
		} finally {
			pool.freeConnection(con, stmt, rs);
		}
		return true;
	}
	
	// ������ 1���� ���ν�Ƽ�� ���
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
					stmt = con.prepareStatement(findCityNoSql); //���ù�ȣ ��������
					stmt.setString(1, cityArray[i]); // ���� ������� ��ȣ ��������
					rs = stmt.executeQuery();
					if (rs.next()) {
						cityDtos.add(new CityDto(rs.getString("city_no"), cityArray[i], String.valueOf(i + 1))); // ���� ��ȣ, Ÿ��Ʋ, ����
					} else {
						System.out.println("cityTitle�� no �˻� ���� : " + cityArray[i]);
					}
				}
	
				for (CityDto cityDto : cityDtos) { // ���� ���� dto���� ��� �־��ֱ�
					stmt = con.prepareStatement(insertMainCitys);
					stmt.setString(1, sch_no);
					stmt.setString(2, cityDto.getCity_index());
					stmt.setString(3, cityDto.getCity_no());
					int cityInsertResult = stmt.executeUpdate();
					if (cityInsertResult < 1) {
						System.out.println("cityDto ��Ͻ���  : " + cityDto);
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
		System.out.println("���� �Լ���");
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
			System.out.println("���� ���� ����: " + count);
			
			
			ScheduleDto scheduleDto = new ScheduleDto();
			scheduleDto.setMem_no(mem_no);

			List<CityDto> cityDtos = new ArrayList<>();
			for (int i = 0; i < cityArray.length; i++) {
				stmt = con.prepareStatement(findCityNoSql); //���ù�ȣ ��������
				stmt.setString(1, cityArray[i]); // ���� ������� ��ȣ ��������
				rs = stmt.executeQuery();
				if (rs.next()) {
					cityDtos.add(new CityDto(rs.getString("city_no"), cityArray[i], String.valueOf(i + 1))); // ���� ��ȣ, Ÿ��Ʋ, ����
				} else {
					System.out.println("cityTitle�� no �˻� ���� : " + cityArray[i]);
				}
			}
			for(int i=0; i<cityDtos.size(); i++){
				System.out.println(cityDtos.get(i).getCity_title());;
			}
			System.out.println("��������");

			for (int i=0; i < cityDtos.size(); i++) {
				if(i < count){
					stmt = con.prepareStatement(updateMainCitys);
					stmt.setString(1, cityDtos.get(i).getCity_index());
					stmt.setString(2, cityDtos.get(i).getCity_no());
					stmt.setInt(3, i+1);
					stmt.executeUpdate();
					System.out.println(cityDtos.get(i).getCity_index()+"��° " + cityDtos.get(i).getCity_title()+"�� ����");
				}else{
					stmt = con.prepareStatement(insertMainCitys);
					stmt.setString(1, sch_no);
					stmt.setString(2, cityDtos.get(i).getCity_index());
					stmt.setString(3, cityDtos.get(i).getCity_no());
					stmt.executeUpdate();
					System.out.println(cityDtos.get(i).getCity_index()+"��° " + cityDtos.get(i).getCity_title()+"�� �߰�");
				}
			}
			System.out.println("�����Ϸ�");
			
			return true;
		} catch (Exception err) {
			System.out.println("updateSchedule : " + err);
			return false;
		} finally {
			pool.freeConnection(con, stmt);
		}
	}
	
	// ������ ����Ʈ �ҷ�����
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
	
	// ������ �ҷ�����
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
	
	// ������ ����
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

