package matereply.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import dbcp.DBConnectionMgr;

//by ¼Õ½ÂÇÑ, °­º´Çö
public class MateReplyDao {
	private Connection con;
	private PreparedStatement stmt;
	private ResultSet rs;
	private DBConnectionMgr pool;
	
	public MateReplyDao(){
		this.pool = DBConnectionMgr.getInstance();
	}
	
	public void insertMateReply(MateReplyDto dto){
		System.out.println("insertMateReplyÇÔ¼ö ¿È");
		
		String sql="insert into mate_reply(mat_re_no,mat_re_content,mat_re_date,mat_re_pos,mat_re_depth,mat_no,mem_no,mat_group)"
				+ "values(seq_mate_reply_no.nextVal,?,sysdate,?,?,?,?,seq_mate_group.nextVal)";
		
		try{	
//			´ñ±Û µî·Ï
			con=pool.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, dto.getMat_re_content());
			stmt.setInt(2, 0);
			stmt.setInt(3, 0);
			stmt.setString(4, dto.getMat_no());
			stmt.setString(5, dto.getMem_no());
			
			stmt.executeQuery();
		}
		catch(Exception err){
			System.out.println("insertMateReply()"+err);
		}
		finally{
			pool.freeConnection(con,stmt);
		}
		System.out.println("ºÎ¸ð´ñ±Û ¹øÈ£: "+dto.getMat_re_no());
		System.out.println("ºÎ¸ð´ñ±Û ³»¿ë: "+dto.getMat_re_content());
		System.out.println("ºÎ¸ð´ñ±Û ±×·ì: "+dto.getMat_group());
		System.out.println("ºÎ¸ð´ñ±Û pos: "+dto.getMat_re_pos());
		System.out.println("ºÎ¸ð´ñ±Û depth: "+dto.getMat_re_depth());
	}
	
	public void insertMateReReply(MateReplyDto dto, String parent_no){
		System.out.println("´ë´ñ±Û µî·ÏÇÔ¼ö");
		List<String> list = getParentInfo(parent_no);
		int parent_pos = Integer.parseInt(list.get(0));
		int parent_depth = Integer.parseInt(list.get(1));
		String parent_group = list.get(2);
		
//		if(parent_depth == 0){
			updatePos(parent_pos, parent_group);
//		}
		
		String sql="insert into mate_reply(mat_re_no,mat_re_content,mat_re_date,mat_re_pos,mat_re_depth,mat_no,mem_no,mat_group)"
				+ "values(seq_mate_reply_no.nextVal,?,sysdate,?,?,?,?,?)";
		

		try{
			con=pool.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, dto.getMat_re_content());
			stmt.setInt(2, parent_pos+1);
			stmt.setInt(3, parent_depth+1);
			stmt.setString(4, dto.getMat_no());
			stmt.setString(5, dto.getMem_no());
			stmt.setString(6, parent_group);
			
			stmt.executeQuery();
		}
		catch(Exception err){
			System.out.println("insertMateReply()"+err);
		}
		finally{
			pool.freeConnection(con,stmt);
		}
		System.out.println("ºÎ¸ð´ñ±Û ¹øÈ£: "+parent_no);
		System.out.println("ºÎ¸ð´ñ±Û ±×·ì: "+parent_group);
		System.out.println("ºÎ¸ð´ñ±Û pos: "+parent_pos);
		System.out.println("ºÎ¸ð´ñ±Û depth: "+parent_depth);
		System.out.println("ÀÚ½Ä´ñ±Û ¹øÈ£: "+dto.getMat_re_no());
		System.out.println("ÀÚ½Ä´ñ±Û ³»¿ë: "+dto.getMat_re_content());
		System.out.println("ÀÚ½Ä´ñ±Û ±×·ì: "+dto.getMat_group());
		System.out.println("ÀÚ½Ä´ñ±Û pos: "+dto.getMat_re_pos());
		System.out.println("ÀÚ½Ä´ñ±Û depth: "+dto.getMat_re_depth());
	}
	
	public List<String> getParentInfo(String parent_no){
		List<String> list = new ArrayList<String> ();
		String sql= "select mat_re_pos, mat_re_depth, mat_group from mate_reply where mat_re_no="+parent_no;
		
		try {
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				list.add(rs.getString("mat_re_pos"));
				list.add(rs.getString("mat_re_depth"));
				list.add(rs.getString("mat_group"));
			}
		} catch (Exception e) {
			System.out.println("getMateReplyList:"+e);
		}finally {
			pool.freeConnection(con, stmt, rs);
		}
		return list;
	}
	
	public void updatePos(int parent_pos, String mat_group){
		String sql = "update mate_reply set mat_re_pos=mat_re_pos+1 where mat_re_pos>"+parent_pos+" and mat_group="+mat_group;
		
		try{
			con=pool.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.executeUpdate();
		}catch(Exception err){
			System.out.println("db ºñ¾úÀ½");
		}
	}
	
	public void updateMateReply(MateReplyDto dto){
		try{
			String sql = "update mate_reply set mat_re_content=?, mat_re_date=sysdate where mat_re_no=?";
			
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, dto.getMat_re_content());
			stmt.setString(2, dto.getMat_re_no());
			
			
			stmt.executeUpdate();
		}
		catch(Exception err){
			System.out.println("updateMateReply : " + err);
		}
		finally{
			pool.freeConnection(con, stmt);
		}
		
	}
	
	public void deleteMateReply(int num){
		
		try{
			String sql = "delete from mate_reply where mat_re_no=" + num;
			
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
		
			stmt.executeUpdate();
		}
		catch(Exception err){
			System.out.println("deleteMateReply : " + err);
		}
		finally{
			pool.freeConnection(con, stmt);
		}
	}
	
	
	public List<MateReplyDto> getMateReplyList(String mat_no){
		List<MateReplyDto> list = new ArrayList<MateReplyDto> ();
		
		String sql= "select mat_re.*, mem.mem_name from mate_reply mat_re, member mem "
				+ "where mat_re.mat_no="+mat_no+" and mat_re.mem_no = mem.mem_no "
						+ "order by mat_re.mat_group desc, mat_re.mat_re_pos desc";
		try {
			
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				MateReplyDto dto = new MateReplyDto();
				dto.setMat_re_content(rs.getString("mat_re_content"));
				dto.setMat_re_date(rs.getString("mat_re_date"));
				dto.setMat_re_pos(rs.getString("mat_re_pos"));
				dto.setMat_re_depth(rs.getString("mat_re_depth"));
				dto.setMat_no(rs.getString("mat_no"));
				dto.setMat_re_no(rs.getString("mat_re_no"));
				dto.setMem_no(rs.getString("mem_no"));
				dto.setMat_group(rs.getString("mat_group"));
				dto.setMem_name(rs.getString("mem_name"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			System.out.println("getMateReplyList:"+e);
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
	      String sql = "select * from mate_reply where mat_no="+num;
	      
	      try{
	         
	         con = pool.getConnection();
	         stmt = con.prepareStatement(sql);
	         rs = stmt.executeQuery();
	         
	         MateReplyDto dto = new MateReplyDto();
	         
	         int result = 0;
	         
	         while(rs.next()){
	            rs.getString("mat_no");
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
