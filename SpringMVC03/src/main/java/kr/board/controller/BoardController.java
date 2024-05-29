package kr.board.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.board.entity.Board;
import kr.board.entity.LhubCrs;
import kr.board.mapper.BoardMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class BoardController {
/*
 * ****    SpringMVC03 커리큘럼
 * HttpSession, Cookie, Login, Logout
 * @Service, @Repository
 * 3 Tier(Layer) Architecture
 * Web, Service, Persistence Layer
 * Board example
*/

	//REST방식 적용을위해 주석처리. BoardRestController에 REST방식 적용한코드 기재
//	@Autowired
//	BoardMapper boardMapper;
	
	@RequestMapping("/boardMain.do")
	public String main() {		
		return "board/main"; 
	}

	
	@ResponseBody
	@RequestMapping(value="/lfcrs11.do", produces="application/json;  charset=utf8", method=RequestMethod.POST)
	//public JSONObject lfcrs(HttpSession session, HttpServletRequest request, HttpServletResponse response, @ModelAttribute LhubCrs lhubCrs) throws Exception {
	public void lfcrs(HttpSession session, HttpServletRequest request, HttpServletResponse response, @ModelAttribute LhubCrs lhubCrs) throws Exception {	
		//응답로직
    	response.setCharacterEncoding("UTF-8");
    	response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        StringBuffer msgBuffer = new StringBuffer();
        JSONObject msgObj = new JSONObject();
		System.out.println("=========================================  recept success ");
		String result = "Success";
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		try{

			String jsonData = request.getParameter("dataList");
			System.out.println("=========================================  jsonData :  "+ jsonData);
			//"로 변환
			jsonData = jsonData.replaceAll("&amp;quot;", "\"");
			System.out.println("=========================================  0000000000000000000 ");
			JSONParser parser = new JSONParser();
			System.out.println("=========================================  1111111111111111111111111 ");
			JSONObject obj = (JSONObject) parser.parse(jsonData);
			System.out.println("=========================================  2222222222222222222 ");
			JSONArray qstnListArray = (JSONArray) obj.get("qstnList");

//			String srvSeq = (String) obj.get("srvSeq");
//			srvQstnVO.setSrvSeq(srvSeq);
			String registryNo = "";
			String instituteCode = "";
			for(int i=0; i < qstnListArray.size(); i++) {
				JSONObject srvQstnObject = (JSONObject) qstnListArray.get(i);
				registryNo = (String) srvQstnObject.get("REGISTRYNO");
				instituteCode = (String) srvQstnObject.get("INSTITUTE_CODE");
				System.out.println("============================= REGISTRYNO : "+registryNo );
				System.out.println("============================= INSTITUTE_CODE : "+instituteCode );
//				//값 셋팅
				lhubCrs.setREGISTRYNO(registryNo);
				lhubCrs.setINSTITUTE_CODE(instituteCode);
//
//				resMap = srvService.srvQstnNumSave(lhubCrs);

				//가져온 값 처리로직
				//resMap = 고도화서비스.메소드();
	            //result = "Success";
	            if(!resMap.isEmpty()){
	            	msgObj.put("result", "Success");
	            	msgObj.put("errCode", "");
	            	msgObj.put("errMsg", "");
	            }
	            
				
			}
			
//	         JSONParser jsonParser = new JSONParser();
//	         JSONObject jsonObj = (JSONObject) jsonParser.parse(jsonStr);
//	         JSONArray memberArray = (JSONArray) jsonObj.get("members");
//	 
//	         System.out.println("=====Members=====");
//	         for(int i=0 ; i<memberArray.size() ; i++){
//	             JSONObject tempObj = (JSONObject) memberArray.get(i);
//	             System.out.println(""+(i+1)+"번째 멤버의 이름 : "+tempObj.get("name"));
//	             System.out.println(""+(i+1)+"번째 멤버의 이메일 : "+tempObj.get("email"));
//	             System.out.println(""+(i+1)+"번째 멤버의 나이 : "+tempObj.get("age"));
//	             System.out.println("----------------------------");
//	         }
	


		}catch(Exception e) {
			//result = 1;
			System.out.println("{}"+e.toString());
			e.getStackTrace();
			
			msgObj.put("result", "fail");
			msgObj.put("errCode", "003");//고도화팀에서 에러코드항목 정의해서 api공유 후 러닝허브에서 받는쪽 처리를 해야한다.
        	msgObj.put("errMsg", e.toString());
		} finally {
			msgBuffer.append(msgObj.toString());
			
			//	결과 전송
            out.print(msgBuffer.toString());
            out.flush();
            out.close();
            
            //DbUtils.closeQuietly(conn);
		}
		//return msgObj;

//		returnMap.put("result", result);

		//return ViewUtil.ajaxView(returnMap);
	}

	
//	//러닝허브에서 송신한 데이터를 수신처리 후 응답메시지를 리턴한다.
//	@RequestMapping("/boardList.do")
//	public @ResponseBody JSONObject linkageTest(HttpServletRequest request, HttpServletResponse response){		
//		//List<Board> list = boardMapper.getLists();
//		return list; // (서버에서 객체를 응답한다.) JSON 데이터 형식으로 변환을 해서 리턴하겠다.
//	}
	
//	// @ResponseBody -> jackson-databind를 pom.xml에 추가해줘야한다.(객체를  -> JSON데이터 포멧으로 변환) 예) [ { },{ },{ } ] => {}을 JSON데이터 포멧이라부른다.
//	@RequestMapping("/boardList.do")
//	public @ResponseBody List<Board> boadList(){		
//		List<Board> list = boardMapper.getLists();
//		return list; // (서버에서 객체를 응답한다.) JSON 데이터 형식으로 변환을 해서 리턴하겠다.
//	}
//	
//	@RequestMapping("/boardInsert.do")
//	public @ResponseBody void boardInsert(Board vo) {		
//		boardMapper.boardInsert(vo);
//	}
//	
//	@RequestMapping("/boardDelete.do")
//	public @ResponseBody void boardDelete(@RequestParam("idx") int idx) {
//		boardMapper.boardDelete(idx);
//	}
//	
//	@RequestMapping("/boardUpdate.do")
//	public @ResponseBody void boardUpdate(Board vo) {
//		boardMapper.boardUpdate(vo);
//	}
//	
//	@RequestMapping("/boardContent.do")
//	public @ResponseBody Board boardContent(int idx) {
//		Board vo = boardMapper.boardContent(idx);
//		return vo; // vo -> JSON
//	}
//	
//	@RequestMapping("/boardCount.do")
//	public @ResponseBody Board boardCount(int idx) {
//		boardMapper.boardCount(idx);
//		Board vo = boardMapper.boardContent(idx);
//		return vo;
//	}
	
	
}
