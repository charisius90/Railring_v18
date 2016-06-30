package review.model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbcp.Command;

public class ReviewDeleteCommand implements Command{

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ReviewDao dao = new ReviewDao();
		
		System.out.println("삭제커맨드왔고 번호는 : " + req.getParameter("review_no"));
		dao.deleteReview(Integer.parseInt(req.getParameter("review_no")));
		
		return "review.action?cmd=REVIEW";
	}
}
