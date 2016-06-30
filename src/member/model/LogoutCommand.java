package member.model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbcp.Command;

public class LogoutCommand implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		MemberDto member = (MemberDto)session.getAttribute("member");
		if(member != null){
			session.removeAttribute("member");
		}
		
		return "/mainPage.jsp";
	}
}
