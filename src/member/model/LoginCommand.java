package member.model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbcp.Command;

public class LoginCommand implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String email = req.getParameter("inputEmail");
		String pw = req.getParameter("inputPassword");
		String url = "/mainPage.jsp";
		
		MemberDao dao = new MemberDao();
		if(dao.isCollectPw(email, pw)){
			HttpSession session = req.getSession();
			session.setAttribute("member", dao.getMemberInfo(email));
		}
		else{
			if(!dao.isMember(email)){
				req.setAttribute("error", "email");
			}
			else{
				req.setAttribute("error", "pw");
			}
			req.setAttribute("inputEmail", email);
			req.setAttribute("inputPassword", pw);
			url = "/WEB-INF/views/memberPage/memberLoginPage.jsp";
		}
		
		return url;	
	}
}
