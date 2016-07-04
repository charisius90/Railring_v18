package mate.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import dbcp.DBConnectionMgr;
import utils.Paging;

public class MateDao {

	private Connection con;
	private PreparedStatement stmt;
	private ResultSet rs;
	private DBConnectionMgr pool;

	public MateDao() {
		try {
			pool = DBConnectionMgr.getInstance();
		} catch (Exception err) {
			System.out.println("MateDao" + err);
		}
	}

	public void insertMate(MateDto mate) {

		String sql = "insert into mate(mat_no, mat_subject, mat_content,mat_date, mat_count, mem_no, sch_no)"
				+ "values(seq_mate_no.nextVal,?,?,sysdate,0,?,?)";

		try {
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);

			stmt.setString(1, mate.getMat_subject());
			stmt.setString(2, mate.getMat_content());
			stmt.setString(3, mate.getMem_no());
			stmt.setString(4, mate.getSch_no());
			
			stmt.executeUpdate();
		} catch (Exception err) {
			System.out.println("insertMate()" + err);
		} finally {
			pool.freeConnection(con, stmt);
		}
	}

	public void deleteMate(int num) {
		System.out.println("삭제메서드옴");
		try {
			String sql = "delete from mate where mat_no=" + num;

			con = pool.getConnection();
			stmt = con.prepareStatement(sql);

			stmt.executeUpdate();
		} catch (Exception err) {
			System.out.println("deleteBoard : " + err);
		} finally {
			pool.freeConnection(con, stmt);
		}
	}

	// 규채 : 게시글 조회수 증가
	public MateDto getMate(int num, boolean isRead) {
		System.out.println("이제 열 글 번호: " + num);
		String sql = "select mat.*, mem.mem_name from  mate mat, member mem where mat.mem_no = mem.mem_no and mat.mat_no =" + num;
		MateDto dto = new MateDto();
		String sql2 = "update mate set mat_count=mat_count+1 where mat_no=" + num;
		try {
			con = pool.getConnection();
			
			 if(isRead == true){
		            stmt = con.prepareStatement(sql2);
		            stmt.executeUpdate();
		     }
			
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();

			if (rs.next()) {
				dto.setMat_no(rs.getString("mat_no"));
				dto.setSch_no(rs.getString("sch_no"));
				dto.setMat_subject(rs.getString("mat_subject"));
				dto.setMat_content(rs.getString("mat_content"));
				dto.setMat_date(rs.getString("mat_date"));
				dto.setMem_no(rs.getString("mem_no"));
				dto.setMat_count(rs.getString("mat_count"));
				dto.setMem_name(rs.getString("mem_name"));
			}
		} catch (Exception err) {
			System.out.println("getMate : " + err);
		} finally {
			pool.freeConnection(con, stmt, rs);
		}
		return dto;
	}

	public void updateMate(MateDto dto) {
		try {
			String sql = "update mate set mat_subject=?, mat_content=? where mat_no=?";

			con = pool.getConnection();
			stmt = con.prepareStatement(sql);

			stmt.setString(1, dto.getMat_subject());
			stmt.setString(2, dto.getMat_content());
			stmt.setString(3, dto.getMat_no());

			stmt.executeUpdate();
		} catch (Exception err) {
			System.out.println("insertBoard : " + err);
		} finally {
			pool.freeConnection(con, stmt);
		}
	}

	/**
	 * pagingList
	 * 
	 * @return
	 */
	public Paging<MateDto> getMatePagingList(int page, String searchOption, String keyword) {
		Paging<MateDto> paging = new Paging<MateDto>();
		paging.setCurrentPage(page);
		paging.setSearchKeyword(keyword);
		paging.setSearchOption(searchOption);
		paging.setList(new ArrayList());

		String searchSql = "";
		if (keyword != null && !keyword.isEmpty()) {
			if ("ALL".equals(searchOption)) {
				searchSql += " and mat.mat_subject like '%" + keyword + "%' "
						+ "and mat.mat_content like '%" + keyword + "%'";
			} else if ("SUBJECT".equals(searchOption)) {
				searchSql += " and mat.mat_subject like '%" + keyword + "%'";
			} else if ("MEMNO".equals(searchOption)) {
				searchSql += " and mem.mem_name like '%" + keyword + "%'";
			}
		}

		String totalCountSql = "select count(*) total from mate mat, member mem "
				+ "where mat.mem_no = mem.mem_no" + searchSql;
		String pagingSql = "select * from"
				+ "(select mat.*, mem.mem_name, ROW_NUMBER() OVER (ORDER BY mat_no desc) rnum "
				+ "from mate mat, member mem "
				+ "where mat.mem_no = mem.mem_no"
				+ searchSql
				+ ") where rnum between ? and ?";

		try {
			con = pool.getConnection();
			stmt = con.prepareStatement(totalCountSql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				paging.setTotalCount(rs.getInt("total"));
			}
			
			stmt = con.prepareStatement(pagingSql);
			stmt.setInt(1, (page - 1) * paging.getSizePerPage() + 1);
			stmt.setInt(2, page * paging.getSizePerPage());
			System.out.println("paging sql :" +pagingSql);
			rs = stmt.executeQuery();
		
			List<MateDto> list = new ArrayList<MateDto>();
			while (rs.next()) {
				MateDto dto = new MateDto();
				dto.setMat_no(rs.getString("mat_no"));
				dto.setSch_no(rs.getString("sch_no"));
				dto.setMat_subject(rs.getString("mat_subject"));
				dto.setMat_content(rs.getString("mat_content"));
				dto.setMat_date(rs.getString("mat_date"));
				dto.setMem_no(rs.getString("mem_no"));
				dto.setMat_count(rs.getString("mat_count"));
				dto.setMem_name(rs.getString("mem_name"));

				list.add(dto);
			}
			paging.setList(list);

		} catch (Exception err) {
			System.out.println("getMateList : " + err + " : " + pagingSql);
		} finally {
			pool.freeConnection(con, stmt, rs);
		}
		System.out.println(paging.getList().size());
		System.out.println(paging);
		return paging;
	}
	
//	안씀
	public Vector<MateDto> getSearchMateList(String searchOption, String keyword) {
		Vector<MateDto> vList = new Vector<MateDto>();

		String sql = "select * from mate";
		if (keyword.equals("") || keyword.isEmpty()) {
			sql += " order by mat_no desc";
		} else {
			if ("ALL".equals(searchOption)) {
				sql += " where mat_subject like '%" + keyword + "%' and mat_content like '%" + keyword + "%'";
			} else if ("SUBJECT".equals(searchOption)) {
				sql += " where mat_subject like '%" + keyword + "%'";
			} else if ("MEMNO".equals(searchOption)) {
				sql += " where mem_no = " + keyword;
			}
			sql += " order by mat_no desc";
		}
		System.out.println(sql);
		try {
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next()) {
				MateDto dto = new MateDto();
				dto.setMat_no(rs.getString("mat_no"));
				dto.setSch_no(rs.getString("sch_no"));
				dto.setMat_subject(rs.getString("mat_subject"));
				dto.setMat_content(rs.getString("mat_content"));
				dto.setMat_date(rs.getString("mat_date"));
				dto.setMem_no(rs.getString("mem_no"));
				dto.setMat_count(rs.getString("mat_count"));

				vList.add(dto);
			}
		} catch (Exception err) {
			System.out.println("getSearchMateList : " + err);
		} finally {
			pool.freeConnection(con, stmt, rs);
		}
		System.out.println(vList.size());
		return vList;
	}
	
	// 규채 : 마이페이지에서 게시판에 자신이 쓴 글 가져오기
		public ArrayList getMate(String mem_no){
			String sql = "select * from mate where mem_no = ? ";
			
			ArrayList<MateDto> list = new ArrayList<MateDto>();
			
			try {
				con = pool.getConnection();
				stmt = con.prepareStatement(sql);
				
				stmt.setString(1, mem_no);
				rs = stmt.executeQuery();	
				
				while(rs.next()){
					MateDto dto = new MateDto();
					dto.setMem_no(rs.getString("mem_no"));
					dto.setMat_subject(rs.getString("mat_subject"));
					dto.setMat_date(rs.getString("mat_date"));
					dto.setMat_count(rs.getString("mat_count"));
					dto.setMat_no(rs.getString("mat_no"));
					
					list.add(dto);
				}
				
				return list;
				
			} catch (Exception e) {
				System.out.println("getReview() : " + e);
			}
			finally{
				pool.freeConnection(con, stmt, rs);
			}
			
			return list;
		}
}
