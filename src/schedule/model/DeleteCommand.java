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

public class DeleteCommand implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("DeleteĿ�ǵ�");
		
		String sch_no = (String) req.getParameter("sch_no");
		
		ScheduleDao dao = new ScheduleDao();
		
		
		boolean result = dao.deleteSchedule(sch_no);
		
		if(!result) {
			req.setAttribute("message", "������ ������ �����߽��ϴ�.");
		}
		req.setAttribute("result", result);
		
		return "/WEB-INF/views/schedulePage/json/schedulePostResult.jsp";
	}

}
