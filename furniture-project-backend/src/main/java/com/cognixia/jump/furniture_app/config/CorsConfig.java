package com.cognixia.jump.furniture_app.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfig implements WebMvcConfigurer {
    
     // define the rules of which APIs can be consumed and by who
	@Override
	public void addCorsMappings(CorsRegistry registry)  {
		
		registry.addMapping("/**"); // which paths are open to be consumed
				//.allowedMethods( "GET", "POST", "PUT", "DELETE", "PATCH" ); // this request types also available
		
	}
}
