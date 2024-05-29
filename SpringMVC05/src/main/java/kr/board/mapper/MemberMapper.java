package kr.board.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.board.entity.AuthVO;
import kr.board.entity.Member;

@Mapper // Mybatis API
public interface MemberMapper {
	public Member registerCheck(String memID);
	
	public int register(Member m);//회원등록( 1, 0 )
	public Member memLogin(Member m);//로그인처리
	public int memUpdate(Member m);//회원정보수정
	public Member getMember(String memID);//회원정보가져오기
	public void memProfileUpdate(Member mvo); //이미지업데이트
	public void authInsert(AuthVO saveVO);//권한 저장

	public void authDelete(String memID);//권한 삭제
	
	
}
