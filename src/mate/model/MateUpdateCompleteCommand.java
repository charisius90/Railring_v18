package mate.model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbcp.Command;

//by 손승한, 강병현
public class MateUpdateCompleteCommand implements Command{

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("메이트업데이트컴플릿커맨드");
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		
		MateDto dto = new MateDto();
		dto.setMat_no(req.getParameter("mate_no"));
		dto.setMat_subject(req.getParameter("subject"));
		dto.setMat_content(req.getParameter("content"));
		
		System.out.println(dto.getMat_subject());
		System.out.println(dto.getMat_content());
		System.out.println(dto.getMat_no());
		System.out.println("이제 dao함수로");
		MateDao dao = new MateDao();
		dao.updateMate(dto);
		
		return "mate.action?cmd=READ&mate_no="+dto.getMat_no();
	}

	
}
