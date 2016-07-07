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

//by 손승한, 강병현
public class MateReplyPostCommand implements Command {
	
	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//비동기로 호출됨. 페이지 전환 X
		
		MateReplyDto dto = new MateReplyDto();
		MateReplyDao dao = new MateReplyDao();
		
//		replyBtnClick(re_no)에서 보낸 re_no값
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
		// content,mem_no,mat_no set한 다음에 dao함수로 등록
		
		
		List<MateReplyDto> list = dao.getMateReplyList(req.getParameter("mat_no"));
		System.out.println("list생성 완료");
		
		//추가된 댓글 정보를 dto객체로 set해서 다시 넘겨받는다. 보이기위해서
		//그걸 아래처럼 저장
		//다시 넘겨받을때 방금 등록한 한놈이 아닌 전체 list를 가져와서 새로고침해줘야한다.
		
		req.setAttribute("replys", list);
		return "/WEB-INF/views/matePage/json/mateReplyJson.jsp";
	}

}
