package kr.board.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.board.entity.Board;
import kr.board.entity.LhubCrs;
import kr.board.entity.LhubCrsWapper;
import kr.board.mapper.BoardMapper;


@RequestMapping("/board")
@RestController // @ResponseBody(JSON)응답, 어노테이션에 ResponseBody가 묵시적으로 들어있어서 따로 메소드에 표시할 필요가없음
public class BoardRestController {
	
	    @Autowired
	    BoardMapper boardMapper;
	
	    // @ResponseBody->jackson-databind(객체를->JSON 데이터포멧으로 변환)
		@GetMapping("/all")
		public List<Board> boardList(){
			List<Board> list=boardMapper.getLists();
			System.out.println("========================== url : http://localhost:8081/controller/board/all");
			return list; // JSON 데이터 형식으로 변환(API)해서 리턴(응답)하겠다.
		}	
		//@RequestMapping("/boardInsert.do")
		@PostMapping("/new")
		public void boardInsert(Board vo) {
			boardMapper.boardInsert(vo); // 등록성공
		}
	
//테스트코드 조상현 Rest API  Json연동 시작 : 1.수신(과정), 2.수신(기수), 3.송신(신청),4.수신(수료)================================================================================================================>

	    //1. Rest API 수신처리 : 러닝허브에서 송신한 송신데이터에 대한 수신처리 기능(JSON데이터 수신처리 및 응답 리턴)
	    @PostMapping("/lfCrs")//과정정보
	    public void lfCrs(HttpServletRequest request,
                HttpServletResponse response) throws IOException, ParseException {

	    	System.out.println("==================== Lhub lfcrs method request data recept ===================== ");
	    	//응답변수 셋팅
	    	String result = "Success";//처리결과코드
	    	String errCode = "";//에러발생시 에러코드
	    	//고도화에서 러닝허브로 처리결과(응답) 데이터 송신
	    	response.setCharacterEncoding("UTF-8");
	    	response.setContentType("application/json");
	        PrintWriter out = response.getWriter();
	        StringBuffer msgBuffer = new StringBuffer();
	        JSONObject msgObj = new JSONObject();
	        
	        
	        // 수신 Process the JSON array 
	        ServletInputStream inputStream = request.getInputStream();
	        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
	        System.out.println("messageBody={} : "+ messageBody);
	        


	    	try {
		        JSONParser parser = new JSONParser();
		        JSONObject obj = (JSONObject) parser.parse(messageBody.toString());
		        JSONArray crsListArray = (JSONArray) obj.get("dataList");//과정관련 데이터
		        
		        
		        //이력저장로직작성  String seq =  service.insertHist();//데이터를 insert하고 seq를 가져온다.
		        
				String registryNo = "";
				String instituteName = "";
			

	    		//과정관련 데이터
				for(int i=0; i < crsListArray.size(); i++) {
					JSONObject srvQstnObject = (JSONObject) crsListArray.get(i);
					
					//json데이터 값 추출 및 셋팅
					registryNo = (String) srvQstnObject.get("REGISTRYNO");
					instituteName = (String) srvQstnObject.get("INSTITUTE_NAME");
					System.out.println("============================= REGISTRYNO : "+registryNo );
					System.out.println("============================= INSTITUTE_NAME : "+instituteName );
	
					//비즈니스로직 처리
	//				LhubCrs lhubCrs = new LhubCrs();
	//				lhubCrs.setREGISTRYNO(registryNo);
	//				//또는
	//				Map<String, Object> resMap = new HashMap<String, Object>();
	//	            if(!resMap.isEmpty()){
	//	            	resMap.put("registryNo", registryNo);
	//	            	resMap.put("instituteName", instituteName);
	//	            }
								
	//				resMap = apiService.crsInsert(lhubCrsV0);//서비스 호출	
		            
				}

				
//======================================  응답처리 ======================================			
				msgObj.put("errMsg","");
			} catch (ParseException pe) {
				result = "fail";
				errCode = "001";// JSON 파싱에러
	        	msgObj.put("errMsg", errorPrint(pe.toString()));
				pe.getStackTrace();
			} catch (Exception e) {
				result = "fail";
				errCode = "002";// 에러
	        	msgObj.put("errMsg", errorPrint(e.toString()));
				e.getStackTrace();
			} finally {
				msgObj.put("result", result);
				msgObj.put("errCode", errCode);
				msgBuffer.append(msgObj.toString());
				
				//	결과 전송
	            out.print(msgBuffer.toString());
	            out.flush();
	            out.close();
			}
	    	
	    	//수신은 정상적으로 되었으나 처리과정에서 애러가 발생되었을 경우 앞전에 등록한 이력에 '처리결과'를 fail로 수정(디폴트 success), 에러코드,에러메시지 넣기
	    	//if("fail".equals(result)) //이력 수정  =  service.updateHist(seq, result, errCode );
	        
	    		//response.getWriter().write("ok");//단순 텍스트응답시 사용

	    }


	    //2. Rest API 수신처리 : 러닝허브에서 송신한 송신데이터에 대한 수신처리 기능(JSON데이터 수신처리 및 응답 리턴)
	    //기수연동 : 러닝허브송신데이터에 대한 -> 고도화 수신처리 메소드	
	    @PostMapping("/lfCrsSess")//기수정보
	    public void lfCrsSess(HttpServletRequest request,
                HttpServletResponse response) throws IOException, ParseException {

	    	System.out.println("==================== Lhub lfcrssess method request data recept ===================== ");
	    	//응답변수 셋팅
	    	String result = "Success";//처리결과코드
	    	String errCode = "";//에러발생시 에러코드
	    	//고도화에서 러닝허브로 처리결과(응답) 데이터 송신
	    	response.setCharacterEncoding("UTF-8");
	    	response.setContentType("application/json");
	        PrintWriter out = response.getWriter();
	        StringBuffer msgBuffer = new StringBuffer();
	        JSONObject msgObj = new JSONObject();
	    	
	    	// 수신 Process the JSON array
	        ServletInputStream inputStream = request.getInputStream();
	        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
	        System.out.println("messageBody={} : "+ messageBody);

	        
		    try {

		    	JSONParser parser = new JSONParser();
		        JSONObject obj = (JSONObject) parser.parse(messageBody.toString());
		        JSONArray crssessListArray = (JSONArray) obj.get("dataList");//기수관련 데이터
		        JSONArray bookListArray = (JSONArray) obj.get("bookList");//교제정보 관련 데이터
		        
		        
		        //이력저장로직작성  String seq =  service.insertHist();//데이터를 insert하고 seq를 가져온다.
		        
				String cspCourseNo = "";
				String registryNo = "";
				//기수관련 데이터
				for(int i=0; i < crssessListArray.size(); i++) {
					JSONObject crsSessObject = (JSONObject) crssessListArray.get(i);
					
					//json데이터 값 추출 및 셋팅
					cspCourseNo = (String) crsSessObject.get("CSPCOURSENO");
					registryNo = (String) crsSessObject.get("REGISTRYNO");
					System.out.println("============================= CSPCOURSENO : "+cspCourseNo );
					System.out.println("============================= REGISTRYNO : "+registryNo );
	
					//비즈니스로직 처리
	//				LhubCrsSess lhubCrsSessVO = new LhubCrsSessVO();
	//				lhubCrsSess.setCspCourseNo(cspCourseNo);
	//				lhubCrsSess.setRegistryNo(registryNo);
	//				//또는
	//				Map<String, Object> resMap = new HashMap<String, Object>();
	//	            if(!resMap.isEmpty()){
	//	            	resMap.put("cspCourseNo", cspCourseNo);
	//	            	resMap.put("registryNo", registryNo);
	//	            }
								
	//				resMap = apiService.crsSessInsert(lhubCrsSessVO);//서비스 호출	
		            
				}
				
				
				String openingYear = "";
				String instituteName = "";
				
				//교제정보 관련 데이터
				for(int i=0; i < bookListArray.size(); i++) {
					JSONObject crsBookObject = (JSONObject) bookListArray.get(i);
					
					//json데이터 값 추출 및 셋팅
					openingYear = (String) crsBookObject.get("OPENINGYEAR");
					instituteName = (String) crsBookObject.get("INSTITUTE_NAME");
					System.out.println("============================= OPENINGYEAR : "+openingYear );
					System.out.println("============================= INSTITUTE_NAME : "+instituteName );
	
					//비즈니스로직 처리
	//				LhubCrsBookVO lhubCrsBookVO = new LhubCrsBookVO();
	//				lhubCrsBookVO.setCspCourseNo(openingYear);
	//				lhubCrsBookVO.setRegistryNo(instituteName);
	//				//또는
	//				Map<String, Object> resMap = new HashMap<String, Object>();
	//	            if(!resMap.isEmpty()){
	//	            	resMap.put("openingYear", openingYear);
	//	            	resMap.put("instituteName", instituteName);
	//	            }
								
	//				resMap = apiService.bookInsert(lhubCrsBookVO);//서비스 호출	
		            
				}
				
				//======================================  응답처리 ======================================			
				msgObj.put("errMsg","");
			} catch (ParseException pe) {
				result = "fail";
				errCode = "001";// JSON 파싱에러
	        	msgObj.put("errMsg", errorPrint(pe.toString()));
				pe.getStackTrace();
			} catch (Exception e) {
				result = "fail";
				errCode = "002";// 에러
	        	msgObj.put("errMsg", errorPrint(e.toString()));
				e.getStackTrace();
			} finally {
				msgObj.put("result", result);
				msgObj.put("errCode", errCode);
				msgBuffer.append(msgObj.toString());
				
				//	결과 전송
	            out.print(msgBuffer.toString());
	            out.flush();
	            out.close();
			}
	    	
	    	//수신은 정상적으로 되었으나 처리과정에서 애러가 발생되었을 경우 앞전에 등록 한이력에 '처리결과'를 fail로 수정(디폴트 success), 에러코드,에러메시지 넣기
	    	//if("fail".equals(result)) //이력 수정  =  service.updateHist(seq, result, errCode );
	        
	    		//response.getWriter().write("ok");//단순 텍스트응답시 사용

	    }   
	    
	    //3. Rest API 송신처리 : 신청정보 고도화 데이터를 러닝허브로 송신한다. (JSON데이터로 파싱 후 송신 후 리턴메시지 수신)
	    //disconnect쪽 보자 제공해준 소스가 무언가 문제가 있을수도있다.
	    //고도화에서 제공받은 송신처리메소드
	    @PutMapping("/update")//신청정보
	    //고도화용@RequestMapping("/sendApi.do")
	    public void flApply(@RequestBody Board vo) throws IOException {
	    //public String sendApi() throws Exception {
	       
	       System.out.println("start!!");
	
//	       StringBuilder postData = new StringBuilder("http://localhost:8080/api/v1/hrd/receiveApi.do");
//운영	       StringBuilder postData = new StringBuilder("http://learning-hub.co.kr/back/client/podoSync/PodoSyncAction.do");
	       StringBuilder postData = new StringBuilder("http://localhost/back/client/ehrdSync/LhubSyncAction.do");//개발자체테스트	       
		    //StringBuilder postData = new StringBuilder("http://localhost/back/client/lhubSync/LhubSyncAction.do");//개발자체테스트	
	       URL url = new URL(postData.toString());
	       
	       HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	       conn.setDoOutput(true);
	       conn.setDoInput(true);
	       conn.setRequestProperty("Content-type", "application/json");
	       conn.setRequestProperty("Accept", "application/json");
	       conn.setRequestMethod("POST");
	       
	       JSONObject jsonData = new JSONObject();
	       
	       //샘플 스프03  http://localhost:8081/controller/boardMain.do 메인접속 > 로그인  webper / 12345 > 게시판 글쓰고 수정한다음 '수정'누르면 연동데이터 날라감
	       // 개발버전
	       // 소속사명  	: 서울교통공사
	       // 소속사코드 	: 9296
	       // emp_no 	: 21714673
	       // 러닝허브 클라이언트 ID : 10777
	       // 러닝허브 사용자 번호 : 197843	안병은
	       //{"applyGb":"APPLY","blngCd":"9296","crsSessNo":"92960199","crsId":"1093653","empNo":"21714673","learningGb":"ALW"}
	       // 러닝허브 기수 아이디 : 1093653202200001 정보공개제도의 이해, stdy_st_dt : 20220101, stdy_end_dt : 20221231, crs_yr : 2022
	       
	       //crs_sess_id = '1057496202200001' and crs_id ='1057496' and crs_sess_no = '92960199';
	       jsonData.put("applyGb", "APPLY");
	       jsonData.put("blngCd", "9296");
	       jsonData.put("empNo", "21714673");
	       jsonData.put("crsSessNo", "92960199");
	       jsonData.put("crsId", "1057496");
	       jsonData.put("learningGb", "ALW");
	       System.out.println("22222222222222222222222222");
	       OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	       wr.write(jsonData.toString());
	       System.out.println("33333333333333333");
	       wr.flush();
	       wr.close();
	       
	       BufferedReader rd;
	       
	       if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	          rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
		       System.out.println("444444444444444444444");
	       }else{
	          rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(),"utf-8"));
		       System.out.println("555555555555555555");
	       }
	       
	       StringBuilder sb = new StringBuilder();
	       
	       String line;

	       while ((line = rd.readLine()) != null) {
	          sb.append(line);
	       }

	       String resultSet = sb.toString();
	       
	       System.out.println("resultSet: {} = " + resultSet);
	       
	       rd.close();
	       conn.disconnect();
	       
//	       return resultSet;
//	    }
			//boardMapper.boardUpdate(vo);				//@RequestBody로 받는다.
		}

	    //4. Rest API 수신처리 : 러닝허브에서 송신한 송신데이터에 대한 수신처리 기능(JSON데이터 수신처리 및 응답 리턴)
	    // 수료연동(러닝허브에서 배치를 통해 송신한 송신데이터에 대한 수신처리 메소드) : 러닝허브송신데이터에 대한 -> 고도화 수신처리 메소드
	    //http://localhost:8081/controller/board/lfCompl
	    //{"complInfoList":{"EMPLOYEENO":"8510080","CSPCOURSENO":"44230","COMPLETIONSTATUS":"1","REGISTRYNO":"220-82-01439","REGDATE":"20240229","ATTENDANCERATE":"0","DISCUSSSCORE":"0","COMPLETIONNO":"1007497-2009-00004-0003","DISCUSSCOUNT":"0","ATTENDANCESCORE":"0","OPENINGYEAR":"2009","REAL_EDU_TIME":"","COURSESEQNO":"4","EXAMSCORE":"50","EXAMCOUNT":"0","PROGRESSSCORE":"0","TASKCOUNT":"0","TOTALSCORE":"100","TASKSCORE":"50","PROGRESSRATE":"100"}}
	    @PostMapping("/lfCompl")
	    public void lfCompl(HttpServletRequest request,
                HttpServletResponse response) throws IOException, ParseException {

	    	System.out.println("==================== Lhub lfCompl method request data recept ===================== ");
	    	//응답변수 셋팅
	    	String result = "Success";//처리결과코드
	    	String errCode = "";//에러발생시 에러코드
	    	//고도화에서 러닝허브로 처리결과(응답) 데이터 송신
	    	response.setCharacterEncoding("UTF-8");
	    	response.setContentType("application/json");
	        PrintWriter out = response.getWriter();
	        StringBuffer msgBuffer = new StringBuffer();
	        JSONObject msgObj = new JSONObject();
	        
	        
	        // 수신 Process the JSON array 
	        ServletInputStream inputStream = request.getInputStream();
	        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
	        System.out.println("messageBody={} : "+ messageBody);
	        


	    	try {
		        JSONParser parser = new JSONParser();
		        JSONObject obj = (JSONObject) parser.parse(messageBody.toString());
		        JSONArray complListArray = (JSONArray) obj.get("complInfoList");//과정관련 데이터
		        
		        
		        //이력저장로직작성  String seq =  service.insertHist();//데이터를 insert하고 seq를 가져온다.
		        
				String registryNo = "";
				String employeeNo = "";
			

	    		//과정관련 데이터
				for(int i=0; i < complListArray.size(); i++) {
					JSONObject srvQstnObject = (JSONObject) complListArray.get(i);
					
					//json데이터 값 추출 및 셋팅
					//registryNo = (String) srvQstnObject.get("REGISTRYNO");
					employeeNo = (String) srvQstnObject.get("EMPLOYEENO");
					//System.out.println("============================= REGISTRYNO : "+registryNo );
					System.out.println("============================= EMPLOYEENO : "+employeeNo );
			    	System.out.println("==================================================================== ");
					//비즈니스로직 처리
	//				LhubCrs lhubCrs = new LhubCrs();
	//				lhubCrs.setREGISTRYNO(registryNo);
	//				//또는
	//				Map<String, Object> resMap = new HashMap<String, Object>();
	//	            if(!resMap.isEmpty()){
	//	            	resMap.put("registryNo", registryNo);
	//	            	resMap.put("instituteName", instituteName);
	//	            }
								
	//				resMap = apiService.crsInsert(lhubCrsV0);//서비스 호출	
		            
				}

				
//======================================  응답처리 ======================================			
				msgObj.put("result","200");
				msgObj.put("resultMsg","성공");				
				
				
			} catch (ParseException pe) {
				result = "500";
				//errCode = "001";// JSON 파싱에러
	        	msgObj.put("resultMsg", "실패");
				pe.getStackTrace();
			} catch (Exception e) {
				result = "500";
				//errCode = "500";// 에러
	        	msgObj.put("resultMsg", "실패");
				e.getStackTrace();
			} finally {
				msgObj.put("result", "200");
				msgObj.put("resultMsg", "성공");
				msgBuffer.append(msgObj.toString());
				
				//	결과 전송
	            out.print(msgBuffer.toString());
	            out.flush();
	            out.close();
			}
	    	
	    	//수신은 정상적으로 되었으나 처리과정에서 애러가 발생되었을 경우 앞전에 등록 한이력에 '처리결과'를 fail로 수정(디폴트 success), 에러코드,에러메시지 넣기
	    	//if("fail".equals(result)) //이력 수정  =  service.updateHist(seq, result, errCode );
	        
	    		//response.getWriter().write("ok");//단순 텍스트응답시 사용

	    }	
	  //메인 Exception 메시지 분리 메소드
		public String errorPrint(String errMsg){
		    int idx = errMsg.indexOf("Exception:");
		    String message = "";
	        if(idx > 0) message = errMsg.substring(0,idx + 9);

			return message;
		}
//테스트코드 조상현  Json연동 송수신처리 및 응답코드 리턴   종료 <===============================================================================================================
		

		@GetMapping("/api/{idx}")
		public Board flApply2(@PathVariable("idx") int idx) {
			StringBuilder postData = new StringBuilder("http://learning-hub.co.kr/back/client/podoSync/PodoSyncAction.do");

			Board vo=boardMapper.boardContent(idx);
			return vo; // vo->JSON
		}

		@DeleteMapping("/{idx}")
		public void boardDelete(@PathVariable("idx") int idx) {
			boardMapper.boardDelete(idx);
		}	
//		@PutMapping("/update")
//		public void boardUpdate(@RequestBody Board vo) {//jsp ajax의 json데이터를 컨트롤러가 받을때는
//			boardMapper.boardUpdate(vo);				//@RequestBody로 받는다.
//		}
		@GetMapping("/{idx}")
		public Board boardContent(@PathVariable("idx") int idx) {
			Board vo=boardMapper.boardContent(idx);
			return vo; // vo->JSON
		}
		@PutMapping("/count/{idx}")
		public Board boardCount(@PathVariable("idx") int idx) {
			boardMapper.boardCount(idx);
			Board vo=boardMapper.boardContent(idx);
			return vo;
		}
}