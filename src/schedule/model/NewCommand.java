package schedule.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbcp.Command;
import member.model.MemberDto;
import utils.Paging;

public class NewCommand implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("뉴커맨드");
		HttpSession session = req.getSession();
		MemberDto member = (MemberDto) session.getAttribute("member");
		if(member == null) {
			req.setAttribute("result", false);
			req.setAttribute("message", "로그인 해주세요.");
			return "/WEB-INF/views/schedulePage/json/schedulePostResult.jsp"; 
		}
		String mem_no = member.getMem_no();
		
		ScheduleDao dao = new ScheduleDao();
		String sch_no = dao.newSchedule(mem_no);
		
		req.setAttribute("sch_no", sch_no);
		System.out.println(req.getAttribute("sch_no"));
		
		return "/WEB-INF/views/schedulePage/json/scheduleNewResult.jsp";
	}

}
