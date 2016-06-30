package member.model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbcp.Command;

public class MyPageCommand implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		MemberDto dto = (MemberDto)session.getAttribute("member");
		
		String url = "/WEB-INF/views/myPage/myPage.jsp";
		
		if(dto == null){
			req.setAttribute("error", "guest");
			url = "/WEB-INF/views/memberPage/memberLoginPage.jsp";
		}
		
		return url;
	}
}
