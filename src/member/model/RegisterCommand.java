package member.model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbcp.Command;

/**
 * 회원가입 커맨드
 * @author 수항
 *
 */
public class RegisterCommand implements Command{
	
	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String mem_email = req.getParameter("inputEmail");
		MemberDao member = new MemberDao();
		boolean check = true; // 가입성공 여부
		MemberDto dto = null;
		// 가입 성공 url
		String url = "front.do?cmd=main";
		
		if(member.isMember(mem_email)){
			// 이미 가입한 회원일 경우 url
			check = false;
			url = "/WEB-INF/views/memberPage/memberRegisterPage.jsp?error=user";
		}
		else{
			String mem_name = req.getParameter("inputName");
			String mem_pw = req.getParameter("inputPassword");
			dto = new MemberDto();
			dto.setMem_email(mem_email);
			dto.setMem_name(mem_name);
			dto.setMem_pw(mem_pw);
			
			boolean flag = member.insertMember(dto);
			if(!flag){
				// db 입력 오류 발생시 url
				check = false;
				url = "/WEB-INF/views/memberPage/memberRegisterPage.jsp?error=insert";
			}
		}
		
		if(check){
			HttpSession session = req.getSession();
			session.setAttribute("member", member.getMemberInfo(mem_email));
		}
		
		return url;
	}
	
}
