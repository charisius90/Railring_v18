package reviewreply.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import dbcp.DBConnectionMgr;

public class ReviewReplyDao {
	private Connection con;
	private PreparedStatement stmt;
	private ResultSet rs;
	private DBConnectionMgr pool;
	
	public ReviewReplyDao(){
		this.pool = DBConnectionMgr.getInstance();
	}
	
	public void insertReviewReply(ReviewReplyDto dto){
		System.out.println("insertReviewReplyÇÔ¼ö ¿È");
		
		String sql="insert into review_reply(rev_re_no,rev_re_content,rev_re_date,rev_re_pos,rev_re_depth,rev_no,mem_no,rev_group)"
				+ "values(seq_review_reply_no.nextVal,?,sysdate,?,?,?,?,seq_review_group.nextVal)";
		
		try{	
//			´ñ±Û µî·Ï
			con=pool.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, dto.getRev_re_content());
			stmt.setInt(2, 0);
			stmt.setInt(3, 0);
			stmt.setString(4, dto.getRev_no());
			stmt.setString(5, dto.getMem_no());
			
			stmt.executeQuery();
		}
		catch(Exception err){
			System.out.println("insertReviewReply()"+err);
		}
		finally{
			pool.freeConnection(con,stmt);
		}
		System.out.println("ºÎ¸ð´ñ±Û ¹øÈ£: "+dto.getRev_re_no());
		System.out.println("ºÎ¸ð´ñ±Û ³»¿ë: "+dto.getRev_re_content());
		System.out.println("ºÎ¸ð´ñ±Û ±×·ì: "+dto.getRev_group());
		System.out.println("ºÎ¸ð´ñ±Û pos: "+dto.getRev_re_pos());
		System.out.println("ºÎ¸ð´ñ±Û depth: "+dto.getRev_re_depth());
	}
	
	public void insertReviewReReply(ReviewReplyDto dto, String parent_no){
		System.out.println("´ë´ñ±Û µî·ÏÇÔ¼ö");
		List<String> list = getParentInfo(parent_no);
		int parent_pos = Integer.parseInt(list.get(0));
		int parent_depth = Integer.parseInt(list.get(1));
		String parent_group = list.get(2);
		
//		if(parent_depth == 0){
			updatePos(parent_pos, parent_group);
//		}
		
		String sql="insert into review_reply(rev_re_no,rev_re_content,rev_re_date,rev_re_pos,rev_re_depth,rev_no,mem_no,rev_group)"
				+ "values(seq_review_reply_no.nextVal,?,sysdate,?,?,?,?,?)";
		

		try{
			con=pool.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, dto.getRev_re_content());
			stmt.setInt(2, parent_pos+1);
			stmt.setInt(3, parent_depth+1);
			stmt.setString(4, dto.getRev_no());
			stmt.setString(5, dto.getMem_no());
			stmt.setString(6, parent_group);
			
			stmt.executeQuery();
		}
		catch(Exception err){
			System.out.println("insertReviewReply()"+err);
		}
		finally{
			pool.freeConnection(con,stmt);
		}
		System.out.println("ºÎ¸ð´ñ±Û ¹øÈ£: "+parent_no);
		System.out.println("ºÎ¸ð´ñ±Û ±×·ì: "+parent_group);
		System.out.println("ºÎ¸ð´ñ±Û pos: "+parent_pos);
		System.out.println("ºÎ¸ð´ñ±Û depth: "+parent_depth);
		System.out.println("ÀÚ½Ä´ñ±Û ¹øÈ£: "+dto.getRev_re_no());
		System.out.println("ÀÚ½Ä´ñ±Û ³»¿ë: "+dto.getRev_re_content());
		System.out.println("ÀÚ½Ä´ñ±Û ±×·ì: "+dto.getRev_group());
		System.out.println("ÀÚ½Ä´ñ±Û pos: "+dto.getRev_re_pos());
		System.out.println("ÀÚ½Ä´ñ±Û depth: "+dto.getRev_re_depth());
	}
	
	public List<String> getParentInfo(String parent_no){
		List<String> list = new ArrayList<String> ();
		String sql= "select rev_re_pos, rev_re_depth, rev_group from review_reply where rev_re_no="+parent_no;
		
		try {
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				list.add(rs.getString("rev_re_pos"));
				list.add(rs.getString("rev_re_depth"));
				list.add(rs.getString("rev_group"));
			}
		} catch (Exception e) {
			System.out.println("getReviewReplyList:"+e);
		}finally {
			pool.freeConnection(con, stmt, rs);
		}
		return list;
	}
	
	public void updatePos(int parent_pos, String rev_group){
		String sql = "update review_reply set rev_re_pos=rev_re_pos+1 where rev_re_pos>"+parent_pos+" and rev_group="+rev_group;
		
		try{
			con=pool.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.executeUpdate();
		}catch(Exception err){
			System.out.println("db ºñ¾úÀ½");
		}
	}
	
	public void updateReviewReply(ReviewReplyDto dto){
		try{
			String sql = "update review_reply set rev_re_content=?, rev_re_date=sysdate where rev_re_no=?";
			
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, dto.getRev_re_content());
			stmt.setString(2, dto.getRev_re_no());
			
			
			stmt.executeUpdate();
		}
		catch(Exception err){
			System.out.println("updateReviewReply : " + err);
		}
		finally{
			pool.freeConnection(con, stmt);
		}
		
	}
	
	public void deleteReviewReply(int num){
		
		try{
			String sql = "delete from review_reply where rev_re_no=" + num;
			
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
		
			stmt.executeUpdate();
		}
		catch(Exception err){
			System.out.println("deleteReviewReply : " + err);
		}
		finally{
			pool.freeConnection(con, stmt);
		}
	}
	
	
	public List<ReviewReplyDto> getReviewReplyList(String rev_no){
		List<ReviewReplyDto> list = new ArrayList<ReviewReplyDto> ();
		
		String sql= "select rev_re.*, mem.mem_name from review_reply rev_re, member mem "
				+ "where rev_re.rev_no="+rev_no+" and rev_re.mem_no = mem.mem_no "
						+ "order by rev_re.rev_group desc, rev_re.rev_re_pos desc";
		try {
			
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				ReviewReplyDto dto = new ReviewReplyDto();
				dto.setRev_re_content(rs.getString("rev_re_content"));
				dto.setRev_re_date(rs.getString("rev_re_date"));
				dto.setRev_re_pos(rs.getString("rev_re_pos"));
				dto.setRev_re_depth(rs.getString("rev_re_depth"));
				dto.setRev_no(rs.getString("rev_no"));
				dto.setRev_re_no(rs.getString("rev_re_no"));
				dto.setMem_no(rs.getString("mem_no"));
				dto.setRev_group(rs.getString("rev_group"));
				dto.setMem_name(rs.getString("mem_name"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			System.out.println("getReviewReplyList:"+e);
		}finally {
			pool.freeConnection(con, stmt, rs);
		}
		
		return list;
		
	}
	
	public String setDepth(int depth){
		System.out.println("µé¿©¾²±â");
		String result = "";
		for(int i=0; i<depth*3; i++){
			result += "&nbsp;";
		}
		return result;
	}
	
	//´ñ±Û °¹¼ö
	public int getSize(int num){
	      String sql = "select * from review_reply where rev_no="+num;
	      
	      try{
	         
	         con = pool.getConnection();
	         stmt = con.prepareStatement(sql);
	         rs = stmt.executeQuery();
	         
	         ReviewReplyDto dto = new ReviewReplyDto();
	         
	         int result = 0;
	         
	         while(rs.next()){
	            rs.getString("rev_no");
	            result++;
	         }
	         
	         return result;
	      }
	      catch(Exception err){
	         System.out.println("getSize :" + err);
	      }
	      finally {
	         pool.freeConnection(con, stmt, rs);
	      }
	      
	      return 0;
	   }
}
