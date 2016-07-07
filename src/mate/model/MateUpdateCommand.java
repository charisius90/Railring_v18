package mate.model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbcp.Command;

//by 손승한, 강병현
public class MateUpdateCommand implements Command{

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		MateDao dao = new MateDao();

		req.setAttribute("mate", dao.getMate(Integer.parseInt(req.getParameter("mate_no")), false));
		
		return "/WEB-INF/views/matePage/mateUpdatePage.jsp";
	}

	
}
