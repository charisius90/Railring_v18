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

//by °­º´Çö
public class ListCommand implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		MemberDto member = (MemberDto) session.getAttribute("member");
		if(member == null){
			return "/WEB-INF/views/schedulePage/json/scheduleListResult.jsp";
		}
		String mem_no = member.getMem_no();
		
		ScheduleDao dao = new ScheduleDao();
		Vector<ScheduleDto> result = dao.getScheduleList(mem_no);
		
		req.setAttribute("result", result);
		
		return "/WEB-INF/views/schedulePage/json/scheduleListResult.jsp";
	}

}
