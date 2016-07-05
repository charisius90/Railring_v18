package member.model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbcp.Command;

/**
 * 
 * @author 수항
 *
 */
public class LoginCommand implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String email = req.getParameter("inputEmail");
		String pw = req.getParameter("inputPassword");
		String url = "/mainPage.jsp";
		
		MemberDao dao = new MemberDao();
		if(dao.isCollectPw(email, pw)){
			// 입력된 이메일과 비밀번호가 DB와 일치하는 경우 session에 해당 회원정보 저장
			HttpSession session = req.getSession();
			session.setAttribute("member", dao.getMemberInfo(email));
		}
		else{
			// 입력값에 문제가 있는 경우
			if(!dao.isMember(email)){
				// 입력 이메일이 DB에 존재하지 않는 경우
				req.setAttribute("error", "email");
			}
			else{
				// 입력 비밀번호가 잘못된 경우
				req.setAttribute("error", "pw");
			}
			// 지난 입력값 request로 돌려줌
			req.setAttribute("inputEmail", email);
			req.setAttribute("inputPassword", pw);
			url = "/WEB-INF/views/memberPage/memberLoginPage.jsp";
		}
		
		return url;	
	}
}
