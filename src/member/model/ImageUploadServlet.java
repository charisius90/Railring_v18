package member.model;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import member.model.MemberDao;
import member.model.MemberDto;

public class ImageUploadServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		
		// 5Mbyte ����
		int maxSize  = 5 * 1024*1024;        
		
		// ������ �����̳� ���
		String root = req.getSession().getServletContext().getRealPath("/");
		
		// ���� ���� ���(ex : /home/tour/web/ROOT/upload)
		String savePath = root + "upload";
		
		// ���ε� ���ϸ�
		String uploadFile = "";
		
		// ���� ������ ���ϸ�
		String newFileName = "";
		
		int read = 0;
		byte[] buf = new byte[1024];
		FileInputStream fin = null;
		FileOutputStream fout = null;
		long currentTime = System.currentTimeMillis();  
		SimpleDateFormat simDf = new SimpleDateFormat("yyyyMMddHHmmss");  
		
		try{
			MultipartRequest multi = new MultipartRequest(req, savePath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
			
			// ���Ͼ��ε�
			uploadFile = multi.getFilesystemName("file");
			// ���� ������ ���ϸ�(ex : 20140819151221.jpg)
			newFileName = simDf.format(new Date(currentTime)) + "." + uploadFile.substring(uploadFile.lastIndexOf(".")+1);
			
			
			// ���ε�� ���� ��ü ����
			File oldFile = new File(savePath + "/" + uploadFile);
			// ���� ����� ���� ��ü ����
			File newFile = new File(savePath + "/" + newFileName);
			// ���ϸ� rename
			if(!oldFile.renameTo(newFile)){
				// rename�� ���� ������� ������ ������ �����ϰ� ���������� ����
				buf = new byte[1024];
				fin = new FileInputStream(oldFile);
				fout = new FileOutputStream(newFile);
				read = 0;
				while((read=fin.read(buf,0,buf.length))!=-1){
					fout.write(buf, 0, read);
				}
				
				fin.close();
				fout.close();
				oldFile.delete();
			}   
			
			// ### �̹��� ������¡
			// ���� �� ���� ������(px)
			int width = 164;
			int height = 164;
			String cmd = req.getParameter("cmd");
			if(cmd.equals("back")){
				width = 950;
				height = 950;
			}
			
			Image origImg = new ImageIcon(newFile.toURI().toURL()).getImage();
			
			// �̹��� ���� ����(ū ���� �ִ� 164px)
			double origHeight = origImg.getHeight(null);
			double origWidth = origImg.getWidth(null);
			double ratio = origHeight/origWidth;
			if(ratio > 1){
				height = (int)(width * ratio);
			}
			else{
				width = (int)(height / ratio);
			}
			System.out.println("check height : " + height);
			System.out.println("check width : " + width);
			Image newImg = origImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			
			// �ȼ� ����
			int pixels[] = new int[width * height]; 
            PixelGrabber pg = new PixelGrabber(newImg, 0, 0, width, height, pixels, 0, width); 
            try {
                pg.grabPixels();
            }
            catch (InterruptedException e) {
                throw new IOException(e.getMessage());
            } 
            BufferedImage destImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
            destImg.setRGB(0, 0, width, height, pixels, 0, width); 
            ImageIO.write(destImg, "jpg", newFile);
            
			String origFileName = multi.getOriginalFileName("file");
			String sysFileName = newFile.getName();
			String filePath = newFile.getPath();
			
			// DB�� �� ���ϸ� ����
			HttpSession session = req.getSession();
			MemberDto dto = (MemberDto)session.getAttribute("member");
			MemberDao dao = new MemberDao();
			if(cmd.equals("back")){
				dto.setMem_background(sysFileName);
			}
			else{
				dto.setMem_image(sysFileName);
			}
			if(dao.updateMember(dto)){
				//������Ʈ ���� �� ��� Ŭ���̾�Ʈ�� ����
				session.setAttribute("member", dto);
				PrintWriter out = resp.getWriter();
				out.print(true);
			}
			
			System.out.println("--------------------------------------- -------------------------------------------");
			System.out.println("!! check resized :");
			System.out.println("���������̸� :" + origFileName);
			System.out.println("���������̸� :" + sysFileName);
			System.out.println("�������ϰ�� :" + filePath);
			
		}catch(Exception err){
			System.out.println("ImageUploadServlet : " + err);
			err.printStackTrace();
		}
	}
	
}
