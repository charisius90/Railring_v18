package member.model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbcp.Command;

/**
 * ȸ������ ���� Ŀ�ǵ�
 * @author ����
 *
 */
public class MyInfoCommand implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String inputName = req.getParameter("inputName");
		String inputGender = req.getParameter("inputGender");
		String inputBirth = req.getParameter("inputBirth");
		
		HttpSession session = req.getSession();
		MemberDto dto = (MemberDto)session.getAttribute("member");
		MemberDao dao = new MemberDao();
		
		if(inputName.length()>6 || inputName.length()<1){
			req.setAttribute("error", "1�� �̻� 6�� ���ϸ� �����մϴ�.");
		}
		else{
			dto.setMem_name(inputName);
			dto.setMem_gender(inputGender);
			dto.setMem_birth(inputBirth);
			
			System.out.println("gender : " + inputGender);
			if(dao.updateMember(dto)){
				//������Ʈ ����
				req.setAttribute("dto", dto);
			}
			else{
				req.setAttribute("error", "������Ʈ ����, ���� ����ڿ��� �����ϼ���.");
			}
			
			session.setAttribute("member", dto);
		}
		
		return "member.action?cmd=MYPAGE";
	}
}
