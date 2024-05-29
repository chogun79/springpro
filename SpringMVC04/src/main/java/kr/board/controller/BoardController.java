package kr.board.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.board.entity.Board;
import kr.board.mapper.BoardMapper;

@Controller
public class BoardController {

	//REST방식 적용을위해 주석처리. BoardRestController에 REST방식 적용한코드 기재
//	@Autowired
//	BoardMapper boardMapper;
	
	@RequestMapping("/boardMain.do")
	public String main() {		
		return "board/main";
	}
	
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
