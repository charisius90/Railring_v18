package schedule.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbcp.Command;
import member.model.MemberDto;
import utils.Paging;

public class LoadCommand implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("LOADÄ¿¸Çµå");
		
		ScheduleDao dao = new ScheduleDao();
		String sch_no = req.getParameter("sch_no");
		System.out.println(sch_no);
		ScheduleDto scheduleDto = dao.getSchedule(sch_no);
		System.out.println(scheduleDto);
		
		req.setAttribute("result", scheduleDto);
		
		return "/WEB-INF/views/schedulePage/json/scheduleLoadResult.jsp";
//		return "/WEB-INF/views/schedulePage/scheduleMainPage.jsp";
	}

}
