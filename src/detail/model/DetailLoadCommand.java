package detail.model;

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

//by 강병현,박규채
public class DetailLoadCommand implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("LOAD커맨드");
		
		DetailDao dao = new DetailDao();
		String sch_no = req.getParameter("sch_no");
		String city_no = req.getParameter("city_no");
		System.out.println(sch_no);
		System.out.println(city_no);
		
		DetailDto detailDto = dao.getDetail(sch_no, city_no);
		System.out.println(detailDto);
		
		req.setAttribute("result", detailDto);
		
		return "/WEB-INF/views/schedulePage/json/detailLoadResult.jsp";
	}

}
