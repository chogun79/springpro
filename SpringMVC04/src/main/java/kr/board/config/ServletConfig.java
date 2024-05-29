package kr.board.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

// servlet-context.xml
@Configuration //환경설정파일이기때문에 넣어준다.
@EnableWebMvc // <annotation-driven /> 
@ComponentScan(basePackages = {"kr.board.controller"}) //<context:component-scan base-package="kr.board.controller" />
public class ServletConfig implements WebMvcConfigurer{// 패키지가다수일경우 basePackages = {"kr.board.controller"},{"kr.board.controller2"},{"kr.board.controller3"} )으로표현
									  //WebMvcConfigurer 를 반드시 implements해줘야 ViewResolver와 resources를 구현할수있는 메소드를 Override할수있다.
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}															//jsp폴더밑에서 jsp파일에서 코딩할때 resources를 경로지정할때(webapp 밑의 resources) webapp밑의 resourcess/밑의 ** 폴더들을 참고해서 찾아가겠다.라는 뜻

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver bean=new InternalResourceViewResolver();
		bean.setPrefix("/WEB-INF/views/");
		bean.setSuffix(".jsp");
		registry.viewResolver(bean);
	}

	
}