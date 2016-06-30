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

public class DetailPostCommand implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("디테일포스트커맨드");
		HttpSession session = req.getSession();
		MemberDto member = (MemberDto) session.getAttribute("member");
		
		String mem_no = member.getMem_no();
		String sch_no = (String) req.getParameter("sch_no");
		String maincity_no = (String) req.getParameter("maincity_no");
		String[] list = req.getParameterValues("city_title");
		
		System.out.println("회원:"+mem_no+"스케쥴:"+sch_no+"도시:"+maincity_no);
		for(int i=0; i<list.length; i++){
			System.out.println(list[i]);
		}
		
		DetailDao dao = new DetailDao();
		boolean result = dao.insertDetail(maincity_no, sch_no, list);
		if(!result) {
			req.setAttribute("message", "스케줄 등록에 실패했습니다.");
		}
		req.setAttribute("result", result);
		
		return "/WEB-INF/views/schedulePage/json/detailPostResult.jsp";
	}

}
