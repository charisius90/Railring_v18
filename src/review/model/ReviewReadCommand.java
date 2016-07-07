package review.model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbcp.Command;

//by 손승한, 강병현
public class ReviewReadCommand implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ReviewDao dao = new ReviewDao();
		
		// 규채 : 조회수 증가
		req.setAttribute("review", dao.getReview(Integer.parseInt(req.getParameter("review_no")), true));
		return "/WEB-INF/views/reviewPage/reviewReadPage.jsp";
	}

	
}
