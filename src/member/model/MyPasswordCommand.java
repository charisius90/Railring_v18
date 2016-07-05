package member.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbcp.Command;

/**
 * 비밀번호 변경 커맨드
 * @author 수항
 *
 */
public class MyPasswordCommand implements Command {
	
	/**
	 * 정규표현식으로 비밀번호 보안도 확인(숫자-문자 or 숫자-특수문자 or 숫자-문자-특수문자로 8자-16자 가능)
	 * @param pw
	 * @return
	 */
	public boolean isValidPw(String pw){
		Pattern p = Pattern.compile("([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])");
		Matcher m = p.matcher(pw);
		
		if(pw.length() >= 8 && pw.length() <= 16){
			return m.find();
		}
		
		return false;
	}

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String oldPw = req.getParameter("oldPw");
		String newPw = req.getParameter("newPw");
		String checkPw = req.getParameter("checkPw");
		String email = req.getParameter("email");
		
		MemberDao dao = new MemberDao();
		
		boolean flag = true;
		
		if(!dao.isCollectPw(email, oldPw)){
			req.setAttribute("wrong", "잘못된 패스워드 입니다.");
			flag = false;
		}
		
		if(!newPw.equals(checkPw)){
			req.setAttribute("diff", "비밀번호가 서로 다릅니다.");
			if(checkPw.length()<8){
				req.setAttribute("diff", "새 비밀번호를 한번 더 입력하세요.");
			}
			flag = false;
		}
		
		if(!isValidPw(newPw)){
			req.setAttribute("fault", "영문,숫자,특수문자 조합 8자 이상 16자 이하만 가능합니다.");
			flag = false;
		}
		
		if(flag == true){
			dao.changePw(email, newPw);
			req.setAttribute("success", "비밀번호 변경 완료");
		}
		else{
			req.setAttribute("oldPw", oldPw);
			req.setAttribute("newPw", newPw);
			req.setAttribute("checkPw", checkPw);
		}
		req.setAttribute("active", true);
		
		return "member.action?cmd=MYPAGE";
	}
}
