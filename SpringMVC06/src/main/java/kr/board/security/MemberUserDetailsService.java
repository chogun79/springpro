package kr.board.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import kr.board.entity.Member;
import kr.board.entity.MemberUser;
import kr.board.mapper.MemberMapper;

// UserDetailsService  - (2)
public class MemberUserDetailsService implements UserDetailsService{
	//인증된 회원의 정보를 스프링시큐리티에서 제공하는 인증된 클래스에 담아줘야하는데 그게 UserDetailsService를 구현한 클래스이다.

	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //로그인이 처리된 정보를... 스프링시큐리트기 User를만들고 Userㄹ르 상속한 MemberUser에 넘겨주면
		// 아이디 패스워드,권한 맞는지 검사처리                //스프링시큐리티에서 username은 사용자이름이아닌 아이디를 의미한다.
		// 로그인처리하기
		Member mvo = memberMapper.memLogin(username);//Member mvo에는 사용자의 정보와 권한정보가들어있는데 스프링시큐리티에 연결된 필더들이 동작되기위해서는  이곳에있는 Member를 직접쓸수없다. 내부적인 클래스를 사용해야한다.
		// Member Type이 <--X--> UserDetails 타입과 맞지 않다
		// Member타입의 인스턴스를 UserDetails로처리하려면
		// 1. Member클래스에 UserDetails인터페이스를 구현해 주는 방법
		// 2. Member클래스가 이미 UserDetails인터페이스를 구현한 User클래스를 상속하는 방법
		// 3. 조합을 이용해서 Member를 포함하는 별도의 클래스를 만드는 방법
		//    =>별도의 클래스를 만들고 Member의 인스턴스를 감싸는 형태로 만들면 Member의 모든 정보를 추가적으로 사용가능 하다는 장점
		
		//--> UserDetails --->  implements --> User ---> extends --> MemberUser(직접생성 : member정보, Auth정보)
		if(mvo != null) {
			//return mvo;//mvo 리턴 못함, 리턴타입이클려서
			return new MemberUser(mvo);// new MemberUser(mvo); //Member, AuthVO
		}else {
			System.out.println("user with username " + username + " does not exist.");
			throw new UsernameNotFoundException("user with username" + username + "does not exist.");
		}
	}
	
}
