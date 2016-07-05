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
 * ��й�ȣ ���� Ŀ�ǵ�
 * @author ����
 *
 */
public class MyPasswordCommand implements Command {
	
	/**
	 * ����ǥ�������� ��й�ȣ ���ȵ� Ȯ��(����-���� or ����-Ư������ or ����-����-Ư�����ڷ� 8��-16�� ����)
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
			req.setAttribute("wrong", "�߸��� �н����� �Դϴ�.");
			flag = false;
		}
		
		if(!newPw.equals(checkPw)){
			req.setAttribute("diff", "��й�ȣ�� ���� �ٸ��ϴ�.");
			if(checkPw.length()<8){
				req.setAttribute("diff", "�� ��й�ȣ�� �ѹ� �� �Է��ϼ���.");
			}
			flag = false;
		}
		
		if(!isValidPw(newPw)){
			req.setAttribute("fault", "����,����,Ư������ ���� 8�� �̻� 16�� ���ϸ� �����մϴ�.");
			flag = false;
		}
		
		if(flag == true){
			dao.changePw(email, newPw);
			req.setAttribute("success", "��й�ȣ ���� �Ϸ�");
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
