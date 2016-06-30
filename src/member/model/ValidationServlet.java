package member.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.MemberDao;

public class ValidationServlet extends HttpServlet{
	
	public boolean isValidPw(String pw){
		Pattern p = Pattern.compile("([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])");
		Matcher m = p.matcher(pw);
		
		if(pw.length() >= 8 && pw.length() <= 16){
			return m.find();
		}
		
		return false;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cmd = req.getParameter("cmd");
		System.out.println("cmd: " + cmd);
		
		MemberDao dao = new MemberDao();
		PrintWriter out = resp.getWriter();
		String result = "";
		
		if(cmd.equals("email")){
			String email = req.getParameter("email");
			System.out.println(email);
			
			boolean flag = dao.isMember(email);
			
			Pattern p = Pattern.compile("(^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$)");
			Matcher m = p.matcher(email); 
			boolean test = m.find();
			
			if(test==true){
				if(flag==true){
					result = "no";
				}
				else{
					result = "yes";
				}      
			}
			else{
				result = "not";
			}
		}
		else if(cmd.equals("pw")){
			String pw = req.getParameter("pw");
			if(isValidPw(pw)){
				result = "yes";
			}
			else{
				result = "no";
			}
		}
		else if(cmd.equals("name")){
			String name = req.getParameter("name");
			if(name.length()>6){
				result = "no";
			}
			else{
				result = "yes";
			}
		}
		
		out.print(result);
	}
}
