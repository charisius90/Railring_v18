package review.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import dbcp.DBConnectionMgr;
import utils.Paging2;

//by 손승한, 강병현
public class ReviewDao {

	private Connection con;
	private PreparedStatement stmt;
	private ResultSet rs;
	private DBConnectionMgr pool;

	public ReviewDao() {
		try {
			pool = DBConnectionMgr.getInstance();
		} catch (Exception err) {
			System.out.println("ReviewDao" + err);
		}
	}

	public void insertReview(ReviewDto review) {

		String sql = "insert into review(rev_no, rev_subject, rev_content,rev_date, rev_count, mem_no, sch_no)"
				+ "values(seq_review_no.nextVal,?,?,sysdate,0,?,?)";

		try {
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);

			stmt.setString(1, review.getRev_subject());
			stmt.setString(2, review.getRev_content());
			stmt.setString(3, review.getMem_no());
			stmt.setString(4, review.getSch_no());

			stmt.executeUpdate();
		} catch (Exception err) {
			System.out.println("insertReview()" + err);
		} finally {
			pool.freeConnection(con, stmt);
		}
	}

	public void deleteReview(int num) {
		System.out.println("삭제메서드옴");
		try {
			String sql = "delete from review where rev_no=" + num;

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
	public ReviewDto getReview(int num, boolean isRead) {
		System.out.println("이제 열 글 번호: " + num);
		String sql = "select rev.*, mem.mem_name from  review rev, member mem where rev.mem_no = mem.mem_no and rev.rev_no =" + num;
		ReviewDto dto = new ReviewDto();
		String sql2 = "update review set rev_count=rev_count+1 where rev_no=" + num;
		try {
			con = pool.getConnection();
			
			 if(isRead == true){
		            stmt = con.prepareStatement(sql2);
		            stmt.executeUpdate();
		     }
			
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();

			if (rs.next()) {
				dto.setRev_no(rs.getString("rev_no"));
				dto.setSch_no(rs.getString("sch_no"));
				dto.setRev_subject(rs.getString("rev_subject"));
				dto.setRev_content(rs.getString("rev_content"));
				dto.setRev_date(rs.getString("rev_date"));
				dto.setMem_no(rs.getString("mem_no"));
				dto.setRev_count(rs.getString("rev_count"));
				dto.setMem_name(rs.getString("mem_name"));
			}
		} catch (Exception err) {
			System.out.println("getReview : " + err);
		} finally {
			pool.freeConnection(con, stmt, rs);
		}
		return dto;
	}

	public void updateReview(ReviewDto dto) {
		try {
			String sql = "update review set rev_subject=?, rev_content=? where rev_no=?";

			con = pool.getConnection();
			stmt = con.prepareStatement(sql);

			stmt.setString(1, dto.getRev_subject());
			stmt.setString(2, dto.getRev_content());
			stmt.setString(3, dto.getRev_no());

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
	public Paging2<ReviewDto> getReviewPagingList(int page, String searchOption, String keyword) {
		Paging2<ReviewDto> paging = new Paging2<ReviewDto>();
		paging.setCurrentPage(page);
		paging.setSearchKeyword(keyword);
		paging.setSearchOption(searchOption);
		paging.setList(new ArrayList());

		String searchSql = "";
		if (keyword != null && !keyword.isEmpty()) {
			if ("ALL".equals(searchOption)) {
				searchSql += " and rev.rev_subject like '%" + keyword + "%' "
						+ "and rev.rev_content like '%" + keyword + "%'";
			} else if ("SUBJECT".equals(searchOption)) {
				searchSql += " and rev.rev_subject like '%" + keyword + "%'";
			} else if ("MEMNO".equals(searchOption)) {
				searchSql += " and mem.mem_name like '%" + keyword + "%'";
			}
		}

		String totalCountSql = "select count(*) total from review rev, member mem "
				+ "where rev.mem_no = mem.mem_no" + searchSql;
		String pagingSql = "select * from"
				+ "(select rev.*, mem.mem_name, ROW_NUMBER() OVER (ORDER BY rev_no desc) rnum "
				+ "from review rev, member mem "
				+ "where rev.mem_no = mem.mem_no"
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
		
			List<ReviewDto> list = new ArrayList<ReviewDto>();
			while (rs.next()) {
				ReviewDto dto = new ReviewDto();
				dto.setRev_no(rs.getString("rev_no"));
				dto.setSch_no(rs.getString("sch_no"));
				dto.setRev_subject(rs.getString("rev_subject"));
				dto.setRev_content(rs.getString("rev_content"));
				dto.setRev_date(rs.getString("rev_date"));
				dto.setMem_no(rs.getString("mem_no"));
				dto.setRev_count(rs.getString("rev_count"));
				dto.setMem_name(rs.getString("mem_name"));

				list.add(dto);
			}
			paging.setList(list);

		} catch (Exception err) {
			System.out.println("getReviewList : " + err + " : " + pagingSql);
		} finally {
			pool.freeConnection(con, stmt, rs);
		}
		System.out.println(paging.getList().size());
		System.out.println(paging);
		return paging;
	}
	
//	안씀
	public Vector<ReviewDto> getSearchReviewList(String searchOption, String keyword) {
		Vector<ReviewDto> vList = new Vector<ReviewDto>();

		String sql = "select * from review";
		if (keyword.equals("") || keyword.isEmpty()) {
			sql += " order by rev_no desc";
		} else {
			if ("ALL".equals(searchOption)) {
				sql += " where rev_subject like '%" + keyword + "%' and rev_content like '%" + keyword + "%'";
			} else if ("SUBJECT".equals(searchOption)) {
				sql += " where rev_subject like '%" + keyword + "%'";
			} else if ("MEMNO".equals(searchOption)) {
				sql += " where mem_no = " + keyword;
			}
			sql += " order by rev_no desc";
		}
		System.out.println(sql);
		try {
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next()) {
				ReviewDto dto = new ReviewDto();
				dto.setRev_no(rs.getString("rev_no"));
				dto.setSch_no(rs.getString("sch_no"));
				dto.setRev_subject(rs.getString("rev_subject"));
				dto.setRev_content(rs.getString("rev_content"));
				dto.setRev_date(rs.getString("rev_date"));
				dto.setMem_no(rs.getString("mem_no"));
				dto.setRev_count(rs.getString("rev_count"));

				vList.add(dto);
			}
		} catch (Exception err) {
			System.out.println("getSearchReviewList : " + err);
		} finally {
			pool.freeConnection(con, stmt, rs);
		}
		System.out.println(vList.size());
		return vList;
	}
	
	// 규채 : 마이페이지에서 게시판에 자신이 쓴 글 가져오기
	public ArrayList getReview(String mem_no){
		String sql = "select * from review where mem_no = ? ";
		
		ArrayList<ReviewDto> list = new ArrayList<ReviewDto>();
		
		try {
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, mem_no);
			rs = stmt.executeQuery();	
			
			while(rs.next()){
				ReviewDto dto = new ReviewDto();
				dto.setMem_no(rs.getString("mem_no"));
				dto.setRev_subject(rs.getString("Rev_subject"));
				dto.setRev_date(rs.getString("Rev_date"));
				dto.setRev_count(rs.getString("Rev_count"));
				dto.setRev_no(rs.getString("Rev_no"));
				
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
