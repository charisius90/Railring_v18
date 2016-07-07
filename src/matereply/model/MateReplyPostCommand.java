package matereply.model;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.print.attribute.standard.RequestingUserName;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;

import dbcp.Command;
import member.model.MemberDto;
import oracle.net.ns.SessionAtts;

//by �ս���, ������
public class MateReplyPostCommand implements Command {
	
	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//�񵿱�� ȣ���. ������ ��ȯ X
		
		MateReplyDto dto = new MateReplyDto();
		MateReplyDao dao = new MateReplyDao();
		
//		replyBtnClick(re_no)���� ���� re_no��
		String parent_no = req.getParameter("parent_no");
		HttpSession session = req.getSession();
		MemberDto member = (MemberDto) session.getAttribute("member");
		
		dto.setMat_re_content(req.getParameter("mat_re_content"));
		dto.setMat_no(req.getParameter("mat_no"));
		
		if(parent_no.equals("parent")){
			dto.setMem_no(member.getMem_no());
			dao.insertMateReply(dto);
		}else{
			dto.setMem_no(member.getMem_no());
			dao.insertMateReReply(dto, parent_no);
		}
		// content,mem_no,mat_no set�� ������ dao�Լ��� ���
		
		
		List<MateReplyDto> list = dao.getMateReplyList(req.getParameter("mat_no"));
		System.out.println("list���� �Ϸ�");
		
		//�߰��� ��� ������ dto��ü�� set�ؼ� �ٽ� �Ѱܹ޴´�. ���̱����ؼ�
		//�װ� �Ʒ�ó�� ����
		//�ٽ� �Ѱܹ����� ��� ����� �ѳ��� �ƴ� ��ü list�� �����ͼ� ���ΰ�ħ������Ѵ�.
		
		req.setAttribute("replys", list);
		return "/WEB-INF/views/matePage/json/mateReplyJson.jsp";
	}

}
