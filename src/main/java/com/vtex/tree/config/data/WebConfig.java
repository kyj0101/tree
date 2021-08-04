package com.vtex.tree.config.data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Bean
	public MappingJackson2JsonView jsonView() {

		return new MappingJackson2JsonView();
	}
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
		commonsMultipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024);
		
		return commonsMultipartResolver;
	}
}
