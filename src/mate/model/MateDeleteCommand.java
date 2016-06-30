package mate.model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbcp.Command;

public class MateDeleteCommand implements Command{

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		MateDao dao = new MateDao();
		
		System.out.println("삭제커맨드왔고 번호는 : " + req.getParameter("mate_no"));
		dao.deleteMate(Integer.parseInt(req.getParameter("mate_no")));
		
		return "mate.action?cmd=MATE";
	}
}
