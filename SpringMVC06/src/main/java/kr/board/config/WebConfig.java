package kr.board.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
// web.xml
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] { RootConfig.class, SecurityConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] { ServletConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		encodingFilter.setForceEncoding(true);		
		return new Filter[]{encodingFilter};
	}

	//web.xml 사용사 web.xml파일에 CharacterEncodingFilter 설정하는
//	 <filter> 
//	    <filter-name>encodingFilter</filter-name> 
//	    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class> 
//	    <init-param> 
//	       <param-name>encoding</param-name> 
//	       <param-value>UTF-8</param-value> 
//	    </init-param> 
//	    <init-param> 
//	       <param-name>forceEncoding</param-name> 
//	       <param-value>true</param-value> 
//	    </init-param> 
//	 </filter> 
//	 <filter-mapping> 
//	    <filter-name>encodingFilter</filter-name> 
//	    <url-pattern>/*</url-pattern> 
//	 </filter-mapping>	
}