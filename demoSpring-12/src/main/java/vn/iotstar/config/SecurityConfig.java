package vn.iotstar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import vn.iotstar.repository.UserInfoRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	UserInfoRepository repository;
	
	@Bean
	UserDetailsService userDetailsService() {
		return new UserInfoService(repository); 
	}
	
	
//	@Bean
//// authentication
//	public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//		UserDetails admin = User.withUsername("huyen").password(encoder.encode("123")).roles("ADMIN").build();
//		UserDetails user = User.withUsername("user").password(encoder.encode("123")).roles("USER").build();
//		return new InMemoryUserDetailsManager(admin, user);
//	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationProvider authenticationProvider(){
	DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
	authenticationProvider.setUserDetailsService(userDetailsService());
	authenticationProvider.setPasswordEncoder(passwordEncoder());
	return authenticationProvider;
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(
				auth -> auth
				.requestMatchers("/user/new").permitAll()
				.requestMatchers("/", "/hello").permitAll()
				.requestMatchers("/customer/**").authenticated()

		).formLogin(Customizer.withDefaults()).build();
	}
}


