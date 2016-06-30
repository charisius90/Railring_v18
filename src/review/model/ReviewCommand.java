package review.model;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbcp.Command;
import utils.Paging2;

public class ReviewCommand implements Command {

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
	
		ReviewDao dao = new ReviewDao();
		Paging2<ReviewDto> paging = dao.getReviewPagingList(page, searchOption, keyword);
		req.setAttribute("paging", paging);
		System.out.println("¸®ºäÄ¿¸Çµå");
		return "/WEB-INF/views/reviewPage/reviewMainPage.jsp";
	}

}
