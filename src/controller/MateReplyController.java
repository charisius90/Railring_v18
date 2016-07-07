package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbcp.Command;
import matereply.model.FactoryCommand;

//by 손승한, 강병현
public class MateReplyController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("메이트리플라이컨트롤러");
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		
		String cmd= req.getParameter("cmd");
		String url="";
		System.out.println(cmd);
		
		Command command=null;
		
		FactoryCommand factory = FactoryCommand.newInstance();
		command=factory.createInstance(cmd);
		
		url = (String)command.processCommand(req, resp);
		
		RequestDispatcher view = req.getRequestDispatcher(url);
		view.forward(req, resp);
	}

	
}
