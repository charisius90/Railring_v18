package review.model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbcp.Command;
import member.model.MemberDto;

public class ReviewPostCommand implements Command{

   @Override
   public Object processCommand(HttpServletRequest req, HttpServletResponse resp)
         throws ServletException, IOException {
      HttpSession session = req.getSession();
      ReviewDto dto = null;
      MemberDto member = (MemberDto)session.getAttribute("member");
      String url = "WEB-INF/views/reviewPage/reviewPostPage.jsp";
      
      // ��ä : �α��� ���ǰ��� �ٸ��� �α����������� �̵�
      if(member == null){
    	  req.setAttribute("error", "guest");
         url = "/WEB-INF/views/memberPage/memberLoginPage.jsp";
      }
      else{
    	  String mem_no = member.getMem_no(); 
         ReviewDao dao = new ReviewDao();
         dto = dao.getReview(Integer.parseInt(mem_no), true);
         req.setAttribute("dto", dto);
      }
      
      return url;
   }
   
}