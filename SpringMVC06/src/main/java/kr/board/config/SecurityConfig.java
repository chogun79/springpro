package kr.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import kr.board.security.MemberUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public UserDetailsService memberUserDetailsService() {
	   return new MemberUserDetailsService();
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberUserDetailsService())
		.passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//요청에대한 보안 설정~~
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		http.addFilterBefore(filter,CsrfFilter.class);	
		// 요청에따른 권한을 확인하여 서비스 하는 부분 - ①
	    http
	         .authorizeRequests()
	              .antMatchers("/")
	              .permitAll()
	              .and()
	         .formLogin()
	              .loginPage("/memLoginForm.do")
	              .loginProcessingUrl("/memLogin.do")
	              .permitAll()
	              .and()
	         .logout()
	              .invalidateHttpSession(true)
	              .logoutSuccessUrl("/")
	              .and()
	         .exceptionHandling().accessDeniedPage("/access-denied");     
	}
	// 패스워드 인코딩 객체 설정
    @Bean 
    public PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder(); 
    }
//	@Bean
//	public UserDetailsService memberUserDetailsService() {
//	   return new MemberUserDetailsService();
//	}
//	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(memberUserDetailsService())
//		.passwordEncoder(passwordEncoder());
//	}
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		//요청에대한 보안 설정~~
//		CharacterEncodingFilter filter = new CharacterEncodingFilter();
//		filter.setEncoding("UTF-8");
//		filter.setForceEncoding(true);
//		http.addFilterBefore(filter,CsrfFilter.class);	
//		
//		// 요청에따른 권한을 확인하여 서비스하는 부분 - (1)
//		http
//			.authorizeRequests() //요청에대한 권한을 설정하겠다.
//				.antMatchers("/")//어떤경로로왔을때 어떤권한이있는 사용자가 접근할수있게 지정할수있다.    "/"는 어떠한 요청도 모든사용자를 허용하겠다는 뜻
//				.permitAll() //특별한권한이 없어도 허용하겠다.
//				.and() // 권한을 이어서 할때 작성
//			.formLogin()//어떤URL이 왔을때 어떤 로그인 페이지로 갈것인지...     스프링에서 제공해주는 로그인페이지가 있는데 그것을 쓰지않고 우리가 많든 페이지를 쓰겠다.
//				.loginPage("memLoginForm.do")
//				.loginProcessingUrl("/memLogin.do")//(스프링내부로 들어가느 단계)어떤 url이 왔을때 스프링 인증을 거치겠다 라는 것, 요청 처리 전 중간에 만들어지는 Spring Web Security의 UserDetailsService를 implements 한 클래스에서 Mapper로 연결해준다.
//				.permitAll()
//				.and()
//			.logout()
//				.invalidateHttpSession(true) //세션을 기본적으로 끊어준다.
//				.logoutSuccessUrl("/")//로그아웃이된다음 가야할 url지정
//				.and()
//			.exceptionHandling().accessDeniedPage("/access-dened");    //클라이언트가 특정권한이없이(미 로그인) 접근시 발생될 수있는 에러 처리 페이지
//	}	
//	
//	// 패스워드 인코딩(암호화) 객체 설정
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//		
//	}
}