package timeTable.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbcp.Command;
import detail.model.DetailDao;
import detail.model.DetailDto;
import member.model.MemberDto;
import schedule.model.ScheduleDao;
import utils.Paging;

//by 강병현, 박규채
public class TimeTableCommand implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("TIMETABLE커맨드");
		
//		HttpSession session = req.getSession();
//		MemberDto member = (MemberDto) session.getAttribute("member");
//		String mem_no = member.getMem_no();
		String sch_no = req.getParameter("sch_no");
		
		TimeTableDao tDao = new TimeTableDao();
		String mem_no = tDao.getMemNo(sch_no);
		
		System.out.println("멤번호:"+mem_no+", 스케쥴번호:"+sch_no);
		TimeTableDao dao = new TimeTableDao();
		List<TimeTableCityDto> list = dao.getTimeTable(sch_no, mem_no);
		
		for(int i=0; i<list.size(); i++){
			System.out.println(list.get(i));
		}
		
		req.setAttribute("result", list);
		
		return "/WEB-INF/views/schedulePage/json/TimeTableResult.jsp";
	}

}
