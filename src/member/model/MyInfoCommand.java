package member.model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbcp.Command;

/**
 * 회원정보 수정 커맨드
 * @author 수항
 *
 */
public class MyInfoCommand implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String inputName = req.getParameter("inputName");
		String inputGender = req.getParameter("inputGender");
		String inputBirth = req.getParameter("inputBirth");
		
		HttpSession session = req.getSession();
		MemberDto dto = (MemberDto)session.getAttribute("member");
		MemberDao dao = new MemberDao();
		
		if(inputName.length()>6 || inputName.length()<1){
			req.setAttribute("error", "1자 이상 6자 이하만 가능합니다.");
		}
		else{
			dto.setMem_name(inputName);
			dto.setMem_gender(inputGender);
			dto.setMem_birth(inputBirth);
			
			System.out.println("gender : " + inputGender);
			if(dao.updateMember(dto)){
				//업데이트 성공
				req.setAttribute("dto", dto);
			}
			else{
				req.setAttribute("error", "업데이트 실패, 서비스 담당자에게 문의하세요.");
			}
			
			session.setAttribute("member", dto);
		}
		
		return "member.action?cmd=MYPAGE";
	}
}
