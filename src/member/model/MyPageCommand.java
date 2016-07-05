package member.model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbcp.Command;

/**
 * 로그인 된 경우만 마이페이지로 이동해주는 커맨드
 * @author 수항
 *
 */
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
