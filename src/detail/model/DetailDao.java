package detail.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import dbcp.DBConnectionMgr;

//by 강병현,박규채
public class DetailDao {
	private DBConnectionMgr pool;
	private Connection con;
	private PreparedStatement stmt;
	private ResultSet rs;

	public DetailDao() {
		this.pool = DBConnectionMgr.getInstance();
	}

	public boolean insertDetail(String city_no, String sch_no, String[] infoArray) {

		String findInfoNoSql = "select info_no from info where info_title = ?";
		String insertMainCitys = "insert into detail(detail_no, info_index, city_no, info_no, sch_no) "
				+ "values(seq_detail_no.nextVal, ?, ?, ?, ?)";
		try {
			con = pool.getConnection();

			DetailDto detailDto = new DetailDto();
			detailDto.setCity_no(city_no);
			detailDto.setSch_no(sch_no);

			List<InfoDto> infoDtos = new ArrayList<>();
			for (int i = 0; i < infoArray.length; i++) {
				stmt = con.prepareStatement(findInfoNoSql); //info번호 꺼내오기
				stmt.setString(1, infoArray[i]); // 도시 순서대로 번호 꺼내오기
				rs = stmt.executeQuery();
				if (rs.next()) {
					infoDtos.add(new InfoDto(rs.getString("info_no"), infoArray[i], String.valueOf(i + 1))); // 도시 번호, 타이틀, 순서
				} else {
					System.out.println("infoTitle로 no 검색 실패 : " + infoArray[i]);
				}
			}

			for (InfoDto infoDto : infoDtos) { // 위에 넣은 dto들을 디비에 넣어주기
				stmt = con.prepareStatement(insertMainCitys);
				stmt.setString(1, infoDto.getInfo_index());
				stmt.setString(2, city_no);
				stmt.setString(3, infoDto.getInfo_no());
				stmt.setString(4, sch_no);
				int detailInsertResult = stmt.executeUpdate();
				if (detailInsertResult < 1) {
					System.out.println("cityDto 등록실패  : " + infoDto);
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
	
	public DetailDto getDetail(String sch_no, String city_no){
		DetailDto detailDto = new DetailDto();
		detailDto.setSch_no(sch_no);
		detailDto.setCity_no(city_no);
		String sql = "select info.*, detail.info_index from info info, detail detail where detail.sch_no="+sch_no
				+" and detail.city_no="+city_no+" and info.info_no=detail.info_no order by detail.info_index";
		try {
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			List<InfoDto> infoDtos = new ArrayList<>();
			
			while (rs.next()) {
				infoDtos.add(new InfoDto(rs.getString("info_no"), rs.getString("info_title"), rs.getString("info_title_kor"), rs.getString("info_index"), rs.getString("info_lat"),rs.getString("info_lng")));
			}
			
			detailDto.setInfos(infoDtos);
			return detailDto;
		} catch (Exception err) {
			System.out.println("getSearchMateList : " + err);
		} finally {
			pool.freeConnection(con, stmt, rs);
		}
		return null;
	}
}

