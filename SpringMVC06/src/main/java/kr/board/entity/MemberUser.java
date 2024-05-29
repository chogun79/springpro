package kr.board.entity;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Data;
// 인증후 사용자 정보를 저장하기 - ③ (UserDetails)
@Data
public class MemberUser extends User{ // UserDetails

	private Member member;
	
	public MemberUser(String username, String password,
			          Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		// TODO Auto-generated constructor stub
	}
	public MemberUser(Member mvo) {
        super(mvo.getMemID(), mvo.getMemPassword(), mvo.getAuthList().stream()
       	      .map(auth->new SimpleGrantedAuthority(auth.getAuth())).// SimpleGrantedAuthority 권한정보를 문자열로 저장 [ROLE_USER, ROLE_MANAGER, ROLE_ADMIN]
       	      collect(Collectors.toList()));
        this.member=mvo; 	
        // List<AuthVO> -> Collection<SimpleGrantedAuthority>
    }
}

//	private Member member;
//	public MemberUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
//		super(username, password, authorities);
//		// 기본적으로 사용자아이디와, 패스워드, 권한정보를 넣어줄수있다.
//	}
//	
//	//스프링시큐리티 핵심로직 : 해당 entity는 스프링시큐리티의 User정보(Authorities시큐리티권한정보 - username 회원아이디,password 비밀번호, authorities 권한정보)와, 기존 회원정보들을 이 생성자에서 객체화시킨 후 MemberUserDetailsService에서 사용된다.
//	public MemberUser(Member mvo) {                                            // List<AuthoVO> -> collection<SimpleGrantedAuthority>으로 변환해야한다.
//		super(mvo.getMemID(), mvo.getMemPassword(), mvo.getAuthList().stream() // 1. getAuthList를 stream으로 바이트 열로 만든다음에
//			      .map(auth->new SimpleGrantedAuthority(auth.getAuth()))//2. map함수를 써서 stream으로 만들어진 결과를 새로운 타입으로 변경한다음 
//			      .collect(Collectors.toList())   //Collectors를 이용해서 컬렉션타입으로 변경한다.
//			 );
//		
//		System.out.print("User 생성자 호출");
//		this.member=mvo;
//		
//	}

