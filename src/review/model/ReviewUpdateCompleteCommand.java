package review.model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbcp.Command;

public class ReviewUpdateCompleteCommand implements Command{

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("리뷰업데이트컴플릿커맨드");
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		
		ReviewDto dto = new ReviewDto();
		dto.setRev_no(req.getParameter("review_no"));
		dto.setRev_subject(req.getParameter("subject"));
		dto.setRev_content(req.getParameter("content"));
		
		System.out.println(dto.getRev_subject());
		System.out.println(dto.getRev_content());
		System.out.println(dto.getRev_no());
		System.out.println("이제 dao함수로");
		ReviewDao dao = new ReviewDao();
		dao.updateReview(dto);
		
		return "review.action?cmd=READ&review_no="+dto.getRev_no();
	}

	
}
