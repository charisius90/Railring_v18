package mate.model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbcp.Command;

//by 손승한, 강병현
public class MateReadCommand implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("리드커맨드옴");
		MateDao dao = new MateDao();
		
		// 규채 : 조회수 증가
		req.setAttribute("mate", dao.getMate(Integer.parseInt(req.getParameter("mate_no")), true));
		System.out.println("이제 리드jsp로");
		return "/WEB-INF/views/matePage/mateReadPage.jsp";
	}

	
}
