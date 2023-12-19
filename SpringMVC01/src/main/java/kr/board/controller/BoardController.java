package kr.board.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.board.entity.Board;
import kr.board.mapper.BoardMapper;

@Controller
public class BoardController {

	@Autowired
	private BoardMapper mapper;
	
	
	// /boardList.do
	// HandlerMapping
	@RequestMapping("/boardList.do") // 요청을 가저와서 Controller의 메소드에 매핑해주는것을 핸들러매핑이라고 한다.
	public String boardList(Model model) {
		
		
//      //우선 DB연결전이라 화면에 테이블 형태로표시할 목록을 List로 구성한다. 
//		Board vo = new Board();
//		vo.setIdx(1);
//		vo.setTitle("게시판실습");
//		vo.setContent("게시판실습");
//		vo.setContent("조상현");
//		vo.setIndate("2022-05-10");
//		vo.setCount(0);
//		
//		List<Board> list = new ArrayList<Board>();
//		list.add(vo);
//		list.add(vo);
//		list.add(vo);
		
		List<Board> list = mapper.getLists();

		model.addAttribute("list", list);
		//객체바인딩 : Controller가 각진 객체를 jsp로 포워딩할때 객체바인딩한다. web에서는 httpServlet인데 스프링은 Model이다.
		return "boardList"; // /WEB-INF/views/boardList.jsp - forward      - ViewResolver:요청처리결과를 경로를 붙여서 알려준다. servlet-context.xml에도 있다.
	}
	
	@GetMapping("/boardForm.do")//jsp의 a태그로 요청이 들어올경우 GetMapping을 사용해도 된다.
	public String boardForm() {
		return "boardForm"; // /WEB-INF/views/boardForm.jsp -> forward
	}
	
	@PostMapping("/boardInsert.do")
	public String boardInsert(Board vo) {// title, content, writer => 파라메터를 자동으로수집(Board)
		mapper.boardInsert(vo); //등록
		return "redirect:/boardList.do"; //redirect
	}
	
	@GetMapping("/boardContent.do")
	public String boardContent(int idx, Model model) { // ?idx=6  @RequestParam int idx의 @RequestParam은 삭제해도 상관없다.
		Board vo=mapper.boardContent(idx);
		// 조회수 증가
		mapper.boardCount(idx);
		model.addAttribute("vo", vo); // ${vo.idx}...
		return "boardContent"; // boardContent.jsp
	}

	@GetMapping("/boardDelete.do/{idx}")
	public String boardDelete(@PathVariable("idx") int idx) { // ?idx=6  //클라이언트(jsp)에서 변수를 지정할 필요없이 java서버에서 @PathVariable를 사용해서 변수를 가져와 치리한다.
		mapper.boardDelete(idx); //삭제		
		return "redirect:/boardList.do";
	}	
	@GetMapping("/boardUpdateForm.do/{idx}")
	public String boardUpdateForm(@PathVariable("idx") int idx, Model model) {
		Board vo=mapper.boardContent(idx);
		model.addAttribute("vo", vo);		
		return "boardUpdate"; // boardUpdate.jsp
	}
	@PostMapping("/boardUpdate.do")
	public String boardUpdate(Board vo) { // idx, title, content
		mapper.boardUpdate(vo); // 수정		
		return "redirect:/boardList.do";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
