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

public class PostCommand implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("post커맨드");
		HttpSession session = req.getSession();
		MemberDto member = (MemberDto) session.getAttribute("member");
		if(member == null) {
			req.setAttribute("result", false);
			req.setAttribute("message", "로그인 해주세요.");
			return "/WEB-INF/views/schedulePage/json/schedulePostResult.jsp"; 
		}
		String mem_no = member.getMem_no();
		String sch_no = (String) req.getParameter("sch_no");
		System.out.println("등록될 스케쥴 번호: " + sch_no);
		String[] list = req.getParameterValues("city_title");
		
		for(int i=0; i<list.length; i++){
			System.out.println(list[i]);
		}
	
		
		ScheduleDao dao = new ScheduleDao();
//		boolean result = dao.insertSchedule(mem_no, sch_no, list);
		boolean result = dao.checkSchedule(mem_no, sch_no, list);
		if(!result) {
			req.setAttribute("message", "스케줄 등록에 실패했습니다.");
		}
		req.setAttribute("result", result);
		
		return "/WEB-INF/views/schedulePage/json/schedulePostResult.jsp";
	}

}
