package mate.model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbcp.Command;
import member.model.MemberDto;

//by 손승한, 강병현
public class MatePostCompleteCommand implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession();
		MemberDto member = (MemberDto) session.getAttribute("member");
		
		MateDto dto = new MateDto();
		dto.setMat_subject(req.getParameter("subject"));
		dto.setMat_content(req.getParameter("content"));
		dto.setMem_no(member.getMem_no());
		dto.setSch_no(req.getParameter("scheduleList"));
		
		MateDao dao = new MateDao();
		dao.insertMate(dto);
		
		return "mate.action?cmd=MATE" ;
	}

	
}
