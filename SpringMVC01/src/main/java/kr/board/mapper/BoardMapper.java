package kr.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import kr.board.entity.Board;

@Mapper // Mybatis API
public interface BoardMapper {
	public List<Board> getLists();//인터페이스와 매퍼를 만들어놓으면 누군가가 이용해서 데이터를 만들어주는데 그건 SqlSessionFactoryBean
	
	public void boardInsert(Board vo);//글저장
	
    public Board boardContent(int idx);
    
    public void boardDelete(int idx);
    
    public void boardUpdate(Board vo);
    
    @Update("update myboard set count=count+1 where idx=#{idx}")//import org.apache.ibatis.annotations.Update 를 이용할때는 쿼리문을 xml에 작성하면 안된다.
    public void boardCount(int idx);
}
