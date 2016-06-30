package schedule.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class TrainServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		// 결과리스트
		ArrayList<TrainDto> list = new ArrayList<>();
		
		String dep = req.getParameter("dep"); // 출발역 코드
		String arr = req.getParameter("arr"); // 도착역 코드
		String date = req.getParameter("date"); // 입력받은 출발날짜 ex : 20160622
		String time = req.getParameter("time"); // 입력받은 출발시간 ex : 02:30
		
		String[] test = time.split(":");
		String tempTime = test[0] + test[1]; // ex : 0230
		
		boolean flag = false; // 여수-부산 경우는 true가 됨
		
		String yeosu = "NAT041993"; // 여수 부산의 역 코드
		String busan = "NAT750046";
		if(dep.equals(yeosu) && arr.equals(busan)){
			flag = true;
			arr = "NAT041595"; // 종착역 코드를 순천역 코드로 설정
		}
		
		StringBuilder urlBuilder = new StringBuilder("http://openapi.tago.go.kr/openapi/service/TrainInfoService/getStrtpntAlocFndTrainInfo"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=tB6mDglNlQ5OUKGzpOD5wbJbcPZICI5auZKw7ZvPd62I3ip5ogzXiM49tNQIhMRK2EVdHGHX9Jup2c%2BjLKs38g%3D%3D"); /* Service Key */
		urlBuilder.append("&" + URLEncoder.encode("depPlaceId","UTF-8") + "=" + URLEncoder.encode(dep, "UTF-8")); /*출발지ID*/
        urlBuilder.append("&" + URLEncoder.encode("arrPlaceId","UTF-8") + "=" + URLEncoder.encode(arr, "UTF-8")); /*도착지ID*/
        urlBuilder.append("&" + URLEncoder.encode("depPlandTime","UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")); /*출발일*/
        urlBuilder.append("&" + URLEncoder.encode("trainGradeCode","UTF-8") + "=" + URLEncoder.encode("02", "UTF-8")); /*차량종류코드*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("999", "UTF-8")); /*검색건수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code1: " + conn.getResponseCode());
		
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(conn.getInputStream());
			
			Element root = doc.getDocumentElement();
			Node items = root.getFirstChild().getNextSibling().getFirstChild();
			
			TrainDto dto = null;
			for(Node n=items.getFirstChild(); n!=null; n=n.getNextSibling()){
				if(n.getNodeType() == Node.ELEMENT_NODE){
					if(n.getNodeName().equals("item")){
						dto = new TrainDto();
						Node temp = n.getFirstChild().getNextSibling();
						dto.setArrSt(temp.getTextContent());
						
						temp = temp.getNextSibling();
						dto.setArrTime(temp.getTextContent());
						
						temp = temp.getNextSibling();
						dto.setDepSt(temp.getTextContent());

						temp = temp.getNextSibling();
						dto.setDepTime(temp.getTextContent());
						
						list.add(dto);
					}
				}
			}
		}
		catch(Exception err){
			System.out.println("test : " + err);
		}
		conn.disconnect();
		
		////////////////////////////////////////////////////////////
		if(flag){
			// 여수-부산 인 경우 직행이 없어 여수-순천, 순천-부산으로 안내 함에 따른 추가 조회
			dep = arr;
			arr = busan;
			
			StringBuilder urlBuilder2 = new StringBuilder("http://openapi.tago.go.kr/openapi/service/TrainInfoService/getStrtpntAlocFndTrainInfo"); /* URL */
			urlBuilder2.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=tB6mDglNlQ5OUKGzpOD5wbJbcPZICI5auZKw7ZvPd62I3ip5ogzXiM49tNQIhMRK2EVdHGHX9Jup2c%2BjLKs38g%3D%3D"); /* Service Key */
			urlBuilder2.append("&" + URLEncoder.encode("depPlaceId","UTF-8") + "=" + URLEncoder.encode(dep, "UTF-8")); /*출발지ID*/
	        urlBuilder2.append("&" + URLEncoder.encode("arrPlaceId","UTF-8") + "=" + URLEncoder.encode(arr, "UTF-8")); /*도착지ID*/
	        urlBuilder2.append("&" + URLEncoder.encode("depPlandTime","UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")); /*출발일*/
	        urlBuilder2.append("&" + URLEncoder.encode("trainGradeCode","UTF-8") + "=" + URLEncoder.encode("02", "UTF-8")); /*차량종류코드*/
	        urlBuilder2.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("999", "UTF-8")); /*검색건수*/
	        urlBuilder2.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
	        
			URL url2 = new URL(urlBuilder2.toString());
			HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
			conn2.setRequestMethod("GET");
			conn2.setRequestProperty("Content-type", "application/json");
			System.out.println("Response code2: " + conn2.getResponseCode());
			
			/////////////////////////////////
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder;
				builder = factory.newDocumentBuilder();
				Document doc = builder.parse(conn2.getInputStream());
				
				Element root = doc.getDocumentElement();
				Node items = root.getFirstChild().getNextSibling().getFirstChild();
				
				TrainDto dto = null;
				for(Node n=items.getFirstChild(); n!=null; n=n.getNextSibling()){
					if(n.getNodeType() == Node.ELEMENT_NODE){
						if(n.getNodeName().equals("item")){
							dto = new TrainDto();
							Node temp = n.getFirstChild().getNextSibling();
							dto.setArrSt(temp.getTextContent());
							
							temp = temp.getNextSibling();
							dto.setArrTime(temp.getTextContent());
							
							temp = temp.getNextSibling();
							dto.setDepSt(temp.getTextContent());
							
							temp = temp.getNextSibling();
							dto.setDepTime(temp.getTextContent());
							
							list.add(dto);
						}
					}
				}
				
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// 사용자가 입력한 시간보다 먼저 출발하는 열차는 리스트에서 제거
		Stack<Integer> stack = new Stack<>();
		int size = list.size();
		for(int i=0; i<size; i++){
			String depTime = "1" + list.get(i).getDepTime().substring(8, 12);
			String inputTime = "1" + tempTime;
			if(Integer.valueOf(depTime).intValue() < Integer.valueOf(inputTime).intValue()){
				stack.push(Integer.valueOf(i));
			}
		}
		size = stack.size();
		for(int i=0; i<size; i++){
			list.remove(stack.pop().intValue());
		}
		
		////////////////출력문 JSON 만들기
		if(list.isEmpty()){
			// 열차가 없을 경우
			PrintWriter out = resp.getWriter();
			out.print("{\"result\":[{\"item\":{\"dep\":\"no\",\"date\":\"no available train\",\"time\":\"no\"}}]}");
			out.close();
			return;
		}
		
		// dep:출발역, date:출발일자, time:열차출발시간
		String output = "{\"result\":[";
		int checker = 0;
		for(TrainDto t : list){
			output += "{\"item\":{\"dep\":\"" + t.getDepSt() + "\", \"date\":\"" + t.getDepTime().substring(0, 8) + "\", \"time\":\"" + t.getDepTime().substring(8, 10) + ":" + t.getDepTime().substring(10, 12) + "\"}}";
			checker++;
			if(checker == list.size()){
				break;
			}
			output += ", ";
		}
		output += "]}";
		
		PrintWriter out = resp.getWriter();
		out.print(output);
		out.close();
	}
	
}