package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrontController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//승한 프론트 컨트롤러에서 각 컨트롤러로 연결 
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		String cmd = req.getParameter("cmd");
		String url = "";
		if(cmd.equals("main")){
			url="mainPage.jsp";
		}
		else if(cmd.equals("schedule")){
			url="WEB-INF/views/schedulePage/scheduleMainPage.jsp";
		}
		else if(cmd.equals("mate")){
			System.out.println("프론트컨트롤러");
			url="mate.action?cmd=MATE";
		}
		else if(cmd.equals("review")){
			url="review.action?cmd=REVIEW";
		}
		else if(cmd.equals("mypage")){
			url="member.action?cmd=MYPAGE";
		}
		else if(cmd.equals("login")){
			url="WEB-INF/views/memberPage/memberLoginPage.jsp";
		}
		else if(cmd.equals("logout")){
			url="member.action?cmd=LOGOUT";
		}
		else if(cmd.equals("signup")){
			url="WEB-INF/views/memberPage/memberRegisterPage.jsp";
		}
		else if(cmd.equals("password")){
			url="WEB-INF/views/memberPage/memberPasswordPage.jsp";
		}
		
		System.out.println(url);
		RequestDispatcher view= req.getRequestDispatcher(url);
		view.forward(req, resp);
	}
}
