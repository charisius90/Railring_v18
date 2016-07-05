package member.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dbcp.DBConnectionMgr;

/**
 * 
 * @author 수항
 *
 */
public class MemberDao {
	private DBConnectionMgr pool;
	private Connection con;
	private PreparedStatement stmt;
	private ResultSet rs;
	
	public MemberDao(){
		this.pool = DBConnectionMgr.getInstance();
	}
	
	/**
	 * 입력한 이메일과 비밀번호의 DB 일치여부 반환 by 수항
	 * @param email
	 * @param pw
	 * @return
	 */
	public boolean isCollectPw(String email, String pw){
		boolean result = false;
		try {
			String sql = "select mem_pw from member where mem_email like '" + email + "'";
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()){
				String mem_pw = rs.getString("mem_pw");
				if(mem_pw.equals(pw)){
					result = true;
				}
			}
		}
		catch (Exception err) {
			System.out.println("MemberDao.isCollectPw() : " + err);
			return result;
		}
		finally{
			pool.freeConnection(con, stmt, rs);
		}
		return result;
	}
	
	/**
	 * 이메일 주소로 회원번호 가져오기. by 소영
	 * @param email
	 * @return
	 */
	public String getMemberNo(String email){
		try{
			String sql = "select mem_no from member where mem_email like '" + email + "'";
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if(rs.next()){
				String mem_no = rs.getString("mem_no");
				return mem_no;
			}
		}
		catch(Exception err){
			System.out.println("MemberDao.getMemberNo() : " + err);
			return null;
		}
		finally{
			pool.freeConnection(con, stmt, rs);
		}
		return null;
	}
	
	/**
	 * 이메일 주소로 회원 정보 가져오기  by 수항
	 * @param mem_email
	 * @return
	 */
	public MemberDto getMemberInfo(String mem_email){
		MemberDto dto = null;
		try{
			String sql = "select * from member where mem_email like '" + mem_email + "'";
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if(rs.next()){
				String no = rs.getString("mem_no");
				String name = rs.getString("mem_name");
				String email = rs.getString("mem_email");
				String gender = rs.getString("mem_gender");
				String pw = rs.getString("mem_pw");
				String birth = rs.getString("mem_birth");
				String date = rs.getString("mem_reg_date");
				String image = rs.getString("mem_image");
				String background = rs.getString("mem_background");
				
				dto = new MemberDto();
				
				if(birth != null){
					birth = birth.split(" ")[0];
				}
				
				dto.setMem_no(no);
				dto.setMem_name(name);
				dto.setMem_email(email);
				dto.setMem_gender(gender);
				dto.setMem_pw(pw);
				dto.setMem_birth(birth);
				dto.setMem_date(date);
				dto.setMem_image(image);
				dto.setMem_background(background);
				return dto;
			}
		}
		catch(Exception err){
			System.out.println("MemberDao.getMemberInfo() : " + err);
			return null;
		}
		finally{
			pool.freeConnection(con, stmt, rs);
		}
		return null;
	}
	
	/**
	 * 입력된 이메일의 회원여부 반환  by 수항
	 * @param email
	 * @return
	 */
	public boolean isMember(String email){
		boolean result = false;
		try {
			String sql = "select mem_email from member where mem_email like '" + email + "'";
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()){
				result = true;
			}
		}
		catch (Exception err) {
			System.out.println("MemberDao.isMember() : " + err);
			return result;
		}
		finally{
			pool.freeConnection(con, stmt, rs);
		}
		return result;
	}
	
	/**
	 * 회원추가  by 수항
	 * @param dto
	 * @return
	 */
	public boolean insertMember(MemberDto dto){
		boolean result = false;
		try {
			String sql = "insert into member(mem_no, mem_email, mem_pw, mem_name, mem_reg_date)"
					+ " values(seq_mem_no.nextVal, ?, ?, ?, sysdate)";
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, dto.getMem_email());
			stmt.setString(2, dto.getMem_pw());
			stmt.setString(3, dto.getMem_name());

			stmt.executeUpdate();
			
			result = true;
		}
		catch (Exception err) {
			System.out.println("MemberDao.insertMember() : " + err);
			return result;
		}
		finally{
			pool.freeConnection(con, stmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 회원 정보 수정  by 수항
	 * @param dto
	 * @return
	 */
	public boolean updateMember(MemberDto dto){
		boolean result = false;
		try {
			String sql = "update member set mem_name=?, mem_gender=?, mem_birth=?, mem_image=?, mem_background=? where mem_no=" + dto.getMem_no();
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, dto.getMem_name());
			stmt.setString(2, dto.getMem_gender());
			stmt.setString(3, dto.getMem_birth());
			stmt.setString(4, dto.getMem_image());
			stmt.setString(5, dto.getMem_background());
			
			stmt.executeUpdate();
			
			result = true;
		}
		catch (Exception err) {
			System.out.println("MemberDao.updateMember() : " + err);
			return result;
		}
		finally{
			pool.freeConnection(con, stmt, rs);
		}
		return result;
	}
	
	/**
	 * 회원 비밀번호 변경
	 * @param email
	 * @param newPw
	 * @return
	 */
	public boolean changePw(String email, String newPw){
		boolean result = false;
		try {
			String sql = "update member set mem_pw=? where mem_email like ?";
			con = pool.getConnection();
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, newPw);
			stmt.setString(2, email);
			stmt.executeUpdate();
			
			result = true;
		}
		catch (Exception err) {
			System.out.println("MemberDao.changePw() : " + err);
			return result;
		}
		finally{
			pool.freeConnection(con, stmt, rs);
		}
		return result;
	}
	
	// 회원삭제 - 구현X
	public void deleteMemeber(){
		
	}
	
	
}
