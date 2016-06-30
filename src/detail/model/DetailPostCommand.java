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
		System.out.println("����������ƮĿ�ǵ�");
		HttpSession session = req.getSession();
		MemberDto member = (MemberDto) session.getAttribute("member");
		
		String mem_no = member.getMem_no();
		String sch_no = (String) req.getParameter("sch_no");
		String maincity_no = (String) req.getParameter("maincity_no");
		String[] list = req.getParameterValues("city_title");
		
		System.out.println("ȸ��:"+mem_no+"������:"+sch_no+"����:"+maincity_no);
		for(int i=0; i<list.length; i++){
			System.out.println(list[i]);
		}
		
		DetailDao dao = new DetailDao();
		boolean result = dao.insertDetail(maincity_no, sch_no, list);
		if(!result) {
			req.setAttribute("message", "������ ��Ͽ� �����߽��ϴ�.");
		}
		req.setAttribute("result", result);
		
		return "/WEB-INF/views/schedulePage/json/detailPostResult.jsp";
	}

}
