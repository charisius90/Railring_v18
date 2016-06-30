package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbcp.Command;
import schedule.model.FactoryCommand;


public class ScheduleController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Ω∫ƒ…¡Ïƒ¡∆Æ∑—∑Ø");
		req.setCharacterEncoding("euc-kr");
		resp.setCharacterEncoding("euc-kr");
		
		String cmd= req.getParameter("cmd");
		String url="";
		System.out.println(cmd);
		
		Command command=null;
		
		FactoryCommand factory = FactoryCommand.newInstance();
		command=factory.createInstance(cmd);
		
		url = (String)command.processCommand(req, resp);
		if (url == null) {
			return;
			
		}
		System.out.println("urlπﬁæ∆ø»");
		System.out.println(url);
		RequestDispatcher view = req.getRequestDispatcher(url);
		view.forward(req, resp);
	}

	
}
