package mate.model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbcp.Command;

//by �ս���, ������
public class MateReadCommand implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("����Ŀ�ǵ��");
		MateDao dao = new MateDao();
		
		// ��ä : ��ȸ�� ����
		req.setAttribute("mate", dao.getMate(Integer.parseInt(req.getParameter("mate_no")), true));
		System.out.println("���� ����jsp��");
		return "/WEB-INF/views/matePage/mateReadPage.jsp";
	}

	
}
