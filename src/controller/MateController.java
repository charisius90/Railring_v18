package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbcp.Command;
import mate.model.FactoryCommand;


public class MateController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("메이트컨트롤러");
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		
		String cmd= req.getParameter("cmd");
		String url="";
		System.out.println(cmd);
		
		Command command=null;
		
		FactoryCommand factory = FactoryCommand.newInstance();
		command=factory.createInstance(cmd);
		url = (String)command.processCommand(req, resp);
		System.out.println("url받아옴");
		if (url == null) {
			return;
			
		}
		System.out.println(url);
		RequestDispatcher view = req.getRequestDispatcher(url);
		view.forward(req, resp);
	}

	
}
