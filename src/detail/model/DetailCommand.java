package detail.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbcp.Command;
import utils.Paging;

//by 강병현,박규채
public class DetailCommand implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
//		PrintWriter out = resp.getWriter();
//		String str = "<script language='javascript'>window.open('"+url+"')</script>";
//		System.out.println(str);
//		out.print(str);
		return "/WEB-INF/views/schedulePage/scheduleDetailPage.jsp";
	}

}
