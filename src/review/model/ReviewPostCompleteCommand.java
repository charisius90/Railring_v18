package review.model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbcp.Command;
import member.model.MemberDto;

public class ReviewPostCompleteCommand implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession();
		MemberDto member = (MemberDto) session.getAttribute("member");
		
		ReviewDto dto = new ReviewDto();
		dto.setRev_subject(req.getParameter("subject"));
		dto.setRev_content(req.getParameter("content"));
		dto.setMem_no(member.getMem_no());
		dto.setSch_no(req.getParameter("scheduleList"));
		
		ReviewDao dao = new ReviewDao();
		dao.insertReview(dto);
		
		return "review.action?cmd=REVIEW" ;
	}

	
}
