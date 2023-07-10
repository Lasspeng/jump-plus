package com.cognixia.jump.furniture_app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cognixia.jump.furniture_app.filter.JwtRequestFilter;

@Configuration
public class SecurityConfiguration {
    
     @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtRequestFilter jwtRequestFilter;
    
    @Bean
    protected UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Bean
    protected SecurityFilterChain filterChain (HttpSecurity http) throws Exception {

        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/authenticate").permitAll()

            // User controller filters
            .antMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("ADMIN", "USER")
            .antMatchers(HttpMethod.POST, "/api/users").permitAll()
            .antMatchers(HttpMethod.PUT, "/api/users/**").hasAnyRole("ADMIN", "USER")
            .antMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")

            // Product controller filters
            .antMatchers(HttpMethod.GET, "/api/products/**").hasAnyRole("ADMIN", "USER")
            .antMatchers(HttpMethod.POST, "api/products").hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT, "/api/products/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN")

            // Transaction controller filter
            .antMatchers(HttpMethod.POST, "/api/transactions").permitAll()

            .antMatchers("/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    protected PasswordEncoder encoder() {
        
        // return NoOpPasswordEncoder.getInstance();

        return new BCryptPasswordEncoder();

    }

    // load the encoder & user details service that are needed for spring security to do authentication
	@Bean
	protected DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder( encoder() );
		
		return authProvider;
	}

    // can autowire and access the authentication manager (manages authentication (login) of our project)
	@Bean
	protected AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
}
