package mate.model;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbcp.Command;
import utils.Paging;

public class MateCommand implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String pageStr = req.getParameter("page");
		String keyword = req.getParameter("keyword");
		String searchOption = req.getParameter("searchOption");

		int page;
		if (pageStr == null || "".equals(pageStr.trim())) {
			page = 1;
		} else {
			page = Integer.parseInt(req.getParameter("page"));
		}
	
		MateDao dao = new MateDao();
		Paging<MateDto> paging = dao.getMatePagingList(page, searchOption, keyword);
		req.setAttribute("paging", paging);
		System.out.println("메이트커맨드");
		return "/WEB-INF/views/matePage/mateMainPage.jsp";
	}

}
