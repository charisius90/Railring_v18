package member.model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbcp.Command;

public class ConfirmCommand implements Command{
	
	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String mem_email = req.getParameter("inputEmail");
		MemberDao member = new MemberDao();
		
		// 가입 성공 url
		String url = "/WEB-INF/views/memberPage/memberCompletePage.jsp";
		
		if(member.isMember(mem_email)){
			// 이미 가입한 회원일 경우 url
			url = "/WEB-INF/views/memberPage/memberRegisterPage.jsp?error=user";
		}
		else{
			String mem_name = req.getParameter("inputName");
			String mem_pw = req.getParameter("inputPassword");
			System.out.println("ConfirmCommand inputName 체크 : " + mem_name);
			MemberDto dto = new MemberDto();
			dto.setMem_email(mem_email);
			dto.setMem_name(mem_name);
			dto.setMem_pw(mem_pw);
			
			boolean flag = member.insertMember(dto);
			if(!flag){
				// db 입력 오류 발생시 url
				url = "/WEB-INF/views/memberPage/memberRegisterPage.jsp?error=insert";
			}
		}
		
		return url;
	}
	
}
