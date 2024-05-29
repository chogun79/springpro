package kr.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//REST방식 적용을위해 주석처리. BoardRestController에 REST방식 적용한코드 기재
@Controller
public class BoardController{	
	
	@RequestMapping("/boardMain.do")
	public String main() {
		return "board/main"; 
	}
	
}
