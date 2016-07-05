package member.model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbcp.Command;

/**
 * 
 * @author ����
 *
 */
public class LoginCommand implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String email = req.getParameter("inputEmail");
		String pw = req.getParameter("inputPassword");
		String url = "/mainPage.jsp";
		
		MemberDao dao = new MemberDao();
		if(dao.isCollectPw(email, pw)){
			// �Էµ� �̸��ϰ� ��й�ȣ�� DB�� ��ġ�ϴ� ��� session�� �ش� ȸ������ ����
			HttpSession session = req.getSession();
			session.setAttribute("member", dao.getMemberInfo(email));
		}
		else{
			// �Է°��� ������ �ִ� ���
			if(!dao.isMember(email)){
				// �Է� �̸����� DB�� �������� �ʴ� ���
				req.setAttribute("error", "email");
			}
			else{
				// �Է� ��й�ȣ�� �߸��� ���
				req.setAttribute("error", "pw");
			}
			// ���� �Է°� request�� ������
			req.setAttribute("inputEmail", email);
			req.setAttribute("inputPassword", pw);
			url = "/WEB-INF/views/memberPage/memberLoginPage.jsp";
		}
		
		return url;	
	}
}
