package reviewreply.model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbcp.Command;

//by 손승한, 강병현
public class ReviewReplyUpdateCommand implements Command{

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		return "/WEB-INF/views/reviewPage/reviewUpdatePage.jsp";
	}

	
}
