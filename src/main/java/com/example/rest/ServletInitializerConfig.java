package com.example.rest;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;


@Configuration
@ImportResource("classpath:/root_context.xml")
public class ServletInitializerConfig extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.sources(RestApplApplication.class);
	}
	
	@Bean
	public CharacterEncodingFilter characterEncodingFilter() {
		final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		return characterEncodingFilter;
	}
	
	@Bean
	public ServletRegistrationBean restServletBean(final ApplicationContext applicationContext) {
		final DispatcherServlet servlet = new DispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		
		return new ServletRegistrationBean(servlet, new String[] {"/rest/*"});
	}
	
}
